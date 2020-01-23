package com.on.todoo.services;

import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.jvnet.hk2.annotations.Service;

import com.on.todoo.db.MongoDao;
import com.on.todoo.entities.Task;
import com.on.todoo.entities.Todo;

@Service
public class TodoService {

	MongoDao dao;
	String collectionName = "todos";
	
	@Inject
	public TodoService(MongoDao dao) {
		this.dao = dao;
	}
	
	
	public Todo create(Todo todo) {
		if(todo.getId() != null) {
			throw new IllegalArgumentException(String.format("Todo %s already exists!", todo.getId()));
		}
				
		populateIds(todo);
		
		dao.create(collectionName, todo, Todo.class);
		return todo;
	}


	public Todo getById(String id) {
		return dao.getById(collectionName, id, Todo.class);
	}
	
	public List<Todo> getAll() {
		return dao.getAll(collectionName, Todo.class);
	}
	
	
	public Todo update(Todo todo) {
		if(todo.getId() == null) {
			throw new IllegalArgumentException(String.format("Todo %s does not exists!", todo.getId()));
		}
		
		populateIds(todo);
		
		return dao.update(collectionName, todo, Todo.class);
	}
	
	
	public void delete(String id) {
		dao.delete(collectionName, id);
	}
	
	
	private void populateIds(Todo todo) {
		if(todo.getId() == null) {
			todo.setId(ObjectId.get().toString());
		}
		
		for (Task task : todo.getTasks()) {
			if (task.getId() == null) {
				task.setId(ObjectId.get().toString());
			}
		}
	}
	
}

