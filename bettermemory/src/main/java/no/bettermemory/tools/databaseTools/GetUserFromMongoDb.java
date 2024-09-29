package no.bettermemory.tools.databaseTools;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetUser;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import no.bettermemory.models.users.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public class GetUserFromMongoDb implements GetUser {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetUserFromMongoDb() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("user_info");
        collection = database.getCollection("patient");
    }

    /**
     * This method should be used for creating Patient object from data stored in a 
     * mongoDB. 
     * @author Hermann Mjelde Hamnnes
     * @param patientId - All patients in the database is organized based on their patientId.
     * @code
     * This is an example of how you can use this method:
     * <pre>{@code  GetUserFromMongoDb getUserFromMongoDb = new GetUserFromMongoDb();

        try{
            Patient patient = getUserFromMongoDb.getPatient("1234");
            System.out.println(patient);
        }

        catch (Exception exception) {
            System.err.println("It did not work");
            exception.printStackTrace();
        } </pre>
     */
    @Override
    public Patient getPatient(String patientId) throws Exception{
        Document query = new Document("_id", patientId);
        Document result = collection.find(query).first();

        if (result != null) { //Checks if the specified document was found.
            Patient patient = new Patient(); //Creates a new Patient object, that will be built.

            //The Patient objects instance variables wil be set by the following code.
            patient.setPatientId(result.getString("_id")); 
            patient.setFirstName(result.getString("first_name"));
            patient.setSurname(result.getString("surname"));
            
            //Checks if the Patient Object in the database is listed with any closeRelatives.
            if(result.containsKey("close_relatives")) { 

                ArrayList<CloseRelative> closeRelatives = new ArrayList<>();

                /*
                 * If the test over returns true, the following code will create a object from the
                 * the returned field.
                 */
                Object closeRelativesObject = result.get("close_relatives"); 
                if(closeRelativesObject instanceof List<?>) { //Checks if the closeRelativesObject indeed is a List.
                    /*
                     * The reason for why List<question mark> is used here is because the compiler can not 
                     * guarantee that the cast of List<specifiedObjectType> will work. Therefor, for safe handling
                     * we are only interested to see if the embedded document indeed is a List object.
                     */
                    List<?> closeRelativeDocuments = (List<?>) closeRelativesObject;
                    for (Object object : closeRelativeDocuments) {
                       

                        if (object instanceof Document) {
                            Document closeRelativeDocument = (Document) object;

                            String id = closeRelativeDocument.getString("_id");
                            String firstName = closeRelativeDocument.getString("first_name");
                            String surname = closeRelativeDocument.getString("surname");

                            closeRelatives.add(new CloseRelative(id, firstName, surname));
                        }
                    }
                }

                patient.setCloseRelatives(closeRelatives);

            }

            return patient;

        }

        else {
            throw new Exception("No patient was found.");
        }
    }

    
    @Override
    public HealthCarePersonnel getHealthCarePersonnel(String employeeNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CloseRelative getCRelative(String patientId, String firstName) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
