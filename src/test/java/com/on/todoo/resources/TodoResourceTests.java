package com.on.todoo.resources;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mongodb.client.MongoDatabase;
import com.on.todoo.core.TestEntityBuilder;
import com.on.todoo.db.EmbeddedMongo;
import com.on.todoo.db.MongoDao;
import com.on.todoo.entities.Todo;
import com.on.todoo.services.TodoService;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(DropwizardExtensionsSupport.class)
public class TodoResourceTests {

	EmbeddedMongo mongo;
	MongoDatabase mongoDb;

	TestEntityBuilder testEntityBuilder;
	Integer numOfTasks = 2;

	ResourceExtension resource;


	@BeforeAll
	public void setUp() throws Exception {
		testEntityBuilder = new TestEntityBuilder();
		mongo = new EmbeddedMongo();
		mongoDb = mongo.startUp("testDB");
		MongoDao dao = new MongoDao();
		dao.initialize(mongoDb);
		TodoService service = new TodoService(dao);

		resource = ResourceExtension.builder().addResource(new TodoResource(service))
				.addProvider(IllegalArgumentExceptionMapper.class).build();
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
	public void create() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);

		// WHEN:
		Todo response = resource.target("/todos").request().post(Entity.json(todo), Todo.class);

		// THEN:
		Assertions.assertNotNull(response.getId());
	}


	@Test
	public void read() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo = resource.target("/todos").request().post(Entity.json(todo), Todo.class);

		// WHEN:
		Todo response = resource.target("/todos/" + todo.getId()).request().get(Todo.class);

		// THEN:
		Assertions.assertNotNull(response);
	}


	@Test
	@SuppressWarnings("unchecked")
	public void readAll() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		resource.target("/todos").request().post(Entity.json(todo), Todo.class);
		resource.target("/todos").request().post(Entity.json(todo), Todo.class);

		// WHEN:
		List<Todo> todos = resource.target("/todos").request().get(List.class);

		// THEN:
		Assertions.assertNotNull(todos);
		Assertions.assertEquals(2, todos.size());
	}


	@Test
	public void update() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo = resource.target("/todos").request().post(Entity.json(todo), Todo.class);

		// WHEN:
		String name = "updated";
		todo.setName(name);
		Todo response = resource.target("/todos").request().put(Entity.json(todo), Todo.class);

		// THEN:
		Assertions.assertEquals(name, response.getName());
	}


	@Test
	public void delete() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo = resource.target("/todos").request().post(Entity.json(todo), Todo.class);

		// WHEN:
		resource.target("/todos/" + todo.getId()).request().delete();
		Todo notFound = resource.target("/todos/" + todo.getId()).request().get(Todo.class);

		// THEN:
		Assertions.assertNull(notFound);
	}


	@Test
	public void failCreate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setId("123");

		// WHEN:
		Response response = resource.target("/todos").request().post(Entity.json(todo));

		// THEN:
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
	}


	@Test
	public void failUpdate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);

		// WHEN:
		Response response = resource.target("/todos").request().put(Entity.json(todo));

		// THEN:
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
	}


	@Test
	public void failValidate() {
		// GIVEN:
		Todo todo = testEntityBuilder.buildUnsavedTodo(numOfTasks);
		todo.setName(null);

		// WHEN:
		Response response = resource.target("/todos").request().post(Entity.json(todo));

		// THEN:
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
	}
}
