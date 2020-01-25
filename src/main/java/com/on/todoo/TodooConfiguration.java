package com.on.todoo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class TodooConfiguration extends Configuration {

	@NotEmpty
	String mongoHost;

	@NotNull
	Integer mongoPort;

	@NotEmpty
	String mongoDbName;

	Boolean embeddedMongo;


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


	public Boolean getEmbeddedMongo() {
		return embeddedMongo;
	}


	public void setEmbeddedMongo(Boolean embeddedMongo) {
		this.embeddedMongo = embeddedMongo;
	}
}
