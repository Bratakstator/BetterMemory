package no.bettermemory.interfaces.storageHandlers.storageGetters;

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
