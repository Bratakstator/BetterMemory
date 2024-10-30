package no.bettermemory;


import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetHourInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetMinutesInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetWeekInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetYearInteger;


public class HermannFunctionTesting {

    public static void main(String[] args) {


        GetHourInteger presentHourInteger = new GetHourInteger();

        System.out.println(presentHourInteger.getHour(0));

        GetMinutesInteger presentMinutesInteger = new GetMinutesInteger();

        System.out.println(presentMinutesInteger.getMinutes(0));

        GetWeekInteger presentWeekInteger = new GetWeekInteger();

        System.out.println(presentWeekInteger.getWeek(0));

        GetYearInteger presentYearInteger = new GetYearInteger();

        System.out.println(presentYearInteger.getYear(0));

        
    }

}
