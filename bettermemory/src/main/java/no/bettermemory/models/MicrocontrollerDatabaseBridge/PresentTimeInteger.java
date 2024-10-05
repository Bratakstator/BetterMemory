package no.bettermemory.models.MicrocontrollerDatabaseBridge;

import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.interfaces.Models.MinutesProvider;
import no.bettermemory.interfaces.Models.WeekProvider;
import no.bettermemory.interfaces.Models.YearProvider;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.*;
import java.time.LocalDateTime;

public class PresentTimeInteger implements HourProvider<Integer>, MinutesProvider<Integer>,
                                            WeekProvider<Integer>, YearProvider<Integer>,
                                            TimeChecker<LocalDateTime>  {

    public PresentTimeInteger(){
        
    }

    @Override 
    public Integer getMinutes(){
        return 0;
    }

    @Override
    public Integer getHour() {
        return null;
    }

    @Override
    public Integer getWeek() {
        return null;
    }

    @Override 
    public Integer getYear() {
        return null;
    }

    @Override
    public LocalDateTime getPresentTime() {
        return null;
    }
    
}
