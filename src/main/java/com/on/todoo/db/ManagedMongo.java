package com.on.todoo.db;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.on.todoo.TodooConfiguration;

import io.dropwizard.lifecycle.Managed;


public class ManagedMongo implements Managed {

	TodooConfiguration config;
	MongoClient mongoClient;
	MongoDao mongoDao;
	
	
	public ManagedMongo(TodooConfiguration config, MongoDao mongoDao) {
		this.config = config;
		this.mongoDao = mongoDao;
	}
	
	
	@Override
	public void start() throws Exception {
		mongoClient = new MongoClient(config.getMongoHost(), config.getMongoPort());
				
		// create codec registry for automatic BSON/POJO conversion:
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        		CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        // create new database if not exists:
        MongoDatabase mongoDb = mongoClient.getDatabase(config.getMongoDbName()).withCodecRegistry(pojoCodecRegistry);
		
		mongoDao.initialize(mongoDb);
	}

	
	@Override
	public void stop() throws Exception {
		mongoClient.close();
	}

	
	public MongoClient getMongoClient() {
		return mongoClient;
	}

}
