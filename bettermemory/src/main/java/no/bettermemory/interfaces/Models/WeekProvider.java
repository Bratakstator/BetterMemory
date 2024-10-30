package no.bettermemory.interfaces.Models;

/**
 * This interface should be implemented tho those class which needs to return a 
 * week value;
 */
public interface WeekProvider<T> {

    /**
     * This method should return a week representation on the specified data value format.
     * @return T
     */
    T getWeek(int minutesInAdvance);
    
}
