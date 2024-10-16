package no.bettermemory.models.DTO;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public class ActivityDTO {
    ObjectId activityId;
    Activity activity;

    public ActivityDTO(ObjectId activityId, Activity activity) {
        this.activityId = activityId;
        this.activity = activity;
    }

    public void setActivityId(ObjectId activityId) { this.activityId = activityId; }

    public void setActivity(Activity activity) { this.activity = activity; }

    public ObjectId getActivityId() { return activityId; }

    public Activity getActivity() { return activity; }
}
