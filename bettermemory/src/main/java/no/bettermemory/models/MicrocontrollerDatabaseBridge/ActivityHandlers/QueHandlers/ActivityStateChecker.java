package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.TimeComparisons;

public class ActivityStateChecker {
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private InsertActivityOrDay<Activity> insertActivity;

    public ActivityStateChecker(StaticContainerHandler<ActivityDTO> arrayHandler, InsertActivityOrDay<Activity> insertActivity) {
        this.arrayHandler = arrayHandler;
        this.insertActivity = insertActivity;
    }

    public void checkQueState() {

        for (int index = 0; index < arrayHandler.length(); index++) {
            ActivityDTO activityDTO = arrayHandler.get(index);

            if (activityDTO.getActivity().getConcluded()) {
                try {
                    insertActivity.updateObject(
                        activityDTO.getActivityId(),
                        activityDTO.getActivity()
                    );
                    arrayHandler.addAtIndex(index, null);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            else if (
                TimeComparisons.currentTimeHasPassedThreshold(
                    activityDTO.getActivity().getHour(),
                    activityDTO.getActivity().getMinutes(),
                    30
                )
            ) {
                arrayHandler.addAtIndex(index, null);
            }
        }
    }
}
