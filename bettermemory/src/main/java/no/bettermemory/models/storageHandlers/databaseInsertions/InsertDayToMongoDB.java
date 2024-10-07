package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.ArrayList;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.DatabaseDataHandler;

public class InsertDayToMongoDB implements InsertActivityOrDay<Day> {
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;
    
    public InsertDayToMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    public ObjectId saveObject(Day day) throws Exception {
        return null;
    }
    
    public void updateObject(ObjectId dayId, Day day) throws Exception {}
}
