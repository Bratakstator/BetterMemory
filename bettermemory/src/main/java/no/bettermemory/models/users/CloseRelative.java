package no.bettermemory.models.users;



/**
 * This class is meant to represent an object of a patients close relative.
 * 
 * @param firstName - Is the relatives first name.
 * @param surname - Is the relatives surname.
 * @param patient - This is a patient-object that this person is a relative of, used to assign a unique id for this user.
 * 
 * @author Joakim Klemsdal Bøe
 * @version 1
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code CloseRelative closeRelative = new CloseRelative("John", "Doe", patientZero)}
 */
public class CloseRelative {
    private final String id; // This is not to be changed after being assigned, thus final
    private String firstName;
    private String surname;

    /**
     * This constructor should be used in case you need to create a CloseRelative object 
     * from information retrieved from a database.
     * @param id
     * @param firstName
     * @param surname
     */
    public CloseRelative(String id, String firstName, String surname) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
    }

    public CloseRelative(String firstName, String surname, Patient patient) {
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();

        /*
         * The unique identification for the close relative will be custom made, it will be made based on the patient id
         * i.e. a close relative will be added to the patient, the patient has the id 012, the relative will get the id
         * 012-1, when another relative is added to the patient their id will be 012-2, and so on.
         */
        this.id = patient.getPatientId() + "-" + (patient.getCloseRelatives().size() + 1);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "ID: " + id
            +"\nFirst name: " + firstName
            +"\nSurname: " + surname;

    }
}
