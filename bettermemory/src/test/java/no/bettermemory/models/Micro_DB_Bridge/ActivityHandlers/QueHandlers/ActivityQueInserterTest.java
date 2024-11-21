package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private TimeIntervalBasedObjectRetriever<ActivityDTO[]> mockActivitiesRetriever;
    @Mock
    private StaticContainerHandler<ActivityDTO> mockArrayHandler;

    @InjectMocks
    private ActivityQueInserter activityQueInserter;

    @Mock
    private ActivityDTO mockActivityDTO;
    @Mock
    private Activity mockActivity;

    @BeforeEach
    void setup() {
        activityQueInserter = new ActivityQueInserter(mockActivitiesRetriever, mockArrayHandler);
    }

    @Test
    public void testInsertNewActivityToArray() throws Exception {
        // Arrange
        ActivityDTO[] activityDTOs = new ActivityDTO[1];
        activityDTOs[0] = mockActivityDTO;
        when(mockActivitiesRetriever.getObjects(30)).thenReturn(activityDTOs);
        
        when(mockArrayHandler.hasNulls()).thenReturn(true, false);
        when(mockActivityDTO.getActivity()).thenReturn(mockActivity);
        when(mockActivity.getConcluded()).thenReturn(false);

        when(mockArrayHandler.nullShiftRight()).thenReturn(mockArrayHandler);
        
        // Act
        activityQueInserter.checkNullsAndAddToList();

        // Assert
        verify(mockArrayHandler.nullShiftRight()).add(mockActivityDTO);
    }

    @Test
    public void testWhenNoActivitiesInArray() {
        // Arrange
        when(mockActivitiesRetriever.getObjects(30)).thenReturn(null);

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
    public void testWhenArrayHandlerIsFull() throws Exception {
        // Arrange
        when(mockActivitiesRetriever.getObjects(30)).thenReturn(new ActivityDTO[1]);

        when(mockArrayHandler.hasNulls()).thenReturn(false);

        // Act
        activityQueInserter.checkNullsAndAddToList();

        // Assert
        verify(mockArrayHandler, never()).nullShiftRight();
    }
}
