package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.TimeBasedDatabaseRetrievers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.mockito.Mockito;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetActivity;
import no.bettermemory.interfaces.Models.MinutesProvider;
import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.interfaces.Models.WeekProvider;
import no.bettermemory.interfaces.Models.DayProvider;
import no.bettermemory.interfaces.Models.YearProvider;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;
import no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.TimeDatabaseRetrievers.TimeBasedActivityRetriever;


@ExtendWith(MockitoExtension.class)
public class TimeBasedActivityRetrieverTest {


    /*
     * A wast number of classes gets mocked here in order to better 
     * predict their behavior.
     */
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
        //Arrange
        when(mockMinuteProvider.getMinutes(anyInt())).thenReturn(5); //
        when(mockHourProvider.getHour(anyInt())).thenReturn(15);
        when(mockDayProvider.getDay(anyInt())).thenReturn("Monday");
        when(mockWeekProvider.getWeek(anyInt())).thenReturn(40);
        when(mockYearProvider.getYear(anyInt())).thenReturn(2024);
        //doReturn() is used here to handle the checked exception for the class.
        Mockito.doReturn(new ActivityDTO[1]).when(mockGetActivity).getActivitiesAtMinute(mockActivityToReceiveDTO);

        //Act
        ActivityDTO[] activities = timeBasedActivityRetriever.getObject();
        
        //Assert
        assertNotNull(activities);
        //assertTrue(activities.isEmpty());
    }
    




    
}
