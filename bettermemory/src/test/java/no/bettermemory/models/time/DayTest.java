package no.bettermemory.models.time;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import no.bettermemory.models.activity.Activity;


public class DayTest {

    @Test
    public void testDayConstructor(){
        ArrayList<Activity> activities = new ArrayList<>();

        Day day = new Day("Monday", activities);

        assertNotNull(day);
        assertEquals("Monday", day.getDayName());
        assertEquals(activities, day.getActivities());

    }
    
}
