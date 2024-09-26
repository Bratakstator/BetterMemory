package no.bettermemory.tools;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnections {
    private static final String MONGODB_CLIENT = "mongodb://localhost:27017";
    private static final String MONGODB_USERS_DATABASE = "user_info";
    private static final String MONGODB_PATIENT_COLLECTION = "patient";
    private static final String MONGODB_HEALTHCARE_PERSONNEL_COLLECTION = "healthcare_personnel";
    
    public static MongoClient getMongodbClientInfo() {
        return MongoClients.create(MONGODB_CLIENT);
    }

    public static MongoDatabase getUsersDatabase(MongoClient client) {
        return client.getDatabase(MONGODB_USERS_DATABASE);
    }

    public static MongoCollection getPatientCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_PATIENT_COLLECTION);
    }

    public static MongoCollection getHealthCarePersonnelCollection(MongoDatabase database) {
        return database.getCollection(MONGODB_HEALTHCARE_PERSONNEL_COLLECTION);
    }
}
