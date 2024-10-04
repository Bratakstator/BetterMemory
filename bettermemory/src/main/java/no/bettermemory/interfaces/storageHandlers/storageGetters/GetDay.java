package no.bettermemory.interfaces.storageHandlers.storageGetters;

import org.bson.types.ObjectId;
import java.util.List;

import no.bettermemory.models.time.Day;

public interface GetDay {
    /**
     * Returns a Day object using time related parameters.
     * 
     * @param patientId
     * @param year
     * @param weekNumber
     * @param dayName
     * 
     * @return Day
     * 
     * @throws Exception
     * 
     */
    Day getSpecificDay(String patientId, int year, int weekNumber, String dayName) throws Exception;

    /**
     * Returns a Day object using its stored object id.
     * 
     * @param dayId
     * 
     * @return Day
     * 
     * @throws Exception
     * 
     */
    Day getSpecificDayFromObjectId(ObjectId dayId) throws Exception;

    /**
     * Returns a list of objects of type Day using object id.
     * 
     * @param dayIds
     * 
     * @return List
     * 
     * @throws Exception
     * 
     */
    List<Day> getDaysFromObjectId(List<ObjectId> dayIds) throws Exception;

    /**
     * Returns a list of objects of type Day using time related parameters.
     * 
     * @param patientId
     * @param year
     * @param weekNumber
     * 
     * @return List
     * 
     * @throws Exception
     * 
     */
    List<Day> getDaysByWeek(String patientId, int year, int weekNumber) throws Exception;
}
