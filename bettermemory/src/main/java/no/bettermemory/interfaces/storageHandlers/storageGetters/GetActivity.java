package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public interface GetActivity {
    List<Activity> getActivitiesAtMinute(String patientId, int year, int weekNumber, String dayName, int hour, int minutes) throws Exception;
    List<Activity> getActivitiesAtHour(String patientId, int year, int weekNumber, String dayName, int hour) throws Exception;
    List<Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;
}
