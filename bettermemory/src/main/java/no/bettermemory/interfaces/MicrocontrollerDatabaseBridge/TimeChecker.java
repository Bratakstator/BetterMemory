package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

/**
 * This interface is meant to be used in order to return a 
 * time datatype. The programmer is free to choose the returned 
 * time data type.
 */
public interface TimeChecker<T> {

    /**
     * Should return the present time in a pre defined time data type.
     * @return T
     */
    T getPresentTime(); 
    
}
