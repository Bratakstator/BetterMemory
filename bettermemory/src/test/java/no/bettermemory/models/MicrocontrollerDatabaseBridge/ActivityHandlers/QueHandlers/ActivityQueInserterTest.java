package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeBasedDatabaseRetrievers.TimeIntervalBasedObjectRetriever;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

@ExtendWith(MockitoExtension.class)
public class ActivityQueInserterTest {
    @Mock
    private TimeIntervalBasedObjectRetriever<Map<ObjectId, Activity>> mockActivitiesMap;
    @Mock
    private StaticContainerHandler<ActivityDTO> mockArrayHandler;
    @InjectMocks
    private ActivityQueInserter activityQueInserter;

    @Mock
    private Map<ObjectId, Activity> mockActivityMap;
    @Mock
    private Set<ObjectId> mockSet;
    @Mock
    private Stream<ObjectId> mockStream;
    @Mock
    private ActivityDTO[] mockArray;

    @BeforeEach
    void setup() {
        activityQueInserter = new ActivityQueInserter(mockActivitiesMap, mockArrayHandler);
    }
}
