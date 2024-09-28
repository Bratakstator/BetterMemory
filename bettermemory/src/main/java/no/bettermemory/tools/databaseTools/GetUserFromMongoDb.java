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
import java.util.List;
import java.util.ArrayList;

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

        if (result != null) { //Checks if the specified document was found.
            Patient patient = new Patient(); //Creates a new Patient object, that will be built.

            //The Patient objects instance variables wil be set by the following code.
            patient.setPatientId(result.getObjectId("_id").toString()); 
            patient.setFirstName(result.getString("first_name"));
            patient.setSurname(result.getString("surname"));
            
            //Checks if the Patient Object in the database is listed with any closeRelatives.
            if(result.containsKey("close_relatives")) { 

                /*
                 * If the test over returns true, the following code will create a object from the
                 * the returned field.
                 */
                Object closeRelativesObject = result.get("close_relatives"); 
                if(closeRelativesObject instanceof List<?>) { //Checks if the closeRelativesObject indeed is a List.
                    /*
                     * The reason for why List<question mark> is used here is because the compiler can not 
                     * guarantee that the cast of List<specifiedObjectType> will work.
                     */
                    List<?> closeRelativeDocuments = (List<?>) closeRelativesObject;
                    for (Object object : closeRelativeDocuments) {
                        ArrayList<CloseRelative> closeRelatives = new ArrayList<>();

                        if (object instanceof Document) {
                            Document closeRelativeDocument = (Document) object;

                            String id = closeRelativeDocument.getString("_id");
                            String firstName = closeRelativeDocument.getString("first_name");
                            String surname = closeRelativeDocument.getString("surname");

                            closeRelatives.add(new CloseRelative(id, firstName, surname));
                        }

                        patient.set
                    }


                }


            }


        }



    }

    
}
