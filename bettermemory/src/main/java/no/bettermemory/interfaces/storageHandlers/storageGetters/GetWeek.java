package no.bettermemory.interfaces.storageHandlers.storageGetters;

import no.bettermemory.models.time.Week;

public interface GetWeek {
    
    /**
     *  This method should be used to retrieve a spesific week based on the parameters from data stored in a MongoDB. 
     *  It returns it return that spesific week
     *  @param pasientId - A unique ID for the person this spesific week belongs to
     *  @param year - The spesific year this spesific week occurs
     *  @param weekNumber - The weekNumber this spesific week is
     *  @return week
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  GetWeekFromMongoDB.getSpecificWeek(patientId, year, weekNumber);}
     */
    Week getSpecificWeek(String patientId, int year, int weekNumber) throws Exception;
}
