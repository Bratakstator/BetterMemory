package no.bettermemory.models.time;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static no.bettermemory.errorMessages.ErrorMessages.notARealDay;



import no.bettermemory.models.activity.Activity;


public class DayTest {

    @Test
    public void testDayConstructor(){
        ArrayList<Activity> activities = new ArrayList<>();

        Day day = new Day("Monday", activities);

        assertNotNull(day);
        assertEquals("monday", day.getDayName());
        assertEquals(activities, day.getActivities());

    }

    @Test 
    public void testInvalidDayName(){
        ArrayList<Activity> activities = new ArrayList<>();
        String dayName = "smonday";


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Day(dayName, activities);
        });

        assertEquals(notARealDay(dayName), exception.getMessage());

    }
    
}
