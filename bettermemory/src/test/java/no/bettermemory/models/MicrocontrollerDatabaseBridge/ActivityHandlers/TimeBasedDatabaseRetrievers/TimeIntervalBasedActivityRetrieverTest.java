package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.TimeBasedDatabaseRetrievers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
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
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.TimeDatabaseRetrievers.TimeIntervalBasedActivityRetriever;
import no.bettermemory.models.activity.Activity;

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
        when(mockYearProvider.getYear()).thenReturn(2024);
        when(mockWeekProvider.getWeek()).thenReturn(45);
        when(mockDayProvider.getDay()).thenReturn("Tuesday");
        when(mockHourProvider.getHour()).thenReturn(10);
        when(mockMinuteProvider.getMinutes()).thenReturn(30);
        Mockito.doReturn(new HashMap<ObjectId, Activity>()).when(mockGetActivity).getActivitiesAtInterval(
            anyString(),
            anyInt(),
            anyInt(),
            anyString(),
            anyInt(),
            anyInt(),
            anyInt()
        );

        // Act
        Map<ObjectId, Activity> activityMap = timeIntervalBasedActivityRetriever.getObjects(30);

        // Assert
        assertNotNull(activityMap);
        assertTrue(activityMap.isEmpty());
    }

    @Test
    public void testRecieveNullWhenTryingToGetObjects() throws Exception {
        // Arrange
        when(mockYearProvider.getYear()).thenReturn(2024);
        when(mockWeekProvider.getWeek()).thenReturn(45);
        when(mockDayProvider.getDay()).thenReturn("Tuesday");
        when(mockHourProvider.getHour()).thenReturn(10);
        when(mockMinuteProvider.getMinutes()).thenReturn(30);
        Mockito.doReturn(null).when(mockGetActivity).getActivitiesAtInterval(
            anyString(),
            anyInt(),
            anyInt(),
            anyString(),
            anyInt(),
            anyInt(),
            anyInt()
        );

        // Act
        Map<ObjectId, Activity> activityMap = timeIntervalBasedActivityRetriever.getObjects(30);

        // Assert
        assertNull(activityMap);
    }
}
