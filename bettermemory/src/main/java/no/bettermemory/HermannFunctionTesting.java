package no.bettermemory;

import no.bettermemory.models.storageHandlers.ToMongoDB;
import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;

public class HermannFunctionTesting {

    public static void main(String[] args) {
        Patient patient = new Patient("12346", "Trine", "Store");
        patient.addCloseRelative("Hermann", "some");

        Patient patient2 = new Patient("12345", "Ola", "Store");
        patient.addCloseRelative("Hermann", "some");

        ToMongoDB toMongoDB = new ToMongoDB(DatabaseConnections.getMongodbClientInfo());
        toMongoDB.saveObject(patient);

        
        toMongoDB.saveObject(patient2);
        
    }

}
