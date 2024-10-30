package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.TimeBasedDatabaseRetrievers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.interfaces.Models.DayProvider;
import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.interfaces.Models.MinutesProvider;
import no.bettermemory.interfaces.Models.WeekProvider;
import no.bettermemory.interfaces.Models.YearProvider;
import no.bettermemory.interfaces.storageHandlers.storageGetters.GetActivity;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.TimeDatabaseRetrievers.TimeIntervalBasedActivityRetriever;

@ExtendWith(MockitoExtension.class)
public class TimeIntervalBasedActivityRetrieverTest {
    @Mock
    private GetActivity mockGetActivity;

    @Mock
    private MinutesProvider<Integer> mockMinuteProvider;

    @Mock 
    private HourProvider<Integer> mockHourProvider;

    @Mock
    private WeekProvider<Integer> mockWeekProvider;

    @Mock
    private DayProvider<String> mockDayProvider;

    @Mock 
    private YearProvider<Integer> mockYearProvider;

    @Mock
    private ActivityToReceiveDTO mockActivityToReceiveDTO;

    @InjectMocks
    private TimeIntervalBasedActivityRetriever timeIntervalBasedActivityRetriever;

    @BeforeEach
    void setUp() {
        timeIntervalBasedActivityRetriever = new TimeIntervalBasedActivityRetriever(
            mockMinuteProvider,
            mockHourProvider,
            mockDayProvider,
            mockWeekProvider,
            mockYearProvider,
            "2000222",
            mockGetActivity
        );
    }

    @Test
    public void testRecieveMapOfActivitiesTest() throws Exception {
        // Arrange
        when(mockYearProvider.getYear(anyInt())).thenReturn(2024);
        when(mockWeekProvider.getWeek(anyInt())).thenReturn(45);
        when(mockDayProvider.getDay(anyInt())).thenReturn("Tuesday");
        when(mockHourProvider.getHour(anyInt())).thenReturn(10);
        when(mockMinuteProvider.getMinutes(anyInt())).thenReturn(30);
        Mockito.doReturn(new ActivityDTO[1]).when(mockGetActivity).getActivitiesAtMinute(mockActivityToReceiveDTO);

        // Act
        ActivityDTO[] activities = timeIntervalBasedActivityRetriever.getObjects(30);

        // Assert
        assertNotNull(activities);
        //assertTrue(activityMap.isEmpty());
    }

    @Test
    public void testRecieveNullWhenTryingToGetObjects() throws Exception {
        // Arrange
        when(mockYearProvider.getYear(anyInt())).thenReturn(2024);
        when(mockWeekProvider.getWeek(anyInt())).thenReturn(45);
        when(mockDayProvider.getDay(anyInt())).thenReturn("Tuesday");
        when(mockHourProvider.getHour(anyInt())).thenReturn(10);
        when(mockMinuteProvider.getMinutes(anyInt())).thenReturn(30);
        Mockito.doReturn(null).when(mockGetActivity).getActivitiesAtMinute(mockActivityToReceiveDTO);

        // Act
        ActivityDTO[] activities = timeIntervalBasedActivityRetriever.getObjects(30);

        // Assert
        assertNull(activities);
    }
}
