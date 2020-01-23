package com.on.todoo.entities;

import javax.validation.constraints.NotEmpty;


public class Task extends Entity {

	@NotEmpty
	String name;
	
	String description;


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
	
}
