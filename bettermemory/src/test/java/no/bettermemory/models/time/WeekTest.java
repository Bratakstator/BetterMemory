package no.bettermemory.models.time;

import no.bettermemory.models.users.Pasient;
import static no.bettermemory.errorMessages.ErrorMessages.toHighWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.toLowWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.dublicateDayInWeekError;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public record WeekTest() {
    @Test
    public void testWeekConstructor() {
        ArrayList<Day> days = new ArrayList<>();
        days.add( new Day("Monday", new ArrayList<>()));
        days.add( new Day("Tuesday", new ArrayList<>()));
        days.add( new Day("WednesdaY", new ArrayList<>()));
        days.add( new Day("Thursday", new ArrayList<>()));
        days.add( new Day("Friday", new ArrayList<>()));
        days.add( new Day("Saturday", new ArrayList<>()));
        days.add( new Day("Sunday", new ArrayList<>()));

        Pasient pasient = new Pasient();

        Week week = new Week(1, 2024, days, pasient);

        assertNotNull(week);
        assertEquals(1, week.getWeekNumber());
        assertEquals(2024, week.getYear());
        assertEquals(days, week.getDays());
        assertEquals(pasient, week.getPasient());  
    }

    @Test 
    public void testInvalidWeekNumberHigh(){
        ArrayList<Day> days = new ArrayList<>();
        Pasient pasient = new Pasient();
        int weekNumber = 53;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Week(weekNumber, 2024, days, pasient);
        });

        assertEquals(toHighWeekNumberError(weekNumber), exception.getMessage());
    }

    @Test 
    public void testInvalidWeekNumberLow(){
        ArrayList<Day> days = new ArrayList<>();
        Pasient pasient = new Pasient();
        int weekNumber = 0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Week(0, 2024, days, pasient);
        });

        assertEquals(toLowWeekNumberError(weekNumber), exception.getMessage());
    }

    @Test 
    public void testDublicatedDayInWeek(){
        ArrayList<Day> days = new ArrayList<>();
        days.add( new Day("monday", new ArrayList<>()));
        days.add( new Day("friday", new ArrayList<>()));
        days.add( new Day("monday", new ArrayList<>()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Week(30, 2024, days, new Pasient());
        });

        assertEquals(dublicateDayInWeekError("monday"), exception.getMessage());
    }

    
}
