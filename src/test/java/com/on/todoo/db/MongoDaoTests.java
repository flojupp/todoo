package com.on.todoo.db;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.mongodb.client.MongoDatabase;
import com.on.todoo.core.EmbeddedMongo;
import com.on.todoo.core.TestEntityBuilder;
import com.on.todoo.entities.Todo;

@TestInstance(Lifecycle.PER_CLASS)
class MongoDaoTests {

	EmbeddedMongo mongo;
	MongoDao dao;
	MongoDatabase mongoDb;
	TestEntityBuilder testEntityBuilder;

	final String collectionName = "testCollection";
	final Integer numOfTasks = 2;

	@BeforeAll
	public void setUp() {
		try {
			mongo = new EmbeddedMongo();
			mongoDb = mongo.startUp("testDB");
			dao = new MongoDao();
			dao.initialize(mongoDb);

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
		mongoDb.getCollection(collectionName).drop();
	}

	@Test
	public void testCreate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setId(ObjectId.get().toString());

		// WHEN:
		dao.create(collectionName, todo, Todo.class);

		// THEN:
		Assertions.assertEquals(1, dao.getAll(collectionName, Todo.class).size());
	}

	@Test
	public void testRead() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setId(ObjectId.get().toString());
		dao.create(collectionName, todo, Todo.class);

		// WHEN:
		Todo read = dao.getById(collectionName, todo.getId(), Todo.class);

		// THEN:
		Assertions.assertNotNull(read);
	}

	@Test
	public void testUpdate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setId(ObjectId.get().toString());
		dao.create(collectionName, todo, Todo.class);

		// WHEN:
		String name = "updated";
		todo.setName(name);
		Todo updated = dao.update(collectionName, todo, Todo.class);

		// THEN:
		Assertions.assertNotNull(updated);
		Assertions.assertEquals(name, updated.getName());
	}

	@Test
	public void testDelete() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setId(ObjectId.get().toString());
		dao.create(collectionName, todo, Todo.class);

		// WHEN:
		dao.delete(collectionName, todo.getId());
		Todo notFound = dao.getById(collectionName, todo.getId(), Todo.class);

		// THEN:
		Assertions.assertNull(notFound);
	}

}
