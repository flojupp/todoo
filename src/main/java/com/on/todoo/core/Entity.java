package com.on.todoo.core;

import org.bson.codecs.pojo.annotations.BsonId;

public abstract class Entity {

	@BsonId
	String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
