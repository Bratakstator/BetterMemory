package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import java.util.Calendar;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ActivityStateChecker {
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private InsertActivityOrDay<Activity> insertActivity;

    public ActivityStateChecker(StaticContainerHandler<ActivityDTO> arrayHandler, InsertActivityOrDay<Activity> insertActivity) {
        this.arrayHandler = arrayHandler;
        this.insertActivity = insertActivity;
    }

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
}
