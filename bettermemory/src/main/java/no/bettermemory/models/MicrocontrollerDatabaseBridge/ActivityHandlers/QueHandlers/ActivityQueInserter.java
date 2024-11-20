package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeBasedDatabaseRetrievers.TimeIntervalBasedObjectRetriever;
import no.bettermemory.models.DTO.ActivityDTO;

public class ActivityQueInserter implements ObjectQueInserter {
    private TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever;
    private StaticContainerHandler<ActivityDTO> arrayHandler;
    private int interval = 30; // Standard value

    public ActivityQueInserter() {}

    public ActivityQueInserter(
        TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever,
        StaticContainerHandler<ActivityDTO> arrayHandler
    ) {
        this.activitiesRetriever = activitiesRetriever;
        this.arrayHandler = arrayHandler;
    }

    public ActivityQueInserter(
        TimeIntervalBasedObjectRetriever<ActivityDTO[]> activitiesRetriever,
        StaticContainerHandler<ActivityDTO> arrayHandler,
        int interval
    ) {
        this.activitiesRetriever = activitiesRetriever;
        this.arrayHandler = arrayHandler;
        this.interval = interval;
    }

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
