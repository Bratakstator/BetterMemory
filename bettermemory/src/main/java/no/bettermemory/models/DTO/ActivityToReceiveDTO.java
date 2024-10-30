package no.bettermemory.models.DTO;

public class ActivityToReceiveDTO {
    String patientId;
    int year;
    int weekNumber;
    String dayName;
    int hour;
    int minutes;

    public ActivityToReceiveDTO(String patientId, int year, int weekNumber, String dayName, int hour, int minutes) {
        this.patientId = patientId.toLowerCase();
        this.year = year;
        this.weekNumber = weekNumber;
        this.dayName = dayName.toLowerCase();
        this.hour = hour;
        this.minutes = minutes;
    }

    public void setPatientId(String patientId) { this.patientId = patientId.toLowerCase(); }
    public void setYear(int year) { this.year = year; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }
    public void setDayName(String dayName) {this.dayName = dayName.toLowerCase(); }
    public void setHour(int hour) { this.hour = hour; }
    public void setMinutes(int minutes) { this.minutes = minutes; }

    public String getPatientId() { return patientId; }
    public int getYear() { return year; }
    public int getWeekNumber() { return weekNumber; }
    public String getDayName() { return dayName; }
    public int getHour() { return hour; }
    public int getMinutes() { return minutes; }
}
