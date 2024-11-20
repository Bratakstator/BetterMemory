package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;
import no.bettermemory.models.activity.Activity;

public interface GetActivity {
    
    /**
     *  This method should be used for creating an activitylist from data stored in a MongoDB. 
     *  It returns an array of every activity at present time
     *  @param activityToReceive -  This DTO includes patientId, year, weekNumber, dayName, hour, minutes
     *  @return ActivityDTO[]
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  getActivityFromMongoDB.getActivitiesAtMinute(activityToReceive);}
     */
    ActivityDTO[] getActivitiesAtMinute(ActivityToReceiveDTO activityToReceive) throws Exception;

    /**
     * This method should be used for to retrieve activities by their ObjectId from MongoDB.
     * It returns a activities for that ObjectId.
     * @param activityIds - A unique ID for an activity in MongoDB
     * @return activities
     * @code
     * This is an example of how you can use this method:
     * <pre>{@code  getActivityFromMongoDB.getActibitiesFromMongoDB(activityIds);}
     */
    Map<ObjectId, Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;
}
