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
public class GetYearIntegerTest {

    @Test
    @DisplayName("Get present year.")
    public void getHour() {
        /*
         * Creates a fixed clock which to be used in the PresentYearInteger constructor.
         * The process of mocking the LocalDateTime class looked quite complicated, I therefor 
         * found it simpler to implement a clock object to the PresentYearInteger class in order 
         * to test this in a more predictable manner.
         */
        Clock fixedClock = Clock.fixed(Instant.parse("1945-08-05T12:00:00Z"), ZoneId.of("UTC"));

       //Crates a PresentHourInteger object with the already created fixedClock object.
        GetYearInteger presentYearInteger = new GetYearInteger(fixedClock);

       
        int presentYear = presentYearInteger.getYear(0);

    
        assertEquals(1945, presentYear);
        
    }
}