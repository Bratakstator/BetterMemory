package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

/**
 * This interface should be implemented those places where you need 
 * a class to return some activities based on a specified time 
 * interval.
 */
public interface TimeIntervalBasedObjectRetriever<T> {

    /**
     * This method should return a set of activities based on a
     * specified timeInterval and the methods logic should use some 
     * kind of a timeBasedActivityRetriever in order to find those
     * activities.
     * @param timeBasedActivityRetriever -Should be an object that implements the timeBasedActivityRetriever interface
     * @param timeInterval 
     * @return
     */
    T getObjects(int interval);

    
    
}
