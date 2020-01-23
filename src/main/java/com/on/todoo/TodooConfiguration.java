package com.on.todoo;

import io.dropwizard.Configuration;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;


public class TodooConfiguration extends Configuration {
    
	@NotEmpty
	String mongoHost;
	
	@NotNull
	Integer mongoPort;
	
	@NotEmpty
	String mongoDbName;

	
	public String getMongoHost() {
		return mongoHost;
	}

	public void setMongoHost(String mongoHost) {
		this.mongoHost = mongoHost;
	}

	public Integer getMongoPort() {
		return mongoPort;
	}

	public void setMongoPort(Integer mongoPort) {
		this.mongoPort = mongoPort;
	}

	public String getMongoDbName() {
		return mongoDbName;
	}

	public void setMongoDbName(String mongoDbName) {
		this.mongoDbName = mongoDbName;
	}
}
