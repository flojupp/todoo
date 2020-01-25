package com.on.todoo.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.on.todoo.entities.Todo;
import com.on.todoo.services.TodoService;

/**
 * GET /todos → Returns a list of all Todos
 * 
 * POST /todos → Expects a Todo (without id) and returns a Todo with id
 * 
 * GET /todos/{id} → Returns a Todo
 * 
 * PUT /todos/{id} → Overwrites an existing Todo
 * 
 * DELETE /todos/{id} → Deletes a Todo
 */

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

	TodoService service;


	@Inject
	public TodoResource(TodoService service) {
		this.service = service;
	}


	@POST
	public Todo createTodo(@Valid Todo todo) throws Exception {
		return service.create(todo);
	}


	@GET
	public List<Todo> getTodos() {
		return service.getAll();
	}


	@GET
	@Path("/{id}")
	public Todo getTodo(@PathParam("id") String id) {
		return service.getById(id);
	}


	@PUT
	public Todo updateTodo(@Valid Todo todo) {
		return service.update(todo);
	}


	@DELETE
	@Path("/{id}")
	public void deleteTodo(@PathParam("id") String id) {
		service.delete(id);
	}

}
