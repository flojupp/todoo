package com.on.todoo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.on.todoo.TodooConfiguration;

public class MongoHealthCheck extends HealthCheck {
	
    TodooConfiguration config;

    public MongoHealthCheck(TodooConfiguration config) {
        this.config = config;
    }

    @Override
    protected Result check() throws Exception {
    	
    	MongoClient client = null;
    	
    	try {
    		client = new MongoClient(config.getMongoHost(), config.getMongoPort());
    	} catch (Exception e) {
    		return Result.unhealthy("Cannot connect to Mongo! " + e.getMessage());
    	} finally {
    		if(client != null)
    			client.close();
    	}
        
        return Result.healthy();        
    }
}