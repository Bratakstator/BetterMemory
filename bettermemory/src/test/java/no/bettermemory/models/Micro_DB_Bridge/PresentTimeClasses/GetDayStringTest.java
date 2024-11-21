package no.bettermemory.models.Micro_DB_Bridge.PresentTimeClasses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.models.Micro_DB_Bridge.PresentTimeClasses.GetDayString;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetDayStringTest {

    @Test
    @DisplayName("Get present Day.")
    public void getDay() {
        /*
         * Creates a fixed clock which to be used in the PresentHourInteger constructor.
         * The process of mocking the LocalDateTime class looked quite complicated, I therefor 
         * found it simpler to implement a clock object to the PresentHourInteger class in order 
         * to test this in a more predictable manner.
         */
        Clock fixedClock = Clock.fixed(Instant.parse("0000-12-25T12:00:00Z"), ZoneId.of("UTC"));

       //Crates a PresentDayString object with the already created fixedClock object.
        GetDayString presentDayString = new GetDayString(fixedClock);

       
        String presentDay = presentDayString.getDay(0);

    
        assertEquals("Monday", presentDay);

        System.out.println("Jesus was born on a monday.");
        
    }
}
