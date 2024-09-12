package no.bettermemory.tools;

import static no.bettermemory.errorMessages.ErrorMessages.toHighWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.toLowWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.dublicateDayInWeekError;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import no.bettermemory.models.time.Day;

/**
 * This class contains a variety of diffrent nice to have controll methodes. Methodes in this 
 * class can be uses insted of creating the same code multiple times diffrent places in the whole 
 * program.
 */
public class TimeControls {

    static final String[] DAYS_OF_THE_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * Tis methode can be used to check that a given week number actully exist.
     * @param weekNumber
     * @throws IllegalArgumentException
     */
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

    public static boolean mustBeeARealDay(String dayName){
        for (String day : DAYS_OF_THE_WEEK) {
            if(day.equalsIgnoreCase(dayName)){
                return true;
            }
        }
        return false;
    }

    /**
     * This methode ensures that any dublication of days in a week gets detected.
     * @param days
     * @throws IllegalArgumentException
     */
    public static void canOnlyBeeOneOfTheSameDay(ArrayList<Day> days) throws IllegalArgumentException {
        Set<String> daysInWeek = new HashSet<>();
        for (Day day : days){
            if(!daysInWeek.add(day.getDayName().toLowerCase())) {
                throw new IllegalArgumentException(dublicateDayInWeekError(day.getDayName()));
            }
        }
    }
    
    
}
