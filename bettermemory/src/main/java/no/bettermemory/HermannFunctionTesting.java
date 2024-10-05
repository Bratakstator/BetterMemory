package no.bettermemory;


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



        

        
        
    }

}
