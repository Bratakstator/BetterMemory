package no.bettermemory.models.storageHandlers.databaseInsertions;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;
import no.bettermemory.models.users.Patient;

public class InsertPatientToMongoDB implements InsertToDatabase<Patient> {
    
    @Override
    public void saveObject(Patient patient) throws Exception {
    }
}
