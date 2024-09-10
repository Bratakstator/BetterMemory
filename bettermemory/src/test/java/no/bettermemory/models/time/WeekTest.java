package no.bettermemory.models.time;

import no.bettermemory.models.users.Pasient;

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
        assertEquals(1, week.getWeeknumber());
        assertEquals(2024, week.getYear());
        assertEquals(days, week.getDays());
        assertEquals(pasient, week.getPasient());  
    }

    @Test 
    public void testInvalidWeekNumberHigh(){
        ArrayList<Day> days = new ArrayList<>();
        Pasient pasient = new Pasient();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Week(53, 2024, days, pasient);
        });

        assertEquals(53 + " > 52. " + 53 +
        "exeed the number of weeks in a calender year, and is therefore deemed invalid.", exception.getMessage());
    }

    @Test 
    public void testInvalidWeekNumberLow(){
        ArrayList<Day> days = new ArrayList<>();
        Pasient pasient = new Pasient();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Week(0, 2024, days, pasient);
        });

        assertEquals(0 + " < 1. Week number "+
        "must be a positive whole number bigger or equal to one.", exception.getMessage());
        


        



    }
    
}
