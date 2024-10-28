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

public class InsertDayToMongoDB implements InsertActivityOrDay<Day> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private InsertActivityOrDay<Activity> insertActivity;
    
    public InsertDayToMongoDB(MongoClient client, InsertActivityOrDay<Activity> insertActivity) {
        this.database = DatabaseConnections.getUsersDatabase(client);
        this.collection = DatabaseConnections.getDaysCollection(database);
        this.insertActivity = insertActivity;
    }

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
