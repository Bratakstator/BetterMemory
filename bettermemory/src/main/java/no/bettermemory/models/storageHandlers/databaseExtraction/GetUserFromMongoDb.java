package no.bettermemory.models.storageHandlers.databaseExtraction;

import no.bettermemory.interfaces.storageHandlers.storageGetters.GetUser;
import no.bettermemory.tools.DatabaseConnections;

import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import no.bettermemory.models.users.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * 
 */
public class GetUserFromMongoDb implements GetUser {

    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public GetUserFromMongoDb(MongoClient mongoClient) {
        database = DatabaseConnections.getUsersDatabase(mongoClient);
    }

    /**
     * This method should be used for creating Patient object from data stored in a 
     * mongoDB. 
     * @author Hermann Mjelde Hamnnes
     * @param patientId - All patients in the database are organized based on their patientId.
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
        collection = DatabaseConnections.getPatientCollection(database);
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

                            String id = closeRelativeDocument.getString("relative_id");
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


    /**
     * This method should be used for creating HealthCarePersonnel object from data stored in a 
     * mongoDB. 
     * @author Hermann Mjelde Hamnnes
     * @param employeeNumber - All health care personnel in the database are organized based on their employee number.
     * @code
     * This is an example of how you can use this method:
     * <pre>{@code  GetUserFromMongoDb getUserFromMongoDb = new GetUserFromMongoDb();

       try{
            HealthCarePersonnel healthCarePersonnel = getUserFromMongoDb.getHealthCarePersonnel("D003809");
            System.out.println(healthCarePersonnel);
        }

        catch (Exception exception) {
            System.err.println("It did not work");
            exception.printStackTrace();
        }
    */
    
    @Override
    public HealthCarePersonnel getHealthCarePersonnel(String employeeNumber) throws Exception {
        collection = DatabaseConnections.getHealthCarePersonnelCollection(database);
        Document query = new Document("_id", employeeNumber);
        Document result = collection.find(query).first();

       
        if (result != null) { //Checks if the specified document was found.
            HealthCarePersonnel healthCarePersonnel = new HealthCarePersonnel(); //Creates a new HealthCarePersonnel object, that will be built.

            //The HealthCarePersonnel objects instance variables wil be set by the following code.
            healthCarePersonnel.setEmployeeNumber(result.getString("_id"));
            healthCarePersonnel.setFirstName(result.getString("first_name"));
            healthCarePersonnel.setSurname(result.getString("surname"));


            //Checks if the HealthCarePersonnel Object in the database is listed with any connectedPatients.
            if(result.containsKey("connected_patients")) { 

                Set<String> connectedPatients = new HashSet<>();

                /*
                * If the test over returns true, the following code will create a object from the
                * the returned field.
                */
                Object connectedPatientsObject = result.get("connected_patients"); 
                if(connectedPatientsObject instanceof List<?>) { //Checks if the connected_patient field indeed is a List.
                    /*
                    * The reason for why List<question mark> is used here is because the compiler can not 
                    * guarantee that the cast of List<specifiedObjectType> will work. Therefor, for safe handling
                    * we are only interested to see if the embedded document indeed is a List object.
                    */
                    List<?> connectedPatientList = (List<?>) connectedPatientsObject;
                    for (Object object : connectedPatientList) {

                        if(object instanceof String){

                            String string = (String) object;
                            connectedPatients.add(string);
                        }
                        

                    }
                }

                healthCarePersonnel.setConnectedPatients(connectedPatients);

            }

            return healthCarePersonnel;

        }

        else {
            throw new Exception("No health care personnel was found.");
        }

    
    }

    @Override
    public CloseRelative getCloseRelative(String patientId, String firstName) throws Exception {
        collection = DatabaseConnections.getPatientCollection(database);
        Document query = new Document("_id", patientId);
        Document result = collection.find(query).first();

        if (result != null) { //Checks if the specified document was found.

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
                     * guarantee that the cast of List<specifiedObjectType> will work. Therefor, for safe handling
                     * we are only interested to see if the embedded document indeed is a List object.
                     */
                    List<?> closeRelativeDocuments = (List<?>) closeRelativesObject;


                        
                    for (Object object : closeRelativeDocuments) {
                
                        if (object instanceof Document) {
                            Document closeRelativeDocument = (Document) object;

                            if (closeRelativeDocument.containsValue(firstName)) {

                                String id = closeRelativeDocument.getString("relative_id");
                                String surname = closeRelativeDocument.getString("surname");

                                CloseRelative closeRelative = new CloseRelative(id, firstName, surname);

                                return closeRelative;


                            }

                            else {
                                throw new Exception("The patient has no close relatives with the provided name.");
                            }
                        }

                        else {
                            throw new Exception("Could not convert object to Document-type");
                        }
                    }

                    
                    
                }

                else {
                    throw new Exception("No close relative list was found.");
                }


            }

            else {
                throw new Exception("The patient has no close relatives");
            }


        }

        
        throw new Exception("No patient was found.");
        
    }
}
