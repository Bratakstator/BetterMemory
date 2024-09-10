package no.bettermemory.tools;

import static no.bettermemory.errorMessages.ErrorMessages.toHighWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.toLowWeekNumberError;

/**
 * This class contains a variety of diffrent nice to have controll methodes. Methodes in this 
 * class can be uses insted of creating the same code multiple times diffrent places in the whole 
 * program.
 */
public class TimeControls {

    public static void weekNumberCheck(int weekNumber) throws IllegalArgumentException {
        if (weekNumber < 1 || weekNumber > 52) {
            if(weekNumber > 52){
                throw new IllegalArgumentException(toHighWeekNumberError(weekNumber));  
            } 

            else if (weekNumber < 1){
                throw new IllegalArgumentException(toLowWeekNumberError(weekNumber));
            }
        }

    }
    
}
