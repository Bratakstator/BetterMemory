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

public class ActivityQueHandler implements ObjectQueHandler<ActivityDTO, Map<ObjectId, Activity>> {
    private InsertActivityOrDay<Activity> insertActivity;
    private TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap;
    private StaticContainerHandler<ActivityDTO> arrayHandler;

    public ActivityQueHandler(
        InsertActivityOrDay<Activity> insertActivity,
        TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> activitiesMap,
        StaticContainerHandler<ActivityDTO> arrayHandler
    ) {
        this.insertActivity = insertActivity;
        this.activitiesMap = activitiesMap;
        this.arrayHandler = arrayHandler;
    }

    public void run() {}

    public void checkQueState() {
        Calendar calendar = Calendar.getInstance();
        int currentMinutesToday = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);

        for (int index = 0; index < arrayHandler.length(); index++) {
            ActivityDTO activityDTO = arrayHandler.get(index);

            int activityMinutes = (activityDTO.getActivity().getHour() * 60) + activityDTO.getActivity().getMinutes();

            if (activityDTO.getActivity().getConcluded()) {
                try {
                    insertActivity.updateObject(
                        activityDTO.getActivityId(),
                        activityDTO.getActivity()
                    );
                    arrayHandler.addAtIndex(index, null);
                } catch (Exception e) {
                    System.out.println("I should reconsider how this handling works, maybe, might not be an issue either way.");
                }
            }
            else if ((currentMinutesToday - activityMinutes) > 30) arrayHandler.addAtIndex(index, null);
        }
    }

    public ActivityDTO[] containerElementToDTOConverter(Map<ObjectId, Activity> activityMap) {
        // I wanted to do this in one line, but it ended up being so long it became three lines anyways.
        ActivityDTO[] activityDTOs = (ActivityDTO[]) activityMap.keySet().stream().map(
            object -> new ActivityDTO((ObjectId) object, (Activity) activityMap.get(object))
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
