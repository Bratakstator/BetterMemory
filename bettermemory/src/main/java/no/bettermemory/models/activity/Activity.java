package no.bettermemory.models.activity;

public class Activity {
    private int hour;
    private int minutes;
    private String shortDescription;
    private String longDescription;

    public Activity(){

    }

    public Activity(int hour, int minutes, String shortDescription, String longDescription){
        this.hour = hour;
        this.minutes = minutes;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
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

    public String getTime(){
        return this.hour + ":" + this.minutes;
    }
    
}
