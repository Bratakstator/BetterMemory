package no.bettermemory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.storageHandlers.ToMongoDB;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;

public class JoakimFunctionTesting {
    public static void main(String[] args) {
        Activity activity1 = new Activity(8, 0, "Aktivitet", "En Aktivitet beskrivelse");
        Activity activity2 = new Activity(10, 0, "Aktivitet", "En Aktivitet beskrivelse");

        ArrayList<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2));

        Day day1 = new Day("monday", activities);
        Day day2 = new Day("tuesday", activities);

        ArrayList<Day> days = new ArrayList<>(Arrays.asList(day1, day2));

        HealthCarePersonnel healthCarePersonnel = new HealthCarePersonnel("789124691", "John", "Doe");

        Patient patient1 = new Patient("246189", "Jane", "Doe");
        Patient patient2 = new Patient("269185", "Jack", "Doe");

        patient2.addCloseRelative("Jack", "Doe jr.");

        Set<String> connectedPatients = new HashSet<String>(Arrays.asList(patient1.getPatientId(), patient2.getPatientId()));
        healthCarePersonnel.setConnectedPatients(connectedPatients);

        Week week1 = new Week(45, 2024, days, patient1);
        Week week2 = new Week(45, 2024, days, patient2);

        ToMongoDB toMongoDB = new ToMongoDB(DatabaseConnections.getMongodbClientInfo());
        toMongoDB.saveObject(patient1);
        toMongoDB.saveObject(patient2);

        toMongoDB.saveObject(healthCarePersonnel);

        toMongoDB.saveObject(week1);
        toMongoDB.saveObject(week2);
    }    
}
