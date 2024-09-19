package no.bettermemory.models.users;










/**
 * This class is meant to represent a patient-object. This object will be used to hold necessary
 * information about a patient to be used in relation to activities and when to do them.
 * 
 * @param name - Is the full name of the patient.
 * @param patientId - Is the patient id assigned by the governing health sector.
 * 
 * @author Joakim Klemsdal Bøe
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code Patient patient = new Patient("Jane Doe", 123456789)}
 */
public class Patient {

    
    private String patientId;
    private String firstName;
    private String surname;

    // I'm just gonna leave this attribute out of the parameter for now
    
    public Patient(String patientId, String firstName, String surname){
        this.patientId = patientId;
        this.firstName = firstName;
        this.surname = surname;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setFirstName(String firstName) { 
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
