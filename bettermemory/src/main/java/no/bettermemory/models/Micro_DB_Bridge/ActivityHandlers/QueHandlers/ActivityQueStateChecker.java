package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueStateChecker;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.TimeComparisons;


/**
 * This class is responsible for managing the state of an activity queue. 
 * It periodically checks whether activities in the queue meet certain conditions 
 * and processes them accordingly, such as marking activities as concluded or 
 * removing outdated activities.
 *
 * @param arrayHandler - Handles a static container holding activity data transfer objects (DTOs).
 * @param insertActivity - Provides functionality to update activity data in the database.
 * @param threshold - The time threshold (in minutes) for considering an activity outdated. Default is 30 minutes.
 * 
 * @author Joakim Klemsdal Bøe
 *
 * @code
 * To use this class:
 * <pre>{@code
 * StaticContainerHandler<ActivityDTO> arrayHandler = new YourArrayHandlerImplementation<>();
 * InsertActivityOrDay<Activity> databaseInserter = new YourDatabaseInserterImplementation<>();
 * ActivityQueStateChecker queStateChecker = new ActivityQueStateChecker(arrayHandler, databaseInserter);
 * queStateChecker.checkQueState();
 * }</pre>
 */

public class ActivityQueStateChecker implements ObjectQueStateChecker {
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private InsertActivityOrDay<Activity> insertActivity;
    private int threshold = 30; // Standard value


    /**
     * Constructor for initializing the ActivityQueStateChecker with required handlers.
     *
     * @param arrayHandler - A handler for managing the activity DTOs in the queue.
     * @param insertActivity - A handler for inserting or updating activity data in the database.
     */
    
    public ActivityQueStateChecker(StaticContainerHandler<ActivityDTO> arrayHandler, InsertActivityOrDay<Activity> insertActivity) {
        this.arrayHandler = arrayHandler;
        this.insertActivity = insertActivity;
    }


    /**
     * Checks the state of the activity queue and processes activities based on their conditions.
     * If an activity is concluded, it updates the database and removes it from the queue.
     * If an activity exceeds the time threshold, it is removed from the queue.
     * 
     * @throws IndexOutOfBoundsException if there is an issue accessing the queue by index.
     * @throws Exception for any unexpected issues during processing.
     */

    @Override
    public void checkQueState() {
        for (int index = 0; index < arrayHandler.length(); index++) {
            ActivityDTO activityDTO = arrayHandler.get(index);

            if (activityDTO == null) continue; // Skip if there is no activity at the current index.

            System.out.println("AQSC "+activityDTO.getActivity());

            if (activityDTO.getActivity().getConcluded()) {
                try {
                    insertActivity.updateObject(
                        activityDTO.getActivityId(),
                        activityDTO.getActivity()
                    );
                    System.out.println("Activity set concluded: " + activityDTO.getActivity());
                    arrayHandler.addAtIndex(index, null); // Remove the activity from the queue.
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
