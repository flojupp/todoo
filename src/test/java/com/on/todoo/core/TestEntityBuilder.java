package com.on.todoo.core;

import java.util.ArrayList;
import java.util.List;

import com.on.todoo.entities.Task;
import com.on.todoo.entities.Todo;

public class TestEntityBuilder {

	private final String description = "test description";
	private final String name = "test name";

	
	public Todo buildUnsavedTodo(Integer numberOfTasks) {
		Todo todo = new Todo();
		todo.setDescription(description);
		todo.setName(name);
		
		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < numberOfTasks; i++) {
			tasks.add(buildUnsavedTask());
		}
		todo.setTasks(tasks );
		
		return todo;
	}
	
	
	public Task buildUnsavedTask() {
		Task task = new Task();
		task.setDescription(description);
		task.setName(name);
		
		return task;
	}
}
