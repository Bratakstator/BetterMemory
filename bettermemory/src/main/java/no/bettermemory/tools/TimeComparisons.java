package no.bettermemory.tools;

import java.util.Calendar;

public class TimeComparisons {
    public static int getCurrentClockTimeInMinutes() {
        Calendar time = Calendar.getInstance();
        return (time.get(Calendar.HOUR_OF_DAY) * 60) + time.get(Calendar.MINUTE);
    }

    public static boolean currentTimeHasPassedThreshold(int hour, int minutes, int threshold) {
        int currentMinutesToday = getCurrentClockTimeInMinutes();
        int givenTimeInMinutes = (hour * 60) + minutes;

        // What was the term? magic numbers? i wouldn't personally call this that, 1440 is the amount of minutes in one day.
        if (currentMinutesToday - givenTimeInMinutes < 0) currentMinutesToday += 1440;
        if (currentMinutesToday - givenTimeInMinutes > 30) return true;

        return false;
    }
}
