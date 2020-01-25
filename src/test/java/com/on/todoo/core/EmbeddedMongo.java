package com.on.todoo.core;

import java.util.Arrays;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class EmbeddedMongo {

	private static final MongodStarter starter = MongodStarter.getDefaultInstance();
	private MongodExecutable mongodExe;
	private MongodProcess mongod;
	private MongoClient mongoClient;

	public MongoDatabase startUp(String databaseName) throws Exception {
		if (mongoClient == null) {
			mongodExe = starter.prepare(new MongodConfigBuilder().version(Version.Main.PRODUCTION)
					.net(new Net("localhost", 12345, Network.localhostIsIPv6())).build());

			mongod = mongodExe.start();

			mongoClient = new MongoClient("localhost", 12345);
		}

		// create codec registry for automatic BSON/POJO conversion:
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true)
						.conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION)).build()));

		return mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
	}

	public void shutDown() {
		mongoClient.close();
		mongod.stop();
		mongodExe.stop();
	}
}
