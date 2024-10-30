package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private TimeIntervalBasedObjectRetriever<ActivityDTO[]> mockActivitiesMap;
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
    private Stream<ActivityDTO> mockStream2;
    @Mock
    private ActivityDTO mockActivityDTO;

    @BeforeEach
    void setup() {
        activityQueInserter = new ActivityQueInserter(mockActivitiesMap, mockArrayHandler);
    }

    @Test
    public void testInsertNewActivityToArray() throws Exception {
        // Arrange
        when(mockActivitiesMap.getObjects(30)).thenReturn(new ActivityDTO[1]);
        
        when(mockArrayHandler.hasNulls()).thenReturn(true, false);
        when(mockArrayHandler.nullShiftRight()).thenReturn(mockArrayHandler);
        
        // Act
        activityQueInserter.checkNullsAndAddToList();

        // Assert
        verify(mockArrayHandler.nullShiftRight()).add(mockActivityDTO);
    }

    @Test
    public void testWhenNoActivitiesInArray() {
        // Arrange
        when(mockActivitiesMap.getObjects(30)).thenReturn(null);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> {
                activityQueInserter.checkNullsAndAddToList();
            }
        );

        // Assert
        assertEquals("No activities to add.", exception.getMessage());
    }

    @Test
    public void testWhenArrayIsFull() throws Exception {
        // Arrange
        when(mockActivitiesMap.getObjects(30)).thenReturn(new ActivityDTO[1]);

        when(mockArrayHandler.hasNulls()).thenReturn(false);

        // Act
        activityQueInserter.checkNullsAndAddToList();

        // Assert
        verify(mockArrayHandler, never()).nullShiftRight();
    }
}
