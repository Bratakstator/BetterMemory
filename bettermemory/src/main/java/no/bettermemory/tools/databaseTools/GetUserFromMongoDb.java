package no.bettermemory.tools.databaseTools;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetUser;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import no.bettermemory.models.users.Patient;
import org.bson.types.ObjectId;

import no.bettermemory.models.users.*;

public class GetUserFromMongoDb implements GetUser {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetUserFromMongoDb() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("user_info");
        collection = database.getCollection("patient");
    }

    @Override
    public getPatient(String patientId) throws Exception{
        Document query = new Document("_id", new ObjectId(patientId));
        Document result = collection.find(query).first();

        if (result != null) {
            Patient patient = new Patient();
        }



    }

    
}
