package no.bettermemory.models.MicrocontrollerDatabaseBridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Collections;
import org.mockito.Mockito;
import java.util.List;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetActivity;
import no.bettermemory.interfaces.Models.MinutesProvider;
import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.interfaces.Models.WeekProvider;
import no.bettermemory.interfaces.Models.DayProvider;
import no.bettermemory.interfaces.Models.YearProvider;
import no.bettermemory.models.activity.Activity;


@ExtendWith(MockitoExtension.class)
public class TimeBasedActivityRetrieverTest {


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
    private TimeBasedActivityRetriever timeBasedActivityRetriever;

    @BeforeEach
    void setUp() {
        timeBasedActivityRetriever = new TimeBasedActivityRetriever(mockMinuteProvider, 
                                                                    mockHourProvider, 
                                                                    mockDayProvider, 
                                                                    mockWeekProvider, 
                                                                    mockYearProvider, 
                                                                    "2000222", 
                                                                    mockGetActivity);
    }

    @Test
    @DisplayName("Get activities for this instant")
    public void testGetObjectReturnsActivityList() throws Exception {
        when(mockMinuteProvider.getMinutes()).thenReturn(5);
        when(mockHourProvider.getHour()).thenReturn(15);
        when(mockDayProvider.getDay()).thenReturn("Monday");
        when(mockWeekProvider.getWeek()).thenReturn(40);
        when(mockYearProvider.getYear()).thenReturn(2024);
        Mockito.doReturn(Collections.emptyList())
                                    .when(mockGetActivity)
                                    .getActivitiesAtMinute(anyString(),
                                                           anyInt(), 
                                                           anyInt(), 
                                                           anyString(), 
                                                           anyInt(), 
                                                           anyInt());

        List<Activity> activities = timeBasedActivityRetriever.getObject();
        
        assertNotNull(activities);
        assertTrue(activities.isEmpty());


    }
    




    
}
