package no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses;

import no.bettermemory.interfaces.Models.WeekProvider;
import java.time.LocalDateTime;
import java.time.Clock;
import java.util.Locale;
import java.time.temporal.WeekFields;

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
public class PresentWeekInteger implements WeekProvider<Integer> {

    private final Clock clock;

    /**
     * If no Clock object was passed to the constructor, the clock will
     * by default be set to the systemDefaultZone.
     */
    public PresentWeekInteger() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * You can pass a clock object to this method in order to manipulate/choose 
     * a different Clock than the systemDefaultZone.
     * @param Clock
     */
    public PresentWeekInteger(Clock clock) {
        this.clock = clock;
    }


    @Override
    /**
     * This class will provide an integer which represent the current or a predefined 
     * weekNumber.
     */
    public Integer getWeek() {
        return  LocalDateTime.now(clock) //sets the clock for this the returned now value.
                             .get(WeekFields.of(Locale.getDefault()) 
                             .weekOfWeekBasedYear()); /*Sets the returned week number to 
                                                  be based on the given year in the returned
                                                  now value.
                                                */
        
    }
    
}
