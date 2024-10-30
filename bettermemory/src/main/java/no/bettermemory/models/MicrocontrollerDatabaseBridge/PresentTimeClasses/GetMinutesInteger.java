package no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses;

import no.bettermemory.interfaces.Models.MinutesProvider;
import java.time.LocalDateTime;
import java.time.Clock;

/**
 * This class is meant to provide the current minutes. However, it is possible to
 * set the clock in order to manually set the time of choosing or to represent 
 * time with an offset.
 * 
 * @see Clock
 * 
 * @author 
 * Hermann Mjelde Hamnnes
 */
public class GetMinutesInteger implements MinutesProvider<Integer> {

    private final Clock clock;

    /**
     * If no Clock object was passed to the constructor, the clock will
     * by default be set to the systemDefaultZone.
     */
    public GetMinutesInteger() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * You can pass a clock object to this method in order to manipulate/choose 
     * a different Clock than the systemDefaultZone.
     * @param Clock
     */
    public GetMinutesInteger(Clock clock) {
        this.clock = clock;
    }


    @Override
    /**
     * This class will provide an integer which represent the current or a predefined 
     * minutes.
     */
    public Integer getMinutes(int minutesInAdvance) {
        return  LocalDateTime.now(clock).plusMinutes(minutesInAdvance).getMinute();
        
    }
    
}
