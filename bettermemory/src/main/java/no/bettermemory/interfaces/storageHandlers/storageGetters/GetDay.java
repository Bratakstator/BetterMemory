package no.bettermemory.interfaces.storageHandlers.storageGetters;

import java.util.List;

import org.bson.types.ObjectId;

import no.bettermemory.models.time.Day;

public interface GetDay {
    Day getSpecific(String patientId, int year, int weekNumber, String dayName) throws Exception;
    List<Day> getListFromObjectId(List<ObjectId> dayIds) throws Exception;
}
