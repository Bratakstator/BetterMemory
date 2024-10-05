package no.bettermemory.models.MicrocontrollerDatabaseBridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PresentHourIntegerTest {

    @Test
    @DisplayName("Get present hour.")
    public void getHour() {
        
        Clock fixedClock = Clock.fixed(Instant.parse("2023-10-01T12:00:00Z"), ZoneId.of("UTC"));

       
        PresentHourInteger presentHourInteger = new PresentHourInteger(fixedClock);

       
        int presentHour = presentHourInteger.getHour();

    
        assertEquals(12, presentHour);
        
    }
}