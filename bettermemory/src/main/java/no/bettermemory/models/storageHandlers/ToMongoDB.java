package no.bettermemory.models.storageHandlers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.ToDatabase;

public class ToMongoDB implements ToDatabase {
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection collection;
}
