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

public class InsertActivityToMongoDB implements InsertActivityOrDay<Activity> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    
    public InsertActivityToMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getActivitiesCollection(database);
    }

    @Override
    public ObjectId saveObject(Activity activity) throws Exception {
        if (activity == null) throw new Exception("Activity is null.");
        Document insert = activity.toDocument();
        InsertOneResult result = collection.insertOne(insert);

        return result.getInsertedId().asObjectId().getValue();
    }

    @Override
    public void updateObject(ObjectId activityId, Activity activity) throws Exception {
        if (activityId == null) throw new Exception("ActivityId is null.");
        if (activity == null) throw new Exception("Activity is null.");
        Document query = new Document("_id", activityId);
        Document replaceDocument = activity.toDocument();
        collection.replaceOne(query, replaceDocument);
    }
}
