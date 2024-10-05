package no.bettermemory.models.storageHandlers.databaseExtraction;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetByPeriod;
import no.bettermemory.interfaces.storageHandlers.storageGetters.GetFromObjectId;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

public class GetDayFromMongoDB implements GetByPeriod<Day>, GetFromObjectId<Day> {
    MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetDayFromMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Day getSpecific(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
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
            List<Activity> activities = getActivities.getListFromObjectId(activityIds);
            day.setActivities((ArrayList<Activity>) activities);
        }

        return day;
    }

    @Override
    public List<Day> getListByPeriod(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
        return null;
    }

    @Override
    public Day getSpecificFromObjectId(ObjectId dayId) throws Exception {
        return null;
    }

    @Override
    public List<Day> getListFromObjectId(List<ObjectId> dayIds) throws Exception {
        return null;
    }
}
