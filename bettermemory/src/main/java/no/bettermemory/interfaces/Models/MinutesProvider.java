package no.bettermemory.interfaces.Models;

/**
 * This interface should be implemented tho those class which needs to return a 
 * minutes value;
 */
public interface MinutesProvider<T> {

    /**
    * This method should return a minutes representation on the specified data value format.
    * @return T
    */
    T getMinutes();
    
}
