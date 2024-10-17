package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticArrayHandler;
import no.bettermemory.models.DTO.ActivityDTO;

public class DisplayArray implements StaticArrayHandler<ActivityDTO> {
    ActivityDTO[] activities;

    public DisplayArray(int arraySize) {
        this.activities = new ActivityDTO[arraySize];
    }

    public void arrayShift() {
        for (int pos = 0; pos < activities.length; pos++) {
            if (activities[pos] == null) {
                for (int ahead = pos + 1; ahead < activities.length; ahead++) {
                    if (activities[ahead] != null) {
                        activities[pos] = activities[ahead];
                        activities[ahead] = null;
                        break;
                    }
                }
            }
        }
    }

    public void addToArray(ActivityDTO activityDTO) throws Exception {
        try {
            int nullPos = getFirstNull();
            activities[nullPos] = activityDTO;
        } catch (Exception e) {
            throw new Exception("No space for new activity.");
        }
    }

    public boolean hasNull() {
        for (ActivityDTO activity : activities) if (activity == null) return true;
        return false;
    }

    public int getFirstNull() throws Exception {
        for (int pos = 0; pos < activities.length; pos++) if (activities[pos] == null) return pos;
        throw new Exception("Array does not contain null values.");
    }

    public ActivityDTO[] getActivityArray() {
        return activities;
    }
}
