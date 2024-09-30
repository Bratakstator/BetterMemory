package no.bettermemory.interfaces.storageHandlers.storageGetters;


import no.bettermemory.models.users.CloseRelative;
import no.bettermemory.models.users.Patient;
import no.bettermemory.models.users.HealthCarePersonnel;

/**
 * This interface should be used as a template for building a 
 * class for extrackting user information from a database and 
 * in to the system.
 */
public interface GetUser {

    /**
     * This methode should take the following parameters patientId.
     * And by accessing the database, exstract the correct values to create and return a 
     * Patient object for a given patientNumber.
     * @param patientId
     * @return Patient
     * @throws Exception
     */
    Patient getPatient(String patientId) throws Exception;

    /**
     * This methode should take the following parameters patientId and firstname.
     * And by accessing the database, exstract the correct values to create and return a 
     * CloseRelative object for a given patient.
     * @param patientId
     * @param firstName -The firstname of the CloseRelative.
     * @return CloseRelative
     * @throws Exception
     */
    CloseRelative getCloseRelative(String patientId, String firstName) throws Exception;

    /**
     * This methode should take the following parameter employeeId.
     * And by accessing the database, exstract the correct values to create and return a 
     * HealthCarePersonnel object for a given patient.
     * @param employeeNumber
     * @return HealthCarePersonnel
     * @throws Exception
     */
    HealthCarePersonnel getHealthCarePersonnel(String employeeNumber) throws Exception;




    
}
