package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeBasedDatabaseRetrievers.TimeIntervalBasedObjectRetriever;
import no.bettermemory.models.DTO.ActivityDTO;

/**
 * This class is responsible for inserting activities into a queue from a database or other data source. 
 * It retrieves activities based on a specified time interval and fills available (null) slots in the 
 * queue while ensuring the activity is not already concluded.
 *
 * @param activitiesRetriever - A retriever that fetches activities within a given time interval.
 * @param arrayHandler - A handler for managing a static container of activity DTOs.
 * @param interval - The time interval in minutes used for retrieving activities. Default is 30 minutes.
 * 
 * @author [Your Name]
 *
 * @code
 * Example usage:
 * <pre>{@code
 * TimeIntervalBasedObjectRetriever<ActivityDTO[]> retriever = new YourRetrieverImplementation<>();
 * StaticContainerHandler<ActivityDTO> arrayHandler = new YourArrayHandlerImplementation<>();
 * ActivityQueInserter queInserter = new ActivityQueInserter(retriever, arrayHandler, 30);
 * queInserter.checkNullsAndAddToList();
 * }</pre>
 */
public class ActivityQueInserter implements ObjectQueInserter {
    private TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever;
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private int interval = 30; // Standard value

    public ActivityQueInserter() {}

    /**
     * Constructor that initializes the inserter with a retriever and a handler.
     *
     * @param activitiesRetriever - A retriever to fetch activities based on a time interval.
     * @param arrayHandler - A handler for managing the queue of activities.
     */
    public ActivityQueInserter(
        TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever,
        StaticContainerHandler<ActivityDTO> arrayHandler
    ) {
        this.activitiesRetriever = activitiesRetriever;
        this.arrayHandler = arrayHandler;
    }

    /**
     * Constructor that initializes the inserter with a retriever, a handler, and a custom time interval.
     *
     * @param activitiesRetriever - A retriever to fetch activities based on a time interval.
     * @param arrayHandler - A handler for managing the queue of activities.
     * @param interval - The time interval in minutes for fetching activities.
     */
    public ActivityQueInserter(
        TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever,
        StaticContainerHandler<ActivityDTO> arrayHandler,
        int interval
    ) {
        this.activitiesRetriever = activitiesRetriever;
        this.arrayHandler = arrayHandler;
        this.interval = interval;
    }

    /**
     * Checks the queue for null slots and adds activities retrieved from the retriever.
     * Only activities that are not already concluded are added. If no activities are retrieved,
     * an exception is thrown. Synchronization ensures thread safety while modifying the queue.
     * 
     * @throws Exception if no activities are retrieved or the queue cannot be modified.
     */
    @Override
    public void checkNullsAndAddToList() throws Exception {
        ActivityDTO[] activityDTOs = activitiesRetriever.getObjects(interval);
        
        if (activityDTOs == null) throw new Exception("No activities to add.");

        int index = 0;
        synchronized (arrayHandler) {
            while (arrayHandler.hasNulls()) {
                System.out.println("AQI: "+activityDTOs[index]);
                    try {
                        if (!activityDTOs[index].getActivity().getConcluded()) {
                            System.out.println("AQI: "+"Activity not already concluded");
                            arrayHandler.nullShiftRight().add(activityDTOs[index]);
                            System.out.println("AQI: "+"Added activity: " + activityDTOs[index]);
                        } else {
                            System.out.println("AQI: "+"Activity already concluded");
                        }
                    } catch (Exception e) {
                        System.err.println(e); // "No more space in array."
                        break;
                    }

                    index++;
                    if (index == activityDTOs.length) break;
            }
        }
        activityDTOs = null;
    }

    public void setInterval(int interval) { this.interval = interval; }

    public int getInterval() { return interval; }
}
