package no.bettermemory.tools;

/**
 * This class contains a variety of diffrent nice to have controll methodes. Methodes in this 
 * class can be uses insted of creating the same code multiple times diffrent places in the whole 
 * program.
 */
public class TimeControls {

    public static void weekNumberCheck(int weeknumber) throws IllegalArgumentException {
        if (weeknumber < 1 || weeknumber > 52) {
            if(weeknumber > 52){
                throw new IllegalArgumentException(weeknumber + " > 52. " + weeknumber +
                "exeed the number of weeks in a calender year, and is therefore deemed invalid.");  
            } 

            else if (weeknumber < 1){
                throw new IllegalArgumentException(weeknumber + " < 1. Week number "+
                "must be a positive whole number bigger or equal to one.");
            }
        }

    }
    
}
