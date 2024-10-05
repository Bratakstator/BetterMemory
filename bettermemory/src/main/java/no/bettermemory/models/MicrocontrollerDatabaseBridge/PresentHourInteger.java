package no.bettermemory.models.MicrocontrollerDatabaseBridge;
import no.bettermemory.interfaces.Models.HourProvider;
import java.time.LocalDateTime;
import java.time.Clock;


public class PresentHourInteger implements HourProvider<Integer> {

    private final Clock clock;

    public PresentHourInteger(Clock clock) {
        this.clock = clock;
    }


    @Override
    public Integer getHour() {

        return (Integer) LocalDateTime.now(clock).getHour();
        
    }
    
}
