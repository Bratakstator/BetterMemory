package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;
import no.bettermemory.models.activity.Activity;

public interface GetActivity {
    ActivityDTO[] getActivitiesAtMinute(ActivityToReceiveDTO activityToReceive) throws Exception;

    /* Been thinking of removing this
    HashMap<ObjectId, Activity> getActivitiesAtInterval(
        String patientId, int year, int weekNumber, String dayName, int currentHour, int currentMinutes, int interval
    ) throws Exception;
     */
    
    Map<ObjectId, Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;
}
