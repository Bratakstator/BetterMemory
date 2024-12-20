package no.bettermemory.models.Micro_DB_Bridge.PresentTimeClasses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetMinutesIntegerTest {

    @Test
    @DisplayName("Get present minutes.")
    public void getMinutes() {
        /*
         * Creates a fixed clock which to be used in the PresentMinutesInteger constructor.
         * The process of mocking the LocalDateTime class looked quite complicated, I therefor 
         * found it simpler to implement a clock object to the PresentMinutesInteger class in order 
         * to test this in a more predictable manner.
         */
        Clock fixedClock = Clock.fixed(Instant.parse("0001-01-01T12:50:00Z"), ZoneId.of("UTC"));

       //Crates a PresentHourInteger object with the already created fixedClock object.
        GetMinutesInteger presentMinutesInteger = new GetMinutesInteger(fixedClock);

       
        int presentMinutes = presentMinutesInteger.getMinutes(0);

    
        assertEquals(50, presentMinutes);
        
    }
}