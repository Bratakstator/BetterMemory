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
public class GetHourIntegerTest {

    @Test
    @DisplayName("Get present hour.")
    public void getHour() {
        /*
         * Creates a fixed clock which to be used in the PresentHourInteger constructor.
         * The process of mocking the LocalDateTime class looked quite complicated, I therefor 
         * found it simpler to implement a clock object to the PresentHourInteger class in order 
         * to test this in a more predictable manner.
         */
        Clock fixedClock = Clock.fixed(Instant.parse("0001-01-01T12:00:00Z"), ZoneId.of("UTC"));

       //Crates a PresentHourInteger object with the already created fixedClock object.
        GetHourInteger presentHourInteger = new GetHourInteger(fixedClock);

       
        int presentHour = presentHourInteger.getHour(0);

    
        assertEquals(12, presentHour);
        
    }
}