package no.bettermemory.models.MicrocontrollerDatabaseBridge;

import java.time.Clock;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDateTime;

import no.bettermemory.interfaces.Models.DayProvider;

/**
 * This class is meant to provide the name of the current day. However, it is possible to
 * set the clock in order to manually set the time of choosing or to represent 
 * time with an offset.
 * 
 * @see Clock
 * 
 * @author 
 * Hermann Mjelde Hamnnes
 */
public class PresentDayString implements DayProvider<String>{

    private final Clock clock;

    
    /**
     * If no Clock object was passed to the constructor, the clock will
     * by default be set to the systemDefaultZone.
     */
    public PresentDayString() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * You can pass a clock object to this method in order to manipulate/choose 
     * a different Clock than the systemDefaultZone.
     * @param Clock
     */
    public PresentDayString(Clock clock) {
        this.clock = clock;
    }

    @Override
    /**
     * This method will return the name of the day for ether the current time provided by the system
     * or based on the time set by the clock.
     */
    public String getDay() {
        return LocalDateTime.now(clock).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

    }
    
}
