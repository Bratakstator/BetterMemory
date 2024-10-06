package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

/**
 * This interface is meant to be used in order to return a 
 * an activity based on some set of time parameters.
 */
public interface TimeBasedActivityRetriever<T> {

    /**
     * Should return the activity in a chosen data format.
     * @param minute
     * @param hour
     * @param dayName
     * @param weekNumber
     * @param year
     * @return T
     * 
     */
    T getActivity(int minute,
                  int hour,
                  String dayName,
                  int weekNumber,
                  int year); 
    
}
