package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticArrayHandler;
import no.bettermemory.models.DTO.ActivityDTO;

public class DisplayArray implements StaticArrayHandler {
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

    public void addToArray() {}

    public boolean hasNull() {
        return false;
    }

    public int getFirstNull() {
        return 0;
    }

    public ActivityDTO[] getActivityArray() {
        return activities;
    }
}
