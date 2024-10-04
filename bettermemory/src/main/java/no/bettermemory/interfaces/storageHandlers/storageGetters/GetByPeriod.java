package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;

public interface GetByPeriod<T> {
    /**
     * Used to return one instance of an object.
     * 
     * @param patientId - The id of the patient the time object belongs to.
     * @param year - The year the time object happens in.
     * @param weekNumber - The week the time object happens in.
     * @param dayName - The day the time object happens in.
     * @param time - An Array which will contain either just hour; or hour and minute,
     * if the Array contains more than needed it will be ignored
     * 
     * @throw Exception
     * 
     */
    T getSpecific(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception;

    /**
     * Used to return one instance of an object.
     * 
     * @param patientId - The id of the patient the time object belongs to.
     * @param year - The year the time object happens in.
     * @param weekNumber - The week the time object happens in.
     * @param dayName - The day the time object happens in.
     * @param time - An Array which will contain either just hour; or hour and minute,
     * if the Array contains more than needed it will be ignored
     * 
     * @throw Exception
     * 
     */
    List<T> getListByPeriod(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception; 
}
