package no.bettermemory.models.storageHandlers.databaseExtraction;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetDay;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

public class GetDayFromMongoDB implements GetDay {
    MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetDayFromMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Day getSpecific(String patientId, int year, int weekNumber, String dayName) throws Exception {
        /*
         * Integer... time will be ignored for this class, it just exists in order to make interface for
         * Day and Activity generic to reduce amount of interfaces, will be an easy fix if required to split them.
        */

        Document weekQuery = new Document("patient", patientId).append("year", year).append("week_number", weekNumber);
        collection = DatabaseConnections.getWeeksCollection(database);
        Document relevantWeek = collection.find(weekQuery).first();

        if (!relevantWeek.containsKey("days")) throw new Exception("No days are registered for week " + weekNumber + ".");

        List<ObjectId> dayIds = (List<ObjectId>) relevantWeek.get("days");

        Document dayDocument = null;
        for (ObjectId dayId : dayIds) {
            Document dayQuery = new Document("_id", dayId).append("day", dayName);
            collection = DatabaseConnections.getDaysCollection(database);
            dayDocument = collection.find(dayQuery).first();
        }

        if (dayDocument == null) throw new Exception(dayName + " has not been registered for week " + weekNumber + ".");

        Day day = new Day();
        day.setDayName(dayDocument.getString("day"));

        if (dayDocument.containsKey("activities")) {
            List<ObjectId> activityIds = (List<ObjectId>) dayDocument.get("activities");
            GetActivityFromMongoDB getActivities = new GetActivityFromMongoDB(client);
            List<Activity> activities = getActivities.getActivitiesFromObjectId(activityIds);
            day.setActivities((ArrayList<Activity>) activities);
        }

        return day;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Day> getDaysFromObjectId(List<ObjectId> dayIds) throws Exception {
        List<Day> days = new ArrayList<>();

        for (ObjectId dayId : dayIds) {
            Document query = new Document("_id", dayId);
            collection = DatabaseConnections.getDaysCollection(database);
            Document result = collection.find(query).first();

            Day day = new Day();

            day.setDayName("day");
            if (result.containsKey("activities")) {
                List<ObjectId> activityIds = (List<ObjectId>) result.get("activities");
                GetActivityFromMongoDB getActivities = new GetActivityFromMongoDB(client);
                List<Activity> activities = getActivities.getActivitiesFromObjectId(activityIds);
                day.setActivities((ArrayList<Activity>) activities);
            }

            days.add(day);
        }

        return days;
    }
}
