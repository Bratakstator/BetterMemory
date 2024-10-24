package no.bettermemory.models.storageHandlers.databaseExtraction;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetActivity;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.TimeControls;

public class GetActivityFromMongoDB implements GetActivity {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetActivityFromMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<ObjectId, Activity> getActivitiesAtMinute(String patientId, int year, int weekNumber, String dayName, int hour, int minutes) throws Exception {
        try {
            TimeControls.hourCheck(hour);
            TimeControls.minuteCheck(minutes);
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }


        Document weekQuery = new Document("patient", patientId).append("year", year).append("week_number", weekNumber);
        collection = DatabaseConnections.getWeeksCollection(database);
        Document weekResult = collection.find(weekQuery).first();

        if (weekResult == null) throw new Exception("week " + weekNumber + ", "+ year + " not registered in system");
        if (!weekResult.containsKey("days")) throw new Exception("No days registered on week " + weekNumber + ".");

        List<ObjectId> dayIds = (List<ObjectId>) weekResult.get("days");

        Document relevantDay = null;
        for (ObjectId dayId : dayIds) {
            Document dayQuery = new Document("_id", dayId).append("day", dayName);
            collection = DatabaseConnections.getDaysCollection(database);
            relevantDay = collection.find(dayQuery).first();
        }

        if (relevantDay == null) throw new Exception(dayName + " not registered on week " + weekNumber + ".");
        if (!relevantDay.containsKey("activities")) throw new Exception(
            "No activities registered on " + dayName + " at week " + weekNumber + "."
        );

        List<ObjectId> activityIds = (List<ObjectId>) relevantDay.get("activities");

        List<Document> activityDocuments = new ArrayList<>();
        for (ObjectId activityId : activityIds) {
            Document activityQuery = new Document("_id", activityId).append("hour", hour).append("minutes", minutes);
            collection = DatabaseConnections.getActivitiesCollection(database);
            Document activityResult = collection.find(activityQuery).first();
            if (activityResult != null) activityDocuments.add(activityResult);
        }

        if (activityDocuments.size() == 0) throw new Exception(
            "Could not find an activity happening on: " + dayName + ", week " + weekNumber + " at " + hour + ":" + minutes + "."
        );

        HashMap<ObjectId, Activity> activities = new HashMap<>();
        for (Document activityDocument : activityDocuments) {
            Activity activity = new Activity();
            activity.setShortDescription(activityDocument.getString("short_desc"));
            activity.setLongDescription(activityDocument.getString("long_desc"));
            activity.setHour(activityDocument.getInteger("hour"));
            activity.setMinutes(activityDocument.getInteger("minutes"));
            activity.setImportant(activityDocument.getBoolean("important"));
            activity.setConcluded(activityDocument.getBoolean("concluded"));
            activities.put(activityDocument.getObjectId("_id"), activity);
        }

        return activities;
    }

    @Override
    public HashMap<ObjectId, Activity> getActivitiesAtInterval(
        String patientId, int year, int weekNumber, String dayName, int currentHour, int currentMinutes, int interval
    ) throws Exception {
        try {
            TimeControls.hourCheck(currentHour);
            TimeControls.minuteCheck(currentMinutes);
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }

        HashMap<ObjectId, Activity> activities = new HashMap<>();
        for (int offset = 0; offset < interval; offset++) {
            int minute = currentMinutes + offset;
            if (minute > 59) {
                currentHour += 1;
                currentMinutes -= 60;
            }
            activities.putAll(
                getActivitiesAtMinute(patientId, year, weekNumber, dayName, currentHour, minute)
            );
        }

        if (activities.size() == 0) throw new Exception("No activities found at the hour " + currentHour + ".");

        return activities;
    }

    @Override
    public HashMap<ObjectId, Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception {
        HashMap<ObjectId, Activity> activities = new HashMap<>();

        for (ObjectId activityId : activityIds) {
            Document query = new Document("_id", activityId);
            collection = DatabaseConnections.getActivitiesCollection(database);
            Document result = collection.find(query).first();

            Activity activity = new Activity();
            activity.setShortDescription(result.getString("short_desc"));
            activity.setLongDescription(result.getString("long_desc"));
            activity.setHour(result.getInteger("hour"));
            activity.setMinutes(result.getInteger("minutes"));
            activity.setImportant(result.getBoolean("important"));
            activity.setConcluded(result.getBoolean("concluded"));

            activities.put(activityId, activity);
        }

        return activities;
    }
}
