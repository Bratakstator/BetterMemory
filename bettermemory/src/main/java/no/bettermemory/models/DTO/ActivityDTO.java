package no.bettermemory.models.DTO;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public class ActivityDTO {
    ObjectId activityId;
    Activity activity;
    String dayName;

    public ActivityDTO(ObjectId activityId, Activity activity, String dayName) {
        this.activityId = activityId;
        this.activity = activity;
        this.dayName = dayName;
    }

    public void setActivityId(ObjectId activityId) { this.activityId = activityId; }

    public void setActivity(Activity activity) { this.activity = activity; }

    public void setDayName(String dayName) { this.dayName = dayName; }

    public ObjectId getActivityId() { return activityId; }

    public Activity getActivity() { return activity; }

    public String getDayName() { return dayName; }
}
