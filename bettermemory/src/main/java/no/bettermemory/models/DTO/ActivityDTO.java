package no.bettermemory.models.DTO;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public class ActivityDTO {
    ObjectId activityId;
    Activity activity;
    int year;
    int weekNumber;
    String dayName;

    public ActivityDTO() {}

    public ActivityDTO(ObjectId activityId, Activity activity, int year, int weekNumber, String dayName) {
        this.activityId = activityId;
        this.activity = activity;
        this.year = year;
        this.weekNumber = weekNumber;
        this.dayName = dayName;
    }

    public void setActivityId(ObjectId activityId) { this.activityId = activityId; }
    public void setActivity(Activity activity) { this.activity = activity; }
    public void setYear(int year) { this.year = year; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }
    public void setDayName(String dayName) { this.dayName = dayName; }

    public ObjectId getActivityId() { return activityId; }
    public Activity getActivity() { return activity; }
    public int getYear() { return year; }
    public int getWeekNumber() { return weekNumber; }
    public String getDayName() { return dayName; }
}
