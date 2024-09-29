package no.bettermemory;

import no.bettermemory.models.storageHandlers.ToMongoDB;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.tools.databaseTools.GetUserFromMongoDb;


public class HermannFunctionTesting {

    public static void main(String[] args) {

        GetUserFromMongoDb getUserFromMongoDb = new GetUserFromMongoDb();

        try{
            Patient patient = getUserFromMongoDb.getPatient("2022002");
            System.out.println(patient);
        }

        catch (Exception exception) {
            System.err.println("It did not work");
            exception.printStackTrace();
        }



        

        
        
    }

}
