package no.bettermemory.models.storageHandlers.databaseExtraction;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetWeek;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.tools.DatabaseConnections;

public class GetWeekFromMongoDB implements GetWeek {
    MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetWeekFromMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    @SuppressWarnings("unchecked")
    public Week getSpecificWeek(String patientId, int year, int weekNumber) throws Exception {
        Document query = new Document("patient", patientId).append("year", year).append("week_number", weekNumber);
        collection = DatabaseConnections.getWeeksCollection(database);
        Document result = collection.find(query).first();

        if (result == null) throw new Exception("week " + weekNumber + ", " + year + " is not registered in system.");

        Week week = new Week();

        week.setPatient(
            (new GetUserFromMongoDb()).getPatient(
                result.getString("patient")
            )
        );
        week.setYear(
            result.getInteger("year")
        );
        week.setWeekNumber(
            result.getInteger("week_number")
        );
        if (result.containsKey("patient")) {
            week.setDays(
                (ArrayList<Day>) (new GetDayFromMongoDB(client)).getListFromObjectId(
                    (List<ObjectId>) result.get("days")
                )
            );
        }

        return week;
    }
}
