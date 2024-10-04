package no.bettermemory.models.storageHandlers.databaseExtraction;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.ArrayList;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetByPeriod;
import no.bettermemory.interfaces.storageHandlers.storageGetters.GetFromObjectId;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.DatabaseConnections;

public class GetActivityFromMongoDB implements GetByPeriod<Activity>, GetFromObjectId<Activity> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetActivityFromMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Activity getSpecific(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
        if (time == null) throw new Exception("Hour and minutes are required for search.");
        if (time.length < 2) {
            String message;
            switch (time.length) {
                case 0:
                    message = "Missing hour and minutes.";
                    break;
                case 1:
                    message = "Missing minutes.";
                    break;
                default:
                    message = "Missing time values, has " + time.length + " values.";
            }
            throw new Exception(message);
        }

        int hour = time[0].intValue();
        int minute = time[0].intValue();

        Document weekQuery = new Document("patient", patientId).append("year", year).append("week_number", weekNumber);
        collection = DatabaseConnections.getWeeksCollection(database);
        Document weekResult = collection.find(weekQuery).first();

        if (!weekResult.containsKey("days")) throw new Exception("No days registered on week " + weekNumber + ".");

        List<ObjectId> dayIds = (List<ObjectId>) weekResult.get("days");
        if (dayIds == null || dayIds.size() == 0) throw new Exception("No days registered on week " + weekNumber + ".");

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
        if (activityIds == null || activityIds.size() == 0) throw new Exception(
            "No activities registered on " + dayName + " at week " + weekNumber + "."
        );

        Document activityDocument = null;
        for (ObjectId activityId : activityIds) {
            Document activityQuery = new Document("_id", activityId).append("hour", hour).append("minutes", minute);
            collection = DatabaseConnections.getActivitiesCollection(database);
            activityDocument = collection.find(activityQuery).first();
        }

        if (activityDocument == null) throw new Exception(
            "Could not find an activity happening on: " + dayName + ", week " + weekNumber + " at " + hour + ":" + minute + "."
        );

        Activity activity = new Activity();
        activity.setShortDescription(activityDocument.getString("short_desc"));
        activity.setLongDescription(activityDocument.getString("long_desc"));
        activity.setHour(activityDocument.getInteger("hour"));
        activity.setMinutes(activityDocument.getInteger("minutes"));
        activity.setImportant(activityDocument.getBoolean("important"));
        activity.setConcluded(activityDocument.getBoolean("concluded"));

        return activity;
    }

    @Override
    public List<Activity> getListByPeriod(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
        if (time == null) throw new Exception("Hour and minutes are required for search.");
        if (time.length == 0) {
            throw new Exception("Missing hour.");
        }

        List<Activity> activities = new ArrayList<>();

        int hour = time[0].intValue();
        for (int minute = 0; minute < 60; minute++) {
            activities.add(
                getSpecific(patientId, year, weekNumber, dayName, hour, minute)
            );
        }

        if (activities.size() == 0) throw new Exception("No activities found at the hour " + hour + ".");

        return activities;
    }

    @Override
    public Activity getSpecificFromObjectId(ObjectId tId) throws Exception {
        return null;
    }

    @Override
    public List<Activity> getListFromObjectId(List<ObjectId> tIds) throws Exception {
        return null;
    }
}
