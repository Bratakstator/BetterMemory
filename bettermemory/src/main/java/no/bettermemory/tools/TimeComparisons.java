package no.bettermemory.tools;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

public class TimeComparisons {
    public static int getCurrentClockTimeInMinutes() {
        Calendar time = Calendar.getInstance();
        return (time.get(Calendar.HOUR_OF_DAY) * 60) + time.get(Calendar.MINUTE);
    }

    public static boolean givenTimeHasPassedThreshold(int year, int weekNumber, String dayName, int hour, int minutes, int threshold) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDateTime providedTime = LocalDateTime.now()
            .withYear(year)
            .with(weekFields.weekOfWeekBasedYear(), weekNumber)
            .with(DayOfWeek.valueOf(dayName.toUpperCase()))
            .withHour(hour)
            .withMinute(minutes)
            .withSecond(0)
            .withNano(0);

        LocalDateTime currentTime = LocalDateTime.now();

        return currentTime.isAfter(providedTime.plusMinutes(threshold));
    }
}
