package no.bettermemory.tools;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
/**
 * Utility class for performing time-related operations and comparisons.
 * Provides methods for retrieving the current clock time in minutes and 
 * checking whether a given time has passed a specific threshold.
 */
public class TimeComparisons {

    /**
     * Retrieves the current clock time in minutes since midnight.
     * 
     * @return The current time in minutes (0 to 1439).
     */
    public static int getCurrentClockTimeInMinutes() {
        Calendar time = Calendar.getInstance();
        return (time.get(Calendar.HOUR_OF_DAY) * 60) + time.get(Calendar.MINUTE);
    }

    /**
     * Checks if a given time, adjusted by a threshold in minutes, has passed relative to the current time.
     * 
     * @param year The year of the provided time.
     * @param weekNumber The ISO week number of the provided time.
     * @param dayName The name of the day (e.g., "MONDAY", "TUESDAY") of the provided time.
     * @param hour The hour (0-23) of the provided time.
     * @param minutes The minutes (0-59) of the provided time.
     * @param threshold The threshold in minutes to check if the time has passed.
     * @return {@code true} if the current time is after the provided time plus the threshold, {@code false} otherwise.
     */
    public static boolean givenTimeHasPassedThreshold(int year, int weekNumber, String dayName, int hour, int minutes, int threshold) {
        
        // Create a WeekFields object to handle locale-specific week numbering
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Create a LocalDateTime instance for the provided time
        LocalDateTime providedTime = LocalDateTime.now()
            .withYear(year)
            .with(weekFields.weekOfWeekBasedYear(), weekNumber)
            .with(DayOfWeek.valueOf(dayName.toUpperCase()))
            .withHour(hour)
            .withMinute(minutes)
            .withSecond(0)
            .withNano(0);

        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Compare the current time to the provided time plus the threshold
        return currentTime.isAfter(providedTime.plusMinutes(threshold));
    }
}
