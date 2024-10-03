package no.bettermemory.models.activity;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    @Test
    public void testActivityConstructor(){

        Activity activity = new Activity(10, 20, "Visit", "Your grand childeren are going to visit you.", true);

        assertNotNull(activity);
        assertEquals(10, activity.getHour());
        assertEquals(20, activity.getMinutes());
        assertEquals("Visit", activity.getShortDescription());
        assertEquals("Your grand childeren are going to visit you.", activity.getLongDescription());
        assertEquals("10:20", activity.getTime());

    }

    @Test 
    public void testInvalidMinuteInput(){

         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Activity(20, 60, "Something", "More", false);
        });

        assertEquals("The minute value that was enterd was not accsepted.", exception.getMessage());
  
    }

    @Test
    public void testInvalidHourInput(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
           new Activity(25, 30, "Something", "More", false);
       });

       assertEquals("The hour value that was enterd was not accsepted.", exception.getMessage());
    }

    



    
}
