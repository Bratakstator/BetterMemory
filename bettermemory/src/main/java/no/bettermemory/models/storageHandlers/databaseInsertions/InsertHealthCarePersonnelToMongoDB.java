package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.DatabaseDataHandler;

public class InsertHealthCarePersonnelToMongoDB implements InsertToDatabase<HealthCarePersonnel> {
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public InsertHealthCarePersonnelToMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getHealthCarePersonnelCollection(database);
    }

    public void saveObject(HealthCarePersonnel healthCarePersonnel) throws Exception {
        if (DatabaseDataHandler.checkIfExists(collection, healthCarePersonnel) != null) throw new Exception(
            "Health care personnel already exists in system."
        );

        Document insert = healthCarePersonnel.toDocument();
        collection.insertOne(insert);
    }

    public void updateObject(HealthCarePersonnel healthCarePersonnel) throws Exception {
        String existingId = (String) DatabaseDataHandler.checkIfExists(collection, healthCarePersonnel);
        if (existingId == null) throw new Exception(
            "Health care personnel does not exist in system."
        );

        Document query = new Document("_id", existingId);
        Document replaceDocument = healthCarePersonnel.toDocument();
        collection.replaceOne(query, replaceDocument);
    }
}
