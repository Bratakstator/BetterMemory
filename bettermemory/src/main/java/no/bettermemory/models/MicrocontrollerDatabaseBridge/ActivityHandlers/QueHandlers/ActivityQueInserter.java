package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import java.util.Map;

import org.bson.types.ObjectId;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeBasedDatabaseRetrievers.TimeIntervalBasedObjectRetriever;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ActivityQueInserter implements ObjectQueInserter {
    private TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap;
    private StaticContainerHandler<ActivityDTO> arrayHandler;

    public ActivityQueInserter(
        TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap,
        StaticContainerHandler<ActivityDTO> arrayHandler
    ) {
        this.activitiesMap = activitiesMap;
        this.arrayHandler = arrayHandler;
    }

    @Override
    public void checkNullsAndAddToList() throws Exception {
        Map<ObjectId, Activity> activityMap = activitiesMap.getObjects(30);
        ActivityDTO[] activityDTOs = activityMap.keySet().stream().map(
            key -> new ActivityDTO(key, activityMap.get(key))
        ).toArray(ActivityDTO[]::new);

        if (activityDTOs == null) throw new Exception("No activities to add.");

        int index = 0;
        while (arrayHandler.hasNulls()) {
            try {
                arrayHandler.nullShiftRight().add(activityDTOs[index]);
            } catch (Exception e) {
                System.err.println(e); // "No more space in array."
                break;
            }

            index++;
            if (index == activityDTOs.length) break;
        }
        activityDTOs = null;
    }
}
