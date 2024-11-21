package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.TimeDatabaseRetrievers;

import java.util.ArrayList;
import java.util.Arrays;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.TimeBasedDatabaseRetrievers.TimeIntervalBasedObjectRetriever;
import no.bettermemory.interfaces.Models.DayProvider;
import no.bettermemory.interfaces.Models.HourProvider;
import no.bettermemory.interfaces.Models.MinutesProvider;
import no.bettermemory.interfaces.Models.WeekProvider;
import no.bettermemory.interfaces.Models.YearProvider;
import no.bettermemory.interfaces.storageHandlers.storageGetters.GetActivity;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.DTO.ActivityToReceiveDTO;

public class TimeIntervalBasedActivityRetriever implements TimeIntervalBasedObjectRetriever<ActivityDTO[]> {
    private MinutesProvider<Integer> minute;
    private HourProvider<Integer> hour;
    private DayProvider<String> dayName;
    private WeekProvider<Integer> weekNumber;
    private YearProvider<Integer> year;
    private String patientId;
    private GetActivity getActivity;

    /**
     *  
     * @param minute - Must be an object of a class which implements MinutesProvider
     * @param hour - Must be an object of a class which implements HourProvider
     * @param dayName - Must be an object of a class which implements DayProvider
     * @param weekNumber - Must be an object of a class which implements WeekProvider
     * @param year - Must be an object of a class which implements YearProvider
     * @param patientId
     * @param getActivity - Must be an object of a class which implements GetActivity
     *                            
     */
    public TimeIntervalBasedActivityRetriever(
        MinutesProvider<Integer> minute,
        HourProvider<Integer> hour,
        DayProvider<String> dayName,
        WeekProvider<Integer> weekNumber,
        YearProvider<Integer> year,
        String patientId,
        GetActivity getActivity
    ) {
        
        this.minute = minute;
        this.hour = hour;
        this.dayName = dayName;
        this.weekNumber = weekNumber;
        this.year = year;
        this.patientId = patientId;
        this.getActivity = getActivity;
    }

    /**
     * This method can be used to retrieve a list of Activities associated with some 
     * time parameters provided by classes which implements some specific interfaces.
     * @throws RuntimeException if a handling error occurs while retrieving activities.
     */
    public ActivityDTO[] getObjects(int interval) {
        ArrayList<ActivityDTO> activityDTOs = new ArrayList<>();
        for (int intervalPos = 0; intervalPos < interval+1; intervalPos++) {
            try {
                ActivityToReceiveDTO activityToReceive = new ActivityToReceiveDTO(
                    patientId,
                    year.getYear(intervalPos),
                    weekNumber.getWeek(intervalPos),
                    dayName.getDay(intervalPos),
                    hour.getHour(intervalPos),
                    minute.getMinutes(intervalPos)
                );
                activityDTOs.addAll(Arrays.asList(getActivity.getActivitiesAtMinute(activityToReceive)));
            } catch (Exception e) {
                //System.err.println(e);
            }
        }

        return activityDTOs.toArray(ActivityDTO[]::new);
    }
}
