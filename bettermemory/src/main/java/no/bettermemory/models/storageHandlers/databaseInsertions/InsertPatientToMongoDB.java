package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.DatabaseDataHandler;

public class InsertPatientToMongoDB implements InsertToDatabase<Patient> {
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public InsertPatientToMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }
    
    @Override
    public void saveObject(Patient patient) throws Exception {
        collection = DatabaseConnections.getPatientCollection(database);

        if (DatabaseDataHandler.checkIfExists(collection, patient) != null) throw new Exception("Patient already exists in system");
        
        Document insert = patient.toDocument();
        collection.insertOne(insert);
    }
}
