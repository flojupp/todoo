package com.on.todoo.core;

import java.util.List;

import javax.validation.constraints.NotEmpty;


public class Todo extends Entity {	
	
	@NotEmpty
	String name;
	
	String description;
	
	List<Task> tasks;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	    
}
