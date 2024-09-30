package no.bettermemory.models.storageHandlers.databaseExtraction;

import no.bettermemory.models.users.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class userObjectBuilder {

    public Patient buildPatient(Document result) {
        Patient patient = new Patient(); //Creates a new Patient object, that will be built.

        //The Patient objects instance variables wil be set by the following code.
        patient.setPatientId(result.getString("_id")); 
        patient.setFirstName(result.getString("first_name"));
        patient.setSurname(result.getString("surname"));

        //Checks if the Patient Object in the database is listed with any closeRelatives.
        if(result.containsKey("close_relatives")) {
            ArrayList<CloseRelative> closeRelatives = new ArrayList<>();
            Object closeRelativesObject = result.get("close_relatives"); 

        
        }


        return null;



    }

    public CloseRelative buildCloseRelative(Document result) {
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

                   
                }
            }
        }
        return null;
    }
    
}
