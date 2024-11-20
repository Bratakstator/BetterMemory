package no.bettermemory.tools;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.models.users.Patient;

/**
 * Utility class for handling database interactions and checking for the existence of various objects in collections.
 * This class provides methods to query the database collections and determine if specific objects (e.g., Patients, 
 * Activities, Days, Weeks, Healthcare Personnel) already exist in the database.
 */
public class DatabaseDataHandler {

    /**
     * Checks if the given object exists in the specified MongoDB collection. 
     * Determines the type of the object and calls the appropriate method for type-specific checks.
     * 
     * @param collection The {@link MongoCollection<Document>} instance to query.
     * @param object The object to check for existence (e.g., Patient, HealthCarePersonnel, Week, Day, Activity).
     * @return The ID of the object if it exists, or {@code null} if it does not.
     */
    public static Object checkIfExists(MongoCollection<Document> collection, Object object) {
        Object objectId;
        if (object instanceof Patient) objectId = checkIfExistsPatient(collection, (Patient) object);
        else if (object instanceof HealthCarePersonnel) objectId = checkIfExistsHealthCarePersonnel(collection, (HealthCarePersonnel) object);
        else if (object instanceof Week) objectId = checkIfExistsWeek(collection, (Week) object);
        else if (object instanceof Day) objectId = checkIfExistsDay(collection, (Day) object);
        else if (object instanceof Activity) objectId = checkIfExistsActivity(collection, (Activity) object);
        else objectId = null;

        return objectId;
    }

    /**
     * Checks if a Patient exists in the specified collection.
     * 
     * @param collection The MongoDB collection to query.
     * @param patient The Patient object to check.
     * @return The ID of the patient if it exists, or {@code null} otherwise.
     */
    private static Object checkIfExistsPatient(MongoCollection<Document> collection, Patient patient) {
        Document query = new Document("_id", patient.getPatientId());
        Document result = collection.find(query).first();
        if (result != null) return result.getString("_id");

        return null;
    }

    /**
     * Checks if a HealthCarePersonnel exists in the specified collection.
     * 
     * @param collection The MongoDB collection to query.
     * @param healthCarePersonnel The HealthCarePersonnel object to check.
     * @return The ID of the healthcare personnel if it exists, or {@code null} otherwise.
     */
    private static Object checkIfExistsHealthCarePersonnel(MongoCollection<Document> collection, HealthCarePersonnel healthCarePersonnel) {
        Document query = new Document("_id", healthCarePersonnel.getEmployeeNumber());
        Document result = collection.find(query).first();
        if (result != null) return result.getString("_id");

        return null;
    }

    /**
     * Checks if a Week exists in the specified collection.
     * 
     * @param collection The MongoDB collection to query.
     * @param week The Week object to check.
     * @return The ID of the week if it exists, or {@code null} otherwise.
     */
    private static Object checkIfExistsWeek(MongoCollection<Document> collection, Week week) {
        List<Object> dayIds = week.getDays().stream().map(
            day -> checkIfExistsDay(
                DatabaseConnections.getDaysCollection(
                    DatabaseConnections.getUsersDatabase(
                        DatabaseConnections.getMongodbClientInfo()
                    )
                ),
                day
            )
        ).collect(Collectors.toList());

        Document query = new Document("week_number", week.getWeekNumber())
        .append("year", week.getYear())
        .append("patient", week.getPatient().getPatientId())
        .append("days", dayIds);
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }

    /**
     * Checks if a Day exists in the specified collection.
     * 
     * @param collection The MongoDB collection to query.
     * @param day The Day object to check.
     * @return The ID of the day if it exists, or {@code null} otherwise.
     */
    private static Object checkIfExistsDay(MongoCollection<Document> collection, Day day) {
        List<Object> activityIds = day.getActivities().stream().map(
            activity -> checkIfExistsActivity(
                DatabaseConnections.getActivitiesCollection(
                    DatabaseConnections.getUsersDatabase(
                        DatabaseConnections.getMongodbClientInfo()
                    )
                ),
                activity
            )
        ).collect(Collectors.toList());

        Document query = new Document("day", day.getDayName()).append("activities", activityIds);
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }

    /**
     * Checks if an Activity exists in the specified collection.
     * 
     * @param collection The MongoDB collection to query.
     * @param activity The Activity object to check.
     * @return The ID of the activity if it exists, or {@code null} otherwise.
     */
    private static Object checkIfExistsActivity(MongoCollection<Document> collection, Activity activity) {
        Document query = new Document("hour", activity.getHour()).append("minutes", activity.getMinutes())
        .append("short_desc", activity.getShortDescription()).append("long_desc", activity.getLongDescription());
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }
    
}
