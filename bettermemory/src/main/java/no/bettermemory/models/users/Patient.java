package no.bettermemory.models.users;

import java.util.ArrayList;

/**
 * This class is meant to represent a patient-object. This object will be used to hold necessary
 * information about a patient to be used in relation to activities and when to do them.
 * 
 * @param patientId - Is the patient id assigned by the governing health sector.
 * @param firstName - Is the first name of the patient.
 * @param surname - Is the surname / last name of the patient.
 * 
 * @author Joakim Klemsdal Bøe
 * @version 1.1
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code Patient patient = new Patient("123", "Jane" "Doe");}
 */
public class Patient {

    
    private String patientId;
    private String firstName;
    private String surname;

    // I'm just gonna leave this attribute out of the parameter for now
    private ArrayList<CloseRelative> closeRelatives = new ArrayList<>();
       
    public Patient(String patientId, String firstName, String surname){
        this.patientId = patientId;
        this.firstName = firstName;
        this.surname = surname;
    }

    /** 
     * Creates a CloseRelative-object to be added to the list of close relatives in patient-object.
     * 
     * @param firstName - The first name of the close relative.
     * @param surname - The surname / last name of the close relative.
     * 
     * @author Joakim Klemsdal Bøe
     * 
     * @code
     * How to use:
     * <pre>{@code patientObject.addCloseRelative("John", "Doe");}
    */
    public void addCloseRelative(String firstName, String surname) {
        CloseRelative closeRelative = new CloseRelative(firstName, surname, this);
        closeRelatives.add(closeRelative);
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

    public ArrayList<CloseRelative> getCloseRelatives() {
        return closeRelatives;
    }
}