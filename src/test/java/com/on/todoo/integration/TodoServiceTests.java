package com.on.todoo.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.mongodb.client.MongoDatabase;
import com.on.todoo.core.TestEntityBuilder;
import com.on.todoo.db.EmbeddedMongo;
import com.on.todoo.db.MongoDao;
import com.on.todoo.entities.Todo;
import com.on.todoo.services.TodoService;

@TestInstance(Lifecycle.PER_CLASS)
public class TodoServiceTests {

	EmbeddedMongo mongo;
	MongoDatabase mongoDb;
	MongoDao dao;

	TestEntityBuilder testEntityBuilder;
	Integer numOfTasks = 2;

	TodoService service;


	@BeforeAll
	public void setUp() {
		try {
			mongo = new EmbeddedMongo();
			mongoDb = mongo.startUp("testDB");
			dao = new MongoDao();
			dao.initialize(mongoDb);
			service = new TodoService(dao);

			testEntityBuilder = new TestEntityBuilder();
		} catch (Exception e) {
			tearDown();
		}
	}


	@AfterAll
	public void tearDown() {
		mongo.shutDown();
	}


	@AfterEach
	public void cleanUp() {
		mongoDb.getCollection("todos").drop();
	}


	@Test
	public void testCreate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);

		// WHEN:
		service.create(todo);

		// THEN:
		Assertions.assertEquals(1, service.getAll().size());
	}


	@Test
	public void testRead() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		service.create(todo);

		// WHEN:
		Todo read = service.getById(todo.getId());

		// THEN:
		Assertions.assertNotNull(read);
	}


	@Test
	public void testUpdate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		service.create(todo);

		// WHEN:
		String name = "updated";
		todo.setName(name);
		Todo updated = service.update(todo);

		// THEN:
		Assertions.assertNotNull(updated);
		Assertions.assertEquals(name, updated.getName());
	}


	@Test
	public void testDelete() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		service.create(todo);

		// WHEN:
		service.delete(todo.getId());
		Todo notFound = service.getById(todo.getId());

		// THEN:
		Assertions.assertNull(notFound);
	}


	@Test
	public void failCreate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);

		// WHEN:
		todo.setId("123");

		// THEN:
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.create(todo);
		});
	}


	@Test
	public void failUpdate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);

		// WHEN:
		todo.setId(null);

		// THEN:
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.update(todo);
		});
	}

}
