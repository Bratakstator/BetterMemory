package no.bettermemory.models.time;

import no.bettermemory.models.users.Patient;
import static no.bettermemory.tools.TimeControls.weekNumberCheck;
import static no.bettermemory.tools.TimeControls.canOnlyBeeOneOfTheSameDay;

import java.util.ArrayList;

import org.bson.Document;

/**
 * This class represent a week objekt. It will consist of the following prameters:
 * @param weekNumber - Identifies the object with a spesific week number during a calender year.
 * @param year - Identifies what year this week object belongs to.
 * @param days - Is a list of days that the week object contains.
 * @param patient - Is a object that describes which pasient this week "plan" belongs to.
 * 
 * 
 * @author Hermann Mjelde Hamnnes
 * @version 1
 * 
 * @code
 * This is how you can create an object of this class:
 * <pre>{@code Week week = new Week(1, 2024, days, pasient);} </pre>
 * 
 */
public class Week {
    private int weekNumber;
    private int year;
    private ArrayList<Day> days = new ArrayList<>();
    private Patient patient;

    /**
     * This is the default constructure for this class.
     */
    public Week(){

    }

    /**
     * This is the constructor for the week object. It will take in the following parameters:
     * @param weekNumber - Identifies the object with a spesific week number during a calender year.
     * @param year - Identifies what year this week object belongs to.
     * @param days - Is a list of days that the week object contains.
     * @param patient - Is a object that describes which pasient this week "plan" belongs to.
     * 
     * @throws IllegalArgumentException
     * 
     */
    public Week(int weekNumber, int year, ArrayList<Day> days, Patient patient) throws IllegalArgumentException {
        try {
            weekNumberCheck(weekNumber);
            canOnlyBeeOneOfTheSameDay(days);
            this.weekNumber = weekNumber;
            this.year = year;
            this.patient= patient;
            this.days = days;
        }

        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage() + "\n The object was not created.");
            throw exception;
        }

    }

    public Document toDocument() {
        Document document = new Document("week_number", weekNumber).append("year", year).append("patient", patient.getPatientId());
        return document;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<Day> getDays(){
        return days;
    }

    public void setDays(ArrayList<Day> days){
        this.days = days;
    }

    public Patient getPatient(){
        return patient;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }



    
}
