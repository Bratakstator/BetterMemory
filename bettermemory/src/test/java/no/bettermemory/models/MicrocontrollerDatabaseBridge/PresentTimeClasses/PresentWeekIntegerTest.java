package no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PresentWeekIntegerTest {

    @Test
    @DisplayName("Get present week.")
    public void getWeek() {
        /*
         * Creates a fixed clock which to be used in the PresentWeekInteger constructor.
         * The process of mocking the LocalDateTime class looked quite complicated, I therefor 
         * found it simpler to implement a clock object to the PresentWeekInteger class in order 
         * to test this in a more predictable manner.
         */
        Clock fixedClock = Clock.fixed(Instant.parse("0001-01-01T12:50:00Z"), ZoneId.of("UTC"));

       //Crates a PresentHourInteger object with the already created fixedClock object.
        GetWeekInteger presentWeekInteger = new GetWeekInteger(fixedClock);

       
        int presentWeek = presentWeekInteger.getWeek();

    
        assertEquals(1, presentWeek);
        
    }
}