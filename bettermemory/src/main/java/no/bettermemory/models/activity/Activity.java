package no.bettermemory.models.activity;

import static no.bettermemory.tools.TimeControls.minuteCheck;

import org.bson.Document;

import static no.bettermemory.tools.TimeControls.hourCheck;

/**
 * This Class is meant to represent an Activity object. Its purpose is tho give information about the contents of the 
 * planed activiy sutch as the time it is planed to take place during the day. 
 * 
 * @param hour
 * @param minutes
 * @param shortDescription - Is a string that works like a title for the activity.
 * @param longDescription - Is a string which gives a more in-depth description of the contents of the activity.
 * @param important - Is a boolean which indicates whether the activity is "important" or not.
 * @param concluded - Is a boolean which indicates whether the activity has been performed or not.
 * 
 * @author Hermann Mjelde Hamnnes
 * 
 * @code
 * This is how you can create an object of this class:
 * <pre>{@code Activity activity = new Activity(10, 20, "Visit", "Your grand children are going to visit you.", true);} </pre>
 * 
 */
public class Activity {
    private int hour;
    private int minutes;
    private String shortDescription;
    private String longDescription;
    private boolean important;
    private boolean concluded = false;

    public Activity(){

    }

    public Activity(int hour, int minutes, String shortDescription, String longDescription, boolean important) throws IllegalArgumentException {
     
        minuteCheck(minutes);
        hourCheck(hour);
        this.hour = hour;
        this.minutes = minutes;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.important = important;
        
    }


    public Document toDocument() {
        Document document = new Document("hour", hour).append("minutes", minutes)
        .append("short_desc", shortDescription).append("long_desc", longDescription)
        .append("important", important).append("concluded", concluded);
        return document;
    }


    public int getHour(){
        return this.hour;
    }       

    public void setHour(int hour){
        this.hour = hour;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public void setMinutes(int minutes){
        this.minutes = minutes;
    }

    public String getShortDescription(){
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }

    public String getLongDescription(){
        return this.longDescription;
    }

    public void setLongDescription(String longDescription){
        this.longDescription = longDescription;
    }

    public boolean getImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean getConcluded() {
        return concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }

    public String getTime(){
        return this.hour + ":" + this.minutes;
    }
    
}
