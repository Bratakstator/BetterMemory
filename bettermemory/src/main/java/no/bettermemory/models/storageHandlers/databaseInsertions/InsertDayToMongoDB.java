package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import java.util.List;
import java.util.ArrayList;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

/**
 * This class should be used for sending day objects to MongoDB
 * This is an example of how you use this class:
 * <pre>{@code  InsertDayToMongoDB insertDayToMongoDB = new insertDayToMongoDB(client);}
 */
public class InsertDayToMongoDB implements InsertActivityOrDay<Day> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private InsertActivityOrDay<Activity> insertActivity;
    
    public InsertDayToMongoDB(MongoClient client, InsertActivityOrDay<Activity> insertActivity) {
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getDaysCollection(database);
        this.insertActivity = insertActivity;
    }
    
    /**
     *  This method should be used to save a day to MongoDB. 
     *  @param day -  The day you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertActivityToMongoDB.saveObject(day);}
     */
    public ObjectId saveObject(Day day) throws Exception {
        if (day == null) throw new Exception("Day object is null.");

        Document dayDocument = day.toDocument();
    
        ArrayList<Activity> activities = day.getActivities();
        if (activities.size() != 0) {
            List<ObjectId> activityIds = new ArrayList<>();
            for (Activity activity : activities) {
                ObjectId activityId = insertActivity.saveObject(activity);
                activityIds.add(activityId);
            }

            dayDocument.append("activities", activityIds);
        }

        InsertOneResult result = collection.insertOne(dayDocument);
        ObjectId dayId = result.getInsertedId().asObjectId().getValue();

        return dayId;
    }
    
    public void updateObject(ObjectId dayId, Day day) throws Exception {}
}
