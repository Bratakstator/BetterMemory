package no.bettermemory.models.time;

import no.bettermemory.models.users.Pasient;
import static no.bettermemory.errorMessages.ErrorMessages.toHighWeekNumberError;
import static no.bettermemory.errorMessages.ErrorMessages.toLowWeekNumberError;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public record WeekTest() {
    @Test
    public void testWeekConstructor() {
        ArrayList<Day> days = new ArrayList<>();
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
    
}
