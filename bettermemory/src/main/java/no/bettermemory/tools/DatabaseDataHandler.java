package no.bettermemory.tools;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import java.util.List;
import java.util.stream.Collectors;

import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.models.users.Patient;

public class DatabaseDataHandler {
    public static ObjectId checkIfExists(MongoCollection<Document> collection, Object object) {
        ObjectId objectId;
        if (object instanceof Patient) objectId = checkIfExistsPatient(collection, (Patient) object);
        else if (object instanceof HealthCarePersonnel) objectId = checkIfExistsHealthCarePersonnel(collection, (HealthCarePersonnel) object);
        else if (object instanceof Week) objectId = checkIfExistsWeek(collection, (Week) object);
        else if (object instanceof Day) objectId = checkIfExistsDay(collection, (Day) object);
        else if (object instanceof Activity) objectId = checkIfExistsActivity(collection, (Activity) object);
        else objectId = null;

        return objectId;
    }

    private static ObjectId checkIfExistsPatient(MongoCollection<Document> collection, Patient patient) {
        Document query = new Document("_id", patient.getPatientId());
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }

    private static ObjectId checkIfExistsHealthCarePersonnel(MongoCollection<Document> collection, HealthCarePersonnel healthCarePersonnel) {
        Document query = new Document("_id", healthCarePersonnel.getEmployeeNumber());
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }

    private static ObjectId checkIfExistsWeek(MongoCollection<Document> collection, Week week) {
        List<ObjectId> dayIds = week.getDays().stream().map(
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

    private static ObjectId checkIfExistsDay(MongoCollection<Document> collection, Day day) {
        List<ObjectId> activityIds = day.getActivities().stream().map(
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

    private static ObjectId checkIfExistsActivity(MongoCollection<Document> collection, Activity activity) {
        Document query = new Document("hour", activity.getHour()).append("minutes", activity.getMinutes())
        .append("short_desc", activity.getShortDescription()).append("long_desc", activity.getLongDescription());
        Document result = collection.find(query).first();
        if (result != null) return result.getObjectId("_id");

        return null;
    }
    
}
