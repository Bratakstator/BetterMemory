package no.bettermemory.models.time;

import java.util.ArrayList;

import no.bettermemory.models.activity.Activity;



public class Day {

    private String dayName;
    private ArrayList<Activity> activities;

    public Day(){

    }

    public Day(String dayName, ArrayList<Activity> activities){
        this.dayName = dayName;
        this.activities = activities;
    }

    public String getDayName(){
        return dayName;
    }

    public void setDayName(String dayName){
        this.dayName = dayName;
    }
    
    public ArrayList<Activity> activities(){
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
    }
}
