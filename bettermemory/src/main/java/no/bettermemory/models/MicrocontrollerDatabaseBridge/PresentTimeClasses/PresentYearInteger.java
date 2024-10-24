package no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses;

import no.bettermemory.interfaces.Models.YearProvider;
import java.time.LocalDateTime;
import java.time.Clock;

/**
 * This class is meant to provide the current year. However, it is possible to
 * set the clock in order to manually set the time of choosing or to represent 
 * time with an offset.
 * 
 * @see Clock
 * 
 * @author 
 * Hermann Mjelde Hamnnes
 */
public class PresentYearInteger implements YearProvider<Integer> {

    private final Clock clock;

    /**
     * If no Clock object was passed to the constructor, the clock will
     * by default be set to the systemDefaultZone.
     */
    public PresentYearInteger() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * You can pass a clock object to this method in order to manipulate/choose 
     * a different Clock than the systemDefaultZone.
     * @param Clock
     */
    public PresentYearInteger(Clock clock) {
        this.clock = clock;
    }


    @Override
    /**
     * This class will provide an integer which represent the current or a predefined 
     * year.
     */
    public Integer getYear() {
        return  LocalDateTime.now(clock).getYear();
    }
    
}