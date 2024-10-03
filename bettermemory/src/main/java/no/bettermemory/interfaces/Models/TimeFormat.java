
package no.bettermemory.interfaces.Models;

/**
 * 
 * This interface should be implemented when creating classes for defining 
 * a time format. 
 */
interface TimeFormat<T> {

    /**
     * This method should return an datatype which represent hour.
     */
    T getHour();

    /**
     * This method should return an datatype which represent minutes.
     */
    T getMinutes();


    /**
     * This method should return an datatype which represent weekNumber.
     */
    T getWeekNumber();

    /**
     * This method should return an datatype which represent year minutes.
     */
    T getYear();



    
}