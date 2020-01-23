package com.on.todoo;

import com.on.todoo.db.ManagedMongo;
import com.on.todoo.db.MongoDao;
import com.on.todoo.health.MongoHealthCheck;
import com.on.todoo.resources.TodoResource;
import com.on.todoo.services.TodoService;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import zone.dragon.dropwizard.HK2Bundle;

public class TodooApplication extends Application<TodooConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodooApplication().run(args);
    }

    @Override
    public String getName() {
        return "todoo";
    }

    @Override
    public void initialize(final Bootstrap<TodooConfiguration> bootstrap) {
    	HK2Bundle.addTo(bootstrap);
    }

    @Override
    public void run(final TodooConfiguration configuration,
                    final Environment environment) {
    	
    	MongoDao dao = new MongoDao();
    	ManagedMongo mongo = new ManagedMongo(configuration, dao);
    	environment.lifecycle().manage(mongo);
    	
    	environment.healthChecks().register("mongo", new MongoHealthCheck(configuration));
    	
    	// manual DI: :(    	
    	TodoService todoService = new TodoService(dao);
    	TodoResource todoResource = new TodoResource(todoService);
    	
        environment.jersey().register(todoResource);
    }

}
