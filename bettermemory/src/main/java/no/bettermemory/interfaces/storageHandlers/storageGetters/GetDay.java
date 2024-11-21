package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;

import org.bson.types.ObjectId;

import no.bettermemory.models.time.Day;

public interface GetDay {
    
    /**
     *  This method should be used to retrieve a spesific day from data stored in a MongoDB. 
     *  It returns that spesific day based on the parameters.
     *  @param patientId -  All patients in the database are organized based on their patientId.
     *  @param year - The year the day is within 
     *  @param weekNumber - The week number the day is within 
     *  @param dayName - The name of the day it is
     *  @return day
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  getDayFromMongoDB.getSpecific(patientId, year, weekNumber, dayName);}
     */
    Day getSpecific(String patientId, int year, int weekNumber, String dayName) throws Exception;

    /**
     *  This method should be used to retrieve a days by their ObjectID from data stored in a MongoDB. 
     *  It returns that an arraylist of days from MongoDB.
     *  @param dayIds -  A unique ID for a day in MongoDB
     *  @return days
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  getDayFromMongoDB.getSpecific();}
     */
    List<Day> getDaysFromObjectId(List<ObjectId> dayIds) throws Exception;
}
