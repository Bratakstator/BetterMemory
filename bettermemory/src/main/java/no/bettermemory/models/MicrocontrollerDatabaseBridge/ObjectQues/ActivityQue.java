package no.bettermemory.models.MicrocontrollerDatabaseBridge.ObjectQues;

import java.util.HashMap;

import org.bson.types.ObjectId;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQue;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ActivityQue implements ObjectQue<HashMap<ObjectId, Activity>, ActivityDTO> {
    HashMap<ObjectId, Activity> activities = new HashMap<>();

    public ActivityQue() {}

    public int addToQue(HashMap<ObjectId, Activity> activities) {
        this.activities.putAll(activities);
        int queLength = this.activities.size();

        return queLength;
    }

    public ActivityDTO getFirstFromQueAsDTO() {
        ObjectId activityId = activities.keySet().iterator().next();
        Activity activity = activities.get(activityId);

        return new ActivityDTO(activityId, activity);
    }

    public boolean containsElements() {
        if (activities.size() > 0) return true;
        return false;
    }
}
