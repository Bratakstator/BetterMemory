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

    private String name;
    private int patientId;
    
    public Patient(String name, int patientId){
        this.name = name;
        this.patientId = patientId;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public int getPatientId() {
        return patientId;
    }
}
