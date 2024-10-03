
package no.bettermemory.interfaces.Models;

/**
 * 
 * This interface should be implemented when creating classes for defining 
 * a time format. 
 */
interface TimeFormat<T, U> {

    /**
     * This method should return a time representative format of choosing. You, the programmer
     * will need to specify what time format you want to convert from.
     * @param timeData
     * @return T
     */
    T convertToTimeFormat(U inputTimeFormat);


    
}