package no.bettermemory.models.time;

import no.bettermemory.models.users.Pasient;
import static no.bettermemory.tools.TimeControls.weekNumberCheck;

import java.util.ArrayList;

/**
 * This class represent a week objekt. It will consist of the following prameters:
 * @param weeknumber - Identifies the object with a spesific week number during a calender year.
 * @param 
 */
public class Week {
    private int weeknumber;
    private int year;
    private ArrayList<Day> days;
    private Pasient pasient;

    public Week(int weeknumber, int year, ArrayList<Day> days, Pasient pasient) {
        weekNumberCheck(weeknumber);
        this.weeknumber = weeknumber;
        this.year = year;
        this.days = days;
        this.pasient= pasient;
    }

    public int getWeeknumber() {
        return weeknumber;
    }

    public void setWeeknumber(int weeknumber) {
        this.weeknumber = weeknumber;
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

    public Pasient getPasient(){
        return pasient;
    }

    public void setPasient(Pasient pasient){
        this.pasient = pasient;
    }



    
}
