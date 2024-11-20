package no.bettermemory.models.storageHandlers.databaseInsertions;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.DatabaseConnections;
/**
 * This class should be used for sending activities to MongoDB
 * This is an example of how you use this class:
 * <pre>{@code  InsertActivityToMongoDB insertActivityToMongoDB = new insertActivityToMongoDB(client);}
 */
public class InsertActivityToMongoDB implements InsertActivityOrDay<Activity> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    
    public InsertActivityToMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getActivitiesCollection(database);
    }

    /**
     *  This method should be used to save an activity to MongoDB. 
     *  @param activity -  The activity you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertActivityToMongoDB.saveObject(activity);}
     */
    @Override
    public ObjectId saveObject(Activity activity) throws Exception {
        if (activity == null) throw new Exception("Activity is null.");
        Document insert = activity.toDocument();
        InsertOneResult result = collection.insertOne(insert);

        return result.getInsertedId().asObjectId().getValue();
    }
    
    /**
     *  This method should be used to update an activity in a MongoDB. 
     *  @param activity -  The activityID you want to update to the database
     *  @param activity -  The activity you want to update to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertActivityToMongoDB.updateObject(activityId, activity);}
     */
    @Override
    public void updateObject(ObjectId activityId, Activity activity) throws Exception {
        if (activityId == null) throw new Exception("ActivityId is null.");
        if (activity == null) throw new Exception("Activity is null.");
        Document query = new Document("_id", activityId);
        Document replaceDocument = activity.toDocument();
        collection.replaceOne(query, replaceDocument);
    }
}
