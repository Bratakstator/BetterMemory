package no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses;

import no.bettermemory.interfaces.Models.HourProvider;
import java.time.LocalDateTime;
import java.time.Clock;

/**
 * This class is meant to provide the current hour. However, it is possible to
 * set the clock in order to manually set the time of choosing or to represent 
 * time with an offset.
 * 
 * @see Clock
 * 
 * @author 
 * Hermann Mjelde Hamnnes
 */
public class GetHourInteger implements HourProvider<Integer> {

    private final Clock clock;

    /**
     * If no Clock object was passed to the constructor, the clock will
     * by default be set to the systemDefaultZone.
     */
    public GetHourInteger() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * You can pass a clock object to this method in order to manipulate/choose 
     * a different Clock than the systemDefaultZone.
     * @param Clock
     */
    public GetHourInteger(Clock clock) {
        this.clock = clock;
    }


    @Override
    /**
     * This class will provide an integer which represent the current or a predefined 
     * hour.
     */
    public Integer getHour(int minutesInAdvance) {
        return LocalDateTime.now(clock).plusMinutes(minutesInAdvance).getHour();
        
    }
    
}
