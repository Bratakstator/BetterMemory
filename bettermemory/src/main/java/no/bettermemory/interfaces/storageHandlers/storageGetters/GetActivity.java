package no.bettermemory.interfaces.storageHandlers.storageGetters;

import org.bson.types.ObjectId;
import java.util.List;

import no.bettermemory.models.activity.Activity;

/**
 * This interface is used to extract data to be used to create one instance of or a list of objects of type Activity.
 * @author Joakim Klemsdal Bøe
 * @version 1.0
 */
public interface GetActivity {
    /**
     * This returns an object of Activity using attributes related to time.
     * @param patientId - Is the Id of the patient.
     * @param year - Is the year the plan takes activity in.
     * @param weekNumber - Is the number of the week the activity takes place in.
     * @param dayName - Is the day the activity takes place in.
     * @param hour - Is the hour the activity takes place in.
     * @param minute - Is the minute the activity takes place in.
     * 
     * @return Activity
     * @throws Exception
     * 
     */
    Activity getSpecificActivity(String patientId, int year, int weekNumber, String dayName, int hour, int minute) throws Exception;

    /**
     * This returns an object of Activity using its object id.
     * This method is meant to be used by other database-extraction methods.
     * 
     * @param objectId - The object id used in the database.
     * 
     * @return Activity
     * @throws Exception
     * 
     */
    Activity getSpecificActivityFromObjectId(ObjectId activityId) throws Exception;

    /**
     * This returns a list of activities using a list of object ids.
     * This method is meant to be used by other database-extraction methods.
     * 
     * @param objectIds - A list of Object ids used in the database.
     * 
     * @return List<Activity>
     * @throws Exception
     * 
     */
    List<Activity> getActivitiesFromObjectId(List<ObjectId> activityIds) throws Exception;

    /**
     * This returns a list of activites using attributes related to time, will return all activites happening at given hour.
     * 
     * @param patientId
     * @param year
     * @param weekNumber
     * @param dayName
     * @param hour
     * 
     * @return List<Activity>
     * 
     * @throws Exception
     * 
     */
    List<Activity> getActivitiesByHour(String patientId, int year, int weekNumber, String dayName, int hour) throws Exception;
}
