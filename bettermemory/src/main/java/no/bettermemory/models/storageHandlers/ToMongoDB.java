package no.bettermemory.models.storageHandlers;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.ToDatabase;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;




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

    public ToMongoDB(MongoClient client) {
        this.client = client;
    }

    public void saveObject(Object object) {
        MongoDatabase database = DatabaseConnections.getUsersDatabase(client);
        if (object instanceof Patient) {
            MongoCollection collection = DatabaseConnections.getPatientCollection(database);
            Document document = ((Patient) object).toDocument();
            collection.insertOne(document);
        }
    }

    public void updateObject(Object object) {}

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoClient getClient() {
        return client;
    }
}
