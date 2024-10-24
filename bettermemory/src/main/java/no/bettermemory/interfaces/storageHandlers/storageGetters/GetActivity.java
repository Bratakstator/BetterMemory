package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public interface GetActivity {
    HashMap<ObjectId, Activity> getActivitiesAtMinute(
        String patientId, int year, int weekNumber, String dayName, int hour, int minutes
    ) throws Exception;

    HashMap<ObjectId, Activity> getActivitiesAtHour(
        String patientId, int year, int weekNumber, String dayName, int hour
    ) throws Exception;
    
    HashMap<ObjectId, Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;
}
