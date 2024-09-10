package no.bettermemory.errorMessages;

public class ErrorMessages {

    public static String toHighWeekNumberError(int weekNumber){
        return weekNumber + " > 52. " + weekNumber +
                "exeed the number of weeks in a calender year, and is therefore deemed invalid.";
    }

    public static String toLowWeekNumberError(int weekNumber){
        return weekNumber + " < 1. Week number "+
            "must be a positive whole number bigger or equal to one.";
    }
    
    
}
