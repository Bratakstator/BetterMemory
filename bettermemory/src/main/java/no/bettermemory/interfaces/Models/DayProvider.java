package no.bettermemory.interfaces.Models;

/**
 * This interface should be implemented in those class which needs to return a 
 * day value;
 */
public interface DayProvider<T> {

    /**
    * This method should return a day representation on the specified data value format.
    * @return T
    */
    T getDay(int minutesInAdvance);
    
}
