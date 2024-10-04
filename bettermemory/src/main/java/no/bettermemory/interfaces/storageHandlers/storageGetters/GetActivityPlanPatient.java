package no.bettermemory.interfaces.storageHandlers.storageGetters;

import no.bettermemory.models.time.Week;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.activity.Activity;


/**
 * This interface should be used as a template for building a 
 * class for extrackting activityPlan information from a database and 
 * in to the system. 
 */
public interface GetActivityPlanPatient {

    /**
     * This methode should take the following parameters patientId and weekNumber
     * and by accessing the database, exstract the correct values to create and return a 
     * Week object for a given patient.
     * @param patientId
     * @param weekNumber
     * @param year
     * @return Week
     * @throws Exception 
     */
    Week getWeekPlan(String patientId, int weekNumber, int year) throws Exception;

    /**
     * This methode should take the following parameters patientId, weekNumber, dayName
     * and by accessing the database, exstract the correct values to create and return a 
     * Day object for a given patient.
     * @param patientId
     * @param weekNumber
     * @param year
     * @param dayName
     * @return Day 
     * @throws Exception 
     */
    Day getDayPlan(String patientId, int weekNumber, int year, String dayName) throws Exception;

    /**
     * This methode should take the following parameters patientId, weekNumber, dayName, 
     * hour and minutes and by accessing the database, exstract the correct values to
     * create and return a Activity object for a given patient.
     * @param patientId
     * @param weekNumber
     * @param year
     * @param dayName
     * @param hour
     * @param minutes
     * @return Activity 
     * @throws Exception 
     */
    Activity getActivity(String patientId, int weekNumber, int year, String dayName, int hour, 
                                int minutes) throws Exception;



}