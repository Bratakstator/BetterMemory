package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

/**
 * This interface is meant to be used in order to return a 
 * an activity based on some set of time parameters.
 */
public interface TimeBasedObjectRetriever<T, U> {

    /**
     * Should return the activity in a chosen data format.
     * @return T
     * 
     */
    T getObject(); 
    
}
