package no.bettermemory.models.storageHandlers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.ToDatabase;




/**
 * This class is used to connect and write to a MongoDB database.
 * The ToMongoDB class takes the provided information and establishes a connection to the database if possible.
 * 
 * @param connectionString - This string should contain the information used to connect to a client.
 * @param databaseName - This string should contain the name of the database to be used.
 * @param collectionName - This string should contain the name of the collection to be used.
 * 
 * @author Joakim Klemsdal Bøe
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code ToMongoDB toMongoDB = new ToMongoDB("mongodb://localhost:27017", "Users", "Patients")}
 */
public class ToMongoDB implements ToDatabase {
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection collection;

    public ToMongoDB(String connectionString, String databaseName, String collectionName) {
        this.client = MongoClients.create(connectionString);
        this.database = this.client.getDatabase(databaseName);
        this.collection = this.database.getCollection(collectionName);
    }

    public void setClient(String connectionString) {
        this.client = MongoClients.create(connectionString);
    }

    public void setDatabase(String databaseName) {
        this.database = this.client.getDatabase(databaseName);
    }

    public void setCollection(String collectionName) {
        this.collection = this.database.getCollection(collectionName);
    }

    public MongoClient getClient() {
        return client;
    }
    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection getCollection() {
        return collection;
    }
}
