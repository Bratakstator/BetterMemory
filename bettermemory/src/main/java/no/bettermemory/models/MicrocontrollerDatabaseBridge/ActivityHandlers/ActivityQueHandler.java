package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

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

    public ActivityDTO[] containerElementToDTOConverter(Map<?, ?> objectMap) {
        return null;
    }

    public void checkNullsAndAddToList(Map<?, ?> objectMap) {}
}
