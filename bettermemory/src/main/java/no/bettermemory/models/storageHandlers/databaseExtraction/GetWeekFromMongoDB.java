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

/**
 *  This class should be used retrieving week objects from data stored in a MongoDB.
 *  This is an example of how you use this class:
 *  <pre>{@code  GetWeekFromMongoDB getWeekFromMongoDB = new getWeekFromMongoDB(client);}
 */
public class GetWeekFromMongoDB implements GetWeek {
    MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetWeekFromMongoDB(MongoClient client) {
        this.client = client;
        this.database = DatabaseConnections.getUsersDatabase(client);
    }

    /**
     *  This method should be used to retrieve a spesific week based on the parameters from data stored in a MongoDB. 
     *  It returns it return that spesific week
     *  @param pasientId - A unique ID for the person this spesific week belongs to
     *  @param year - The spesific year this spesific week occurs
     *  @param weekNumber - The weekNumber this spesific week is
     *  @return week
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  GetWeekFromMongoDB.getSpecificWeek(patientId, year, weekNumber);}
     */

    @SuppressWarnings("unchecked")
    public Week getSpecificWeek(String patientId, int year, int weekNumber) throws Exception {
        Document query = new Document("patient", patientId).append("year", year).append("week_number", weekNumber);
        collection = DatabaseConnections.getWeeksCollection(database);
        Document result = collection.find(query).first();

        if (result == null) throw new Exception("week " + weekNumber + ", " + year + " is not registered in system.");

        Week week = new Week();

        week.setPatient(
            (new GetUserFromMongoDb(client)).getPatient(
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
                (ArrayList<Day>) (new GetDayFromMongoDB(client)).getDaysFromObjectId(
                    (List<ObjectId>) result.get("days")
                )
            );
        }

        return week;
    }
}
