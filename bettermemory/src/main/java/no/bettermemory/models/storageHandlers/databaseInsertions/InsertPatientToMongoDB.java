package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.DatabaseDataHandler;

/**
 * This class should be used for sending a Patient objects to MongoDB
 * This is an example of how you use this class:
 * <pre>{@code  InsertPatientToMongoDB insertPatientToMongoDB = new insertPatientToMongoDB(client);}
 */
public class InsertPatientToMongoDB implements InsertToDatabase<Patient> {
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public InsertPatientToMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getPatientCollection(database);
    }
    
    /**
     *  This method should be used to save a Patient to MongoDB. 
     *  @param patient -  The patient you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertPatientToMongoDB.saveObject(patient);}
     */
    @Override
    public void saveObject(Patient patient) throws Exception {
        if (DatabaseDataHandler.checkIfExists(collection, patient) != null) throw new Exception(
            "Patient already exists in system."
        );
        
        Document insert = patient.toDocument();
        collection.insertOne(insert);
    }

    /**
     *  This method should be used to update an a Patient in a MongoDB. 
     *  @param patient -  The patient you want to update to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertPatientToMongoDB.updateObject(patient);}
     */
    @Override
    public void updateObject(Patient patient) throws Exception {
        String existingId = (String) DatabaseDataHandler.checkIfExists(collection, patient);
        if (existingId == null) throw new Exception("Patient does not exist in system.");

        Document query = new Document("_id", existingId);
        Document replaceDocument = patient.toDocument();
        collection.replaceOne(query, replaceDocument);
    }
}
