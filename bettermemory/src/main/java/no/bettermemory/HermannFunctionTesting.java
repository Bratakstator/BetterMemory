package no.bettermemory;


import no.bettermemory.models.storageHandlers.databaseExtraction.GetUserFromMongoDb;
import no.bettermemory.models.users.CloseRelative;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.models.users.Patient;
import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.*;
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
