package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.ArrayList;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.tools.DatabaseConnections;

/**
 * This class should be used for sending a Week object to MongoDB
 * This is an example of how you use this class:
 * <pre>{@code  InsertPatientToMongoDB insertPatientToMongoDB = new insertPatientToMongoDB(client);}
 */
public class InsertWeekToMongoDB implements InsertToDatabase<Week> {
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;
    InsertActivityOrDay<Day> insertDay;
    
    public InsertWeekToMongoDB(MongoClient client, InsertActivityOrDay<Day> insertDay) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getWeeksCollection(database);
        this.insertDay = insertDay;
    }
    
    /**
     *  This method should be used to save a week to MongoDB. 
     *  @param patient -  The patient you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertWeekToMongoDB.saveObject(week);}
     */
    public void saveObject(Week week) throws Exception {
        if (week == null) throw new Exception("Week is set to null.");

        Document weekDocument = week.toDocument();
        
        ArrayList<Day> days = week.getDays();
        if (days.size() != 0) {
            List<ObjectId> dayIds = new ArrayList<>();
            for (Day day : days) {
                ObjectId dayId = insertDay.saveObject(day);
                dayIds.add(dayId);
            }

            weekDocument.append("days", dayIds);
        }

        collection.insertOne(weekDocument);
    }

    public void updateObject(Week week) throws Exception {}
}
