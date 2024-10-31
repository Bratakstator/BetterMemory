package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueStateChecker;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.TimeComparisons;

public class ActivityQueStateChecker implements ObjectQueStateChecker {
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private InsertActivityOrDay<Activity> insertActivity;
    private int threshold = 30; // Standard value

    public ActivityQueStateChecker(StaticContainerHandler<ActivityDTO> arrayHandler, InsertActivityOrDay<Activity> insertActivity) {
        this.arrayHandler = arrayHandler;
        this.insertActivity = insertActivity;
    }

    @Override
    public void checkQueState() {
        for (int index = 0; index < arrayHandler.length(); index++) {
            ActivityDTO activityDTO = arrayHandler.get(index);

            if (activityDTO == null) continue;

            System.out.println(
                activityDTO.getActivity().getShortDescription()
            );

            if (activityDTO.getActivity().getConcluded()) {
                System.out.println("AQSC "+activityDTO.getActivity());
                try {
                    insertActivity.updateObject(
                        activityDTO.getActivityId(),
                        activityDTO.getActivity()
                    );
                    System.out.println("Activity set concluded: " + activityDTO.getActivity());
                    arrayHandler.addAtIndex(index, null);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println(e);
                } catch (Exception e) {
                    System.err.println(e);
                } 
            }
            else if (
                TimeComparisons.givenTimeHasPassedThreshold(
                    activityDTO.getYear(),
                    activityDTO.getWeekNumber(),
                    activityDTO.getDayName(),
                    activityDTO.getActivity().getHour(),
                    activityDTO.getActivity().getMinutes(),
                    threshold
                )
            ) {
                arrayHandler.addAtIndex(index, null);
            }
        }
    }

    public void setThreshold(int threshold) { this.threshold = threshold; }

    public int getThreshold() { return threshold; }
}
