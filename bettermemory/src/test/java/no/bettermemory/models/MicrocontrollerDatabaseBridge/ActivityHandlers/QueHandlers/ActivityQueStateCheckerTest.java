package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

@ExtendWith(MockitoExtension.class)
public class ActivityQueStateCheckerTest {
    @Mock
    private StaticContainerHandler<ActivityDTO> mockArrayHandler;
    @Mock
    private InsertActivityOrDay<Activity> mockInsertActivity;
    @InjectMocks
    private ActivityQueStateChecker stateChecker;

    @Mock
    private ActivityDTO mockActivityDTO;
    @Mock
    private ObjectId mockObjectId;
    @Mock
    private Activity mockActivity;

    @BeforeEach
    void setup() {
        stateChecker = new ActivityQueStateChecker(mockArrayHandler, mockInsertActivity);
    }

    @Test
    public void testCheckQueStateWhenConcluded() throws Exception {
        // Arrange
        when(mockArrayHandler.length()).thenReturn(1);
        when(mockArrayHandler.get(anyInt())).thenReturn(mockActivityDTO);
        when(mockActivityDTO.getActivity()).thenReturn(mockActivity);
        when(mockActivityDTO.getActivity().getConcluded()).thenReturn(true);
        when(mockArrayHandler.addAtIndex(0, null)).thenReturn(0);
        when(mockActivityDTO.getActivityId()).thenReturn(mockObjectId);

        // Act
        stateChecker.checkQueState();

        // Assert
        verify(mockInsertActivity).updateObject(mockObjectId, mockActivity);
        verify(mockArrayHandler).addAtIndex(0, null);
    }

    // This is just wrong, will fix
    @Test
    public void testCheckQueStateWhenTimeOut() {
        // Arrange
        int year = 2024;
        int weekNumber = 35;
        String dayName = "monday";
        int hour = 10;
        int minutes = 45;

        when(mockArrayHandler.length()).thenReturn(1);
        when(mockArrayHandler.get(anyInt())).thenReturn(mockActivityDTO);
        when(mockActivityDTO.getActivity()).thenReturn(mockActivity);
        when(mockActivityDTO.getActivity().getConcluded()).thenReturn(false);
        when(mockActivityDTO.getActivity().getHour()).thenReturn(hour);
        when(mockActivityDTO.getActivity().getMinutes()).thenReturn(minutes);
        when(mockActivityDTO.getYear()).thenReturn(year);
        when(mockActivityDTO.getWeekNumber()).thenReturn(weekNumber);
        when(mockActivityDTO.getDayName()).thenReturn(dayName);

        // Act
        stateChecker.checkQueState();

        // Assert
        verify(mockArrayHandler).addAtIndex(0, null);
    }

    @Test
    public void testCheckQueStateWhenNothing() {
        // Arrange
        int year = 2026;
        int weekNumber = 35;
        String dayName = "monday";
        int hour = 10;
        int minutes = 45;

        when(mockArrayHandler.length()).thenReturn(1);
        when(mockArrayHandler.get(anyInt())).thenReturn(mockActivityDTO);

        when(mockActivityDTO.getActivity()).thenReturn(mockActivity);
        when(mockActivity.getConcluded()).thenReturn(false);

        when(mockActivityDTO.getYear()).thenReturn(year);
        when(mockActivityDTO.getWeekNumber()).thenReturn(weekNumber);
        when(mockActivityDTO.getDayName()).thenReturn(dayName);
        when(mockActivity.getHour()).thenReturn(hour);
        when(mockActivity.getMinutes()).thenReturn(minutes);

        // Act
        stateChecker.checkQueState();

        // Assert
        verify(mockArrayHandler, never()).addAtIndex(0, null);
    }
}
