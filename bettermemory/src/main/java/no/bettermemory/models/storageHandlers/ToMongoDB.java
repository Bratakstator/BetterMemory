package no.bettermemory.models.storageHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import no.bettermemory.interfaces.storageHandlers.ToDatabase;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.time.Week;
import no.bettermemory.models.users.HealthCarePersonnel;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;




/**
 * This class is used to connect and write to a MongoDB database.
 * The ToMongoDB class takes the provided information and establishes a connection to the database if possible.
 * 
 * @param connectionString - This string should contain the information used to connect to a client.
 * @param databaseName - This string should contain the name of the database to be used.
 * @param collectionName - This string should contain the name of the collection to be used.
 * 
 * @author Joakim Klemsdal Bøe
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code ToMongoDB toMongoDB = new ToMongoDB("mongodb://localhost:27017", "Users", "Patients")}
 */
public class ToMongoDB implements ToDatabase {
    private MongoClient client;

    public ToMongoDB(MongoClient client) {
        this.client = client;
    }

    
    public void saveObject(Object object) {
        MongoDatabase database = DatabaseConnections.getUsersDatabase(client);
        if (object instanceof Patient) {
            MongoCollection<Document> collection = DatabaseConnections.getPatientCollection(database);
            Document document = ((Patient) object).toDocument();
            collection.insertOne(document);
        }
        else if (object instanceof HealthCarePersonnel) {
            MongoCollection<Document> collection = DatabaseConnections.getHealthCarePersonnelCollection(database);
            Document document = ((HealthCarePersonnel) object).toDocument();
            collection.insertOne(document);
        }
        else if (object instanceof Week) {
            MongoCollection<Document> activityCollection = DatabaseConnections.getActivitiesCollection(database);
            MongoCollection<Document> dayCollection = DatabaseConnections.getDaysCollection(database);
            MongoCollection<Document> weekCollection = DatabaseConnections.getWeeksCollection(database);

            Week week = (Week) object;
            ArrayList<Day> days = week.getDays();
            List<ObjectId> dayResultValues = new ArrayList<>();
            for (Day day : days) {
                List<Document> activities = day.getActivities().stream().map(activity -> activity.toDocument()).collect(Collectors.toList());
                List<ObjectId> insertResultValues;
                try {
                    InsertManyResult insertResult = activityCollection.insertMany(activities);
                    insertResultValues = insertResult.getInsertedIds().values()
                    .stream().map(document -> document.asObjectId().getValue()).collect(Collectors.toList());
                } catch (MongoBulkWriteException e) {
                    insertResultValues = e.getWriteResult().getInserts().stream()
                    .map(document -> document.getId().asObjectId().getValue()).collect(Collectors.toList());
                }
                Document dayDocument = day.toDocument();
                dayDocument.append("activities", insertResultValues);
                InsertOneResult insertOneResult = dayCollection.insertOne(dayDocument);
                dayResultValues.add(insertOneResult.getInsertedId().asObjectId().getValue());
            }
            Document weekDocument = week.toDocument();
            weekDocument.append("days", dayResultValues);
            weekCollection.insertOne(weekDocument);
        }
    }

    
    public void updateObject(Object object) {}

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoClient getClient() {
        return client;
    }
}
