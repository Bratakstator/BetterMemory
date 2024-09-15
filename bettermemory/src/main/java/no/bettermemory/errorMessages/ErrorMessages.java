package no.bettermemory.errorMessages;

public class ErrorMessages {

    public static String toHighWeekNumberError(int weekNumber){
        return weekNumber + " > 52. " + weekNumber +
                " exeeds the number of weeks in a calender year, and is therefore deemed invalid.";
    }

    public static String toLowWeekNumberError(int weekNumber){
        return weekNumber + " < 1. Week number "+
            " must be a positive whole number bigger or equal to one.";
    }

    public static String dublicateDayInWeekError(String dayName) {
        return "A dublication of the day " + dayName + " has been detected. This is not allowed.";
    }

    public static String notARealDay(String dayName) {
        return "The day: " + dayName + " does not exist.";
    }

}
