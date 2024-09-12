package no.bettermemory.models.time;

import static no.bettermemory.tools.TimeControls.mustBeeARealDay;

import java.util.ArrayList;

import no.bettermemory.models.activity.Activity;


/**
 * This class is ment to represent a day-object. This object will contain a list of 
 * activities ment for the pasient. This object will have a key role in week-class objects.
 * 
 * @param dayName - Is one of the following: monday, tuesday, wednesday, thursday, friday, saturday or sunday.
 * @param activities - Is a list of object of the class Activity.
 * 
 * @author Hermann Mjelde Hamnnes
 * 
 * @code
 * This is how you can create an object of this class:
 * <pre>{@code Day day = new Day("Monday", activities);} </pre>
 */
public class Day {

    private String dayName;
    private ArrayList<Activity> activities;

    public Day(){

    }

    public Day(String dayName, ArrayList<Activity> activities){
        
        if (mustBeeARealDay(dayName)) this.dayName = dayName;
        else throw new IllegalArgumentException();
        
        this.activities = activities;
    }

    public String getDayName(){
        return dayName;
    }

    public void setDayName(String dayName){
        this.dayName = dayName;
    }
    
    public ArrayList<Activity> getActivities(){
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
    }
}
