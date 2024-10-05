package no.bettermemory.models.storageHandlers.databaseExtraction;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetByPeriod;
import no.bettermemory.interfaces.storageHandlers.storageGetters.GetFromObjectId;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

public class GetDayFromMongoDB implements GetByPeriod<Day>, GetFromObjectId<Day> {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetDayFromMongoDB(MongoClient client) {
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Day getSpecific(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
        return null;
    }

    @Override
    public List<Day> getListByPeriod(String patientId, int year, int weekNumber, String dayName, Integer... time) throws Exception {
        return null;
    }

    @Override
    public Day getSpecificFromObjectId(ObjectId activityId) throws Exception {
        return null;
    }

    @Override
    public List<Day> getListFromObjectId(List<ObjectId> activityIds) throws Exception {
        return null;
    }
}
