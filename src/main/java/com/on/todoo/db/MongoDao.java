package com.on.todoo.db;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.hk2.annotations.Service;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.on.todoo.core.Entity;

@Service
public class MongoDao {

	MongoDatabase mongoDb;	
	
	
	public void initialize(MongoDatabase mongoDb) {
		this.mongoDb = mongoDb;		
	}
	
	
	public <T extends Entity> void create(String collectionName, T entity, Class<T> clazz) {
		mongoDb.getCollection(collectionName, clazz).insertOne(entity);
	}
	
	
	public <T extends Entity> T getById(String collectionName, String id, Class<T> clazz) {
		return mongoDb.getCollection(collectionName, clazz).find(Filters.eq("_id", id), clazz).first();
	}
	
	
	public <T extends Entity> List<T> getAll(String collectionName, Class<T> clazz) {
		return mongoDb.getCollection(collectionName, clazz).find(clazz).into(new ArrayList<T>());
	}
	
	
	public <T extends Entity> T update(String collectionName, T entity, Class<T> clazz) {
		return mongoDb.getCollection(collectionName, clazz).findOneAndReplace(
				Filters.eq("_id", entity.getId()), entity, 
				new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));
	}
	
	
	public void delete(String collectionName, String id) {
		mongoDb.getCollection(collectionName).deleteOne(Filters.eq("_id", id));
	}

}
