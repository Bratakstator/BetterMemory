package no.bettermemory.models.TestRun;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.ArrayHandlers.ArrayDTOHandler;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers.ActivityQueHandler;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers.ActivityQueInserter;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers.ActivityQueStateChecker;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.TimeDatabaseRetrievers.TimeIntervalBasedActivityRetriever;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetDayString;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetHourInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetMinutesInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetWeekInteger;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.PresentTimeClasses.GetYearInteger;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.storageHandlers.databaseExtraction.GetActivityFromMongoDB;
import no.bettermemory.models.storageHandlers.databaseInsertions.InsertActivityToMongoDB;
import no.bettermemory.models.storageHandlers.databaseInsertions.InsertDayToMongoDB;
import no.bettermemory.models.storageHandlers.databaseInsertions.InsertPatientToMongoDB;
import no.bettermemory.models.storageHandlers.databaseInsertions.InsertWeekToMongoDB;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.ArduinoActivityCommunicator;
import no.bettermemory.tools.DatabaseConnections;

public class TestRun {
    public static void main(String[] args) {
        MongoClient client = DatabaseConnections.getMongodbClientInfo();
        MongoDatabase database = DatabaseConnections.getUsersDatabase(client);

        database.drop();

        Patient patient = new Patient(
            "1324790",
            "John",
            "Doe"
        );

        LocalDateTime date = LocalDateTime.now().plusMinutes(20);

        ArrayList<Activity> activityList = new ArrayList<>();

        Activity activity = new Activity(
            date.getHour(),
            date.getMinute(),
            "Take a walk",
            "Take a walk outside.",
            false
        );

        activityList.add(activity);

        Day day = new Day(
            date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            activityList
        );

        ArrayList<Day> dayList = new ArrayList<>(Arrays.asList(day));

        Week week = new Week(
            date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()),
            date.getYear(),
            dayList,
            patient
        );

        InsertActivityToMongoDB insertActivity = new InsertActivityToMongoDB(client);
        InsertDayToMongoDB insertDay = new InsertDayToMongoDB(client, insertActivity);
        InsertWeekToMongoDB insertWeek = new InsertWeekToMongoDB(client, insertDay);
        InsertPatientToMongoDB insertPatient = new InsertPatientToMongoDB(client);

        try {
            insertWeek.saveObject(week);
            insertPatient.saveObject(patient);
        } catch (Exception e) {
            System.err.println(e);
        }

        ActivityDTO[] activityDTOs = new ActivityDTO[1];
        ArrayDTOHandler<ActivityDTO> arrayDTOHandler = new ArrayDTOHandler<>(activityDTOs);

        ActivityQueStateChecker stateChecker = new ActivityQueStateChecker(arrayDTOHandler, insertActivity);

        GetYearInteger getYearInteger = new GetYearInteger();
        GetWeekInteger getWeekInteger = new GetWeekInteger();
        GetDayString getDayString = new GetDayString();
        GetHourInteger getHourInteger = new GetHourInteger();
        GetMinutesInteger getMinutesInteger = new GetMinutesInteger();

        GetActivityFromMongoDB getActivityFromMongoDB = new GetActivityFromMongoDB(client);

        TimeIntervalBasedActivityRetriever activityRetriever = new TimeIntervalBasedActivityRetriever(
            getMinutesInteger,
            getHourInteger,
            getDayString, 
            getWeekInteger, 
            getYearInteger, 
            patient.getPatientId(), 
            getActivityFromMongoDB
        );

        ActivityQueInserter queInserter = new ActivityQueInserter(activityRetriever, arrayDTOHandler);

        ActivityQueHandler runnableQueHandler = new ActivityQueHandler(stateChecker, queInserter);
        Thread queHandlerThread = new Thread(runnableQueHandler);
        queHandlerThread.start();

        ArduinoActivityCommunicator runnableArduino = new ArduinoActivityCommunicator(arrayDTOHandler);
        Thread arduinoThread = new Thread(runnableArduino);
        arduinoThread.start();

        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runnableQueHandler.stop();
        runnableArduino.stop();

        queHandlerThread.interrupt();
        arduinoThread.interrupt();

        try {
            queHandlerThread.join();
            arduinoThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}