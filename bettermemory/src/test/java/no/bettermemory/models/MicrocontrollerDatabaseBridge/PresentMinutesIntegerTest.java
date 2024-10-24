package no.bettermemory.models.MicrocontrollerDatabaseBridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.PresentMinutesInteger;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PresentMinutesIntegerTest {

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
        PresentMinutesInteger presentMinutesInteger = new PresentMinutesInteger(fixedClock);

       
        int presentMinutes = presentMinutesInteger.getMinutes();

    
        assertEquals(50, presentMinutes);
        
    }
}