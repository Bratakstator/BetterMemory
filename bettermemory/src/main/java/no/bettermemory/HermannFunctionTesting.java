package no.bettermemory;

import no.bettermemory.models.storageHandlers.ToMongoDB;
import no.bettermemory.models.users.CloseRelative;
import no.bettermemory.models.users.HealthCarePersonnel;
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

        try{
            HealthCarePersonnel healthCarePersonnel = getUserFromMongoDb.getHealthCarePersonnel("D003809");
            System.out.println(healthCarePersonnel);
        }

        catch (Exception exception) {
            System.err.println("It did not work");
            exception.printStackTrace();
        }

        try{
            CloseRelative closeRelative = getUserFromMongoDb.getCloseRelative("2022002", "Joakim");
            System.out.println(closeRelative);
        }

        catch (Exception exception) {
            System.err.println("It did not work");
            exception.printStackTrace();
        }



        

        
        
    }

}
