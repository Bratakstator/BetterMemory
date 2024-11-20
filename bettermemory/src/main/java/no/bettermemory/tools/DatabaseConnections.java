package no.bettermemory.tools;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Utility class for managing database connections and retrieving specific collections from a MongoDB database.
 * This class provides methods to connect to MongoDB, retrieve the database, and access various collections
 * used within the application.
 */
public class DatabaseConnections {
    private static final String MONGODB_CLIENT = "mongodb://localhost:27017";
    private static final String MONGODB_USERS_DATABASE = "user_info";
    private static final String MONGODB_PATIENT_COLLECTION = "patient";
    private static final String MONGODB_HEALTHCARE_PERSONNEL_COLLECTION = "healthcare_personnel";
    private static final String MONGODB_WEEKS_COLLECTION = "weeks";
    private static final String MONGODB_DAYS_COLLECTION = "days";
    private static final String MONGODB_ACTIVITIES_COLLECTION = "activities";
    
    /**
     * Establishes and returns a connection to the MongoDB client.
     * 
     * @return A {@link MongoClient} instance connected to the database.
     */
    public static MongoClient getMongodbClientInfo() {
        return MongoClients.create(MONGODB_CLIENT);
    }

     /**
     * Retrieves the MongoDB database for user information.
     * 
     * @param client The {@link MongoClient} instance connected to MongoDB.
     * @return A {@link MongoDatabase} instance for the "user_info" database.
     */
    public static MongoDatabase getUsersDatabase(MongoClient client) {
        return client.getDatabase(MONGODB_USERS_DATABASE);
    }

    /**
     * Retrieves the "patient" collection from the specified database.
     * 
     * @param database The {@link MongoDatabase} instance.
     * @return A {@link MongoCollection<Document>} instance for the "patient" collection.
     */
    public static MongoCollection<Document> getPatientCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_PATIENT_COLLECTION);
    }

    /**
     * Retrieves the "healthcare_personnel" collection from the specified database.
     * 
     * @param database The {@link MongoDatabase} instance.
     * @return A {@link MongoCollection<Document>} instance for the "healthcare_personnel" collection.
     */
    public static MongoCollection<Document> getHealthCarePersonnelCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_HEALTHCARE_PERSONNEL_COLLECTION);
    }

    /**
     * Retrieves the "weeks" collection from the specified database.
     * 
     * @param database The {@link MongoDatabase} instance.
     * @return A {@link MongoCollection<Document>} instance for the "weeks" collection.
     */
    public static MongoCollection<Document> getWeeksCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_WEEKS_COLLECTION);
    }

    /**
     * Retrieves the "days" collection from the specified database.
     * 
     * @param database The {@link MongoDatabase} instance.
     * @return A {@link MongoCollection<Document>} instance for the "days" collection.
     */
    public static MongoCollection<Document> getDaysCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_DAYS_COLLECTION);
    }

     /**
     * Retrieves the "activities" collection from the specified database.
     * 
     * @param database The {@link MongoDatabase} instance.
     * @return A {@link MongoCollection<Document>} instance for the "activities" collection.
     */
    public static MongoCollection<Document> getActivitiesCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_ACTIVITIES_COLLECTION);
    }
}
