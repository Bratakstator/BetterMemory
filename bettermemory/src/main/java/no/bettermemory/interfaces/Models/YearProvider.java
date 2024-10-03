package no.bettermemory.interfaces.Models;

/**
 * This interface should be implemented tho those class which needs to return a 
 * year value;
 */
public interface YearProvider<T> {

    /**
     * This method should return a year representation on the specified data value format.
     * @return T
     */
    T getYear();
    
}
