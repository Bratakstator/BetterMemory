package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public interface GetActivity {
    HashMap<ObjectId, Activity> getActivitiesAtMinute(
        String patientId, int year, int weekNumber, String dayName, int hour, int minutes
    ) throws Exception;

    HashMap<ObjectId, Activity> getActivitiesAtInterval(
        String patientId, int year, int weekNumber, String dayName, int currentHour, int currentMinutes, int interval
    ) throws Exception;
    
    HashMap<ObjectId, Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;
}
