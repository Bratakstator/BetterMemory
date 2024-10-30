package no.bettermemory.interfaces.Models;

/**
 * This interface should be implemented tho those class which needs to return a 
 * hour value;
 */
public interface HourProvider<T> {

    /**
    * This method should return a hour representation on the specified data value format.
    * @return T
    */
    T getHour(int minutesInAdvance);

    
    
}
