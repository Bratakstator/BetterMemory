package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import java.util.Calendar;
import java.util.Map;

import org.bson.types.ObjectId;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ObjectQueHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeIntervalBasedObjectRetriever;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ActivityQueHandler implements ObjectQueHandler<ActivityDTO> {
    private InsertActivityOrDay<Activity> insertActivity;
    private TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap;
    private StaticContainerHandler<ActivityDTO> arrayHandler;

    public ActivityQueHandler(
        InsertActivityOrDay<Activity> insertActivity,
        TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap,
        StaticContainerHandler<ActivityDTO> arrayHandler
    ) {}

    public void run() {}

    public void checkTimeOuts() {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentminutes = Calendar.getInstance().get(Calendar.MINUTE);
        int totalMinutes = (currentHour * 60) + currentminutes;

        for (int index = 0; index < arrayHandler.length(); index++) {
            Activity activity = arrayHandler.getAttributeOf(index, ActivityDTO::getActivity);
            int activityMinutes = (activity.getHour() * 60) + activity.getMinutes();

            if ((totalMinutes - activityMinutes) > 30) arrayHandler.addAtIndex(index, null);
        }
    }

    public ActivityDTO[] containerElementToDTOConverter(Map<?, ?> objectMap) {
        // I wanted to do this in one line, but it ended up being so long it became three lines anyways.
        ActivityDTO[] activityDTOs = (ActivityDTO[]) objectMap.keySet().stream().map(
            object -> new ActivityDTO((ObjectId) object, (Activity) objectMap.get(object))
        ).toArray();
        return activityDTOs;
    }

    public void checkNullsAndAddToList() throws Exception {
        ActivityDTO[] activityDTOs = containerElementToDTOConverter(
            activitiesMap.getObjects(30)
        );

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
