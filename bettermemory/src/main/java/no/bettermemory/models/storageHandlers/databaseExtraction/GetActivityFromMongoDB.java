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
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.TimeControls;

/**
 *  This class should be used retrieving activity objects from data stored in a MongoDB.
 *  This is an example of how you use this class:
 *  <pre>{@code  GetActivityFromMongoDB getActivityFromMongoDB = new getActivityFromMongoDB(client);}
 */
public class GetActivityFromMongoDB implements GetActivity {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetActivityFromMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    /**
     *  This method should be used for creating an activitylist from data stored in a MongoDB. 
     *  It returns an array of every activity at present time
     *  @param activityToReceive -  This DTO includes patientId, year, weekNumber, dayName, hour, minutes
     *  @return ActivityDTO[]
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  getActivityFromMongoDB.getActivitiesAtMinute(activityToReceive);}
     */
    @SuppressWarnings("unchecked")
    @Override
    public ActivityDTO[] getActivitiesAtMinute(ActivityToReceiveDTO activityToReceive) throws Exception {
        try {
            TimeControls.hourCheck(activityToReceive.getHour());
            TimeControls.minuteCheck(activityToReceive.getMinutes());
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }


        Document weekQuery = new Document(
                "patient", activityToReceive.getPatientId()
            ).append(
                "year", activityToReceive.getYear()
            ).append(
                "week_number", activityToReceive.getWeekNumber()
        );

        collection = DatabaseConnections.getWeeksCollection(database);
        Document weekResult = collection.find(weekQuery).first();

        if (weekResult == null) throw new Exception(
            "week " + activityToReceive.getWeekNumber() + ", "+ activityToReceive.getYear() + " not registered in system"
        );
        if (!weekResult.containsKey("days")) throw new Exception(
            "No days registered on week " + activityToReceive.getWeekNumber() + "."
        );

        List<ObjectId> dayIds = (List<ObjectId>) weekResult.get("days");

        Document relevantDay = null;
        for (ObjectId dayId : dayIds) {
            Document dayQuery = new Document("_id", dayId).append("day", activityToReceive.getDayName());
            collection = DatabaseConnections.getDaysCollection(database);
            relevantDay = collection.find(dayQuery).first();
        }

        if (relevantDay == null) throw new Exception(
            activityToReceive.getDayName() + " not registered on week " + activityToReceive.getWeekNumber() + "."
        );
        if (!relevantDay.containsKey("activities")) throw new Exception(
            "No activities registered on " + activityToReceive.getDayName()
             + " at week " + activityToReceive.getWeekNumber() + "."
        );

        List<ObjectId> activityIds = (List<ObjectId>) relevantDay.get("activities");

        List<Document> activityDocuments = new ArrayList<>();
        for (ObjectId activityId : activityIds) {
            Document activityQuery = new Document(
                    "_id", activityId
                ).append(
                    "hour", activityToReceive.getHour()
                ).append(
                    "minutes", activityToReceive.getMinutes()
            );

            collection = DatabaseConnections.getActivitiesCollection(database);
            Document activityResult = collection.find(activityQuery).first();
            if (activityResult != null) activityDocuments.add(activityResult);
        }

        if (activityDocuments.size() == 0) throw new Exception(
            "Could not find an activity happening on: " + activityToReceive.getDayName()
             + ", week " + activityToReceive.getWeekNumber()
              + " at " + activityToReceive.getHour()
              + ":" + activityToReceive.getMinutes()
               + "."
        );

        List<ActivityDTO> activityDTOs = new ArrayList<>();
        for (Document activityDocument : activityDocuments) {
            Activity activity = new Activity();
            activity.setShortDescription(activityDocument.getString("short_desc"));
            activity.setLongDescription(activityDocument.getString("long_desc"));
            activity.setHour(activityDocument.getInteger("hour"));
            activity.setMinutes(activityDocument.getInteger("minutes"));
            activity.setImportant(activityDocument.getBoolean("important"));
            activity.setConcluded(activityDocument.getBoolean("concluded"));

            ActivityDTO activityDTO = new ActivityDTO(
                activityDocument.getObjectId("_id"),
                activity,
                weekResult.getInteger("year"),
                weekResult.getInteger("week_number"),
                relevantDay.getString("day")
            );
            activityDTOs.add(activityDTO);
        }

        return activityDTOs.toArray(ActivityDTO[]::new);
    }

    /**
     * This method should be used for to retrieve activities by their ObjectId from MongoDB.
     * It returns activities based on ObjectId and Activity
     * @param activityIds - A unique ID for an activity in MongoDB
     * @return activities
     * @code
     * This is an example of how you can use this method:
     * <pre>{@code  getActivityFromMongoDB.getActibitiesFromMongoDB(activityIds);}
     */
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
