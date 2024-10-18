package no.bettermemory;


import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.PresentHourInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.PresentMinutesInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.PresentWeekInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.PresentYearInteger;


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

        
    }

}
