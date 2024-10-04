package no.bettermemory.interfaces.storageHandlers.storageGetters;

import no.bettermemory.models.time.Week;

public interface GetWeek {
    Week getSpecificWeek(String patientId, int year, int weekNumber) throws Exception;
}
