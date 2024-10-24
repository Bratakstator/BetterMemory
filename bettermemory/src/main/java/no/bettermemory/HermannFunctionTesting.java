package no.bettermemory;


import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import no.bettermemory.models.MicrocontrollerDatabaseBridge.*;


public class HermannFunctionTesting {

    public static void main(String[] args) {


        PresentHourInteger presentHourInteger = new PresentHourInteger();

        System.out.println(presentHourInteger.getHour());

        PresentMinutesInteger presentMinutesInteger = new PresentMinutesInteger();

        System.out.println(presentMinutesInteger.getMinutes());

        PresentWeekInteger presentWeekInteger = new PresentWeekInteger();

        System.out.println(presentWeekInteger.getWeek());

        PresentYearInteger presentYearInteger = new PresentYearInteger();

        System.out.println(presentYearInteger.getYear());

        PresentDayString presentDayString = new PresentDayString(Clock.fixed(Instant.parse("0000-12-25T12:00:00Z"), ZoneId.of("UTC")));

        System.out.println(presentDayString.getDay());



        

        
        
    }

}
