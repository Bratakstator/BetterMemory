package no.bettermemory.models.activity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    @Test
    public void testActivityConstructor(){

        Activity activity = new Activity(10, 20, "Visit", "Your grand childeren are going to visit you.");

        assertNotNull(activity);
        assertEquals(10, activity.getHour());
        assertEquals(20, activity.getMinutes());
        assertEquals("Visit", activity.getShortDescription());
        assertEquals("Your grand childeren are going to visit you.", activity.getLongDescription());
        assertEquals("10:20", activity.getTime());

    }


    
}
