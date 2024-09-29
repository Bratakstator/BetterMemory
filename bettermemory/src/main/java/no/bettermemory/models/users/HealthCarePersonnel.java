
package no.bettermemory.models.users;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is meant to represent a healthCarePersonnel-object. This object work as way to include
 * new healthCarePersonnel to the system. This class will contain information about the employee and 
 * who which patients this employee is allowed to view data on.
 * 
 * @param employeeNumber 
 * @param firstName 
 * @param surname 
 * @param connectedPatients
 * 
 * @author Hermann Mjelde Hamnnes
 * 
 * @code
 * This is how you can create an object of this class:
 * <pre>{@code String employeeNumber = "D003809";
        String firstName = "Elissa";
        String surname = "Andersen";
        HealthCarePersonnel healthCarePersonnel = new HealthCarePersonnel(employeeNumber, firstName, surname);} </pre>
 */
public class HealthCarePersonnel {

    private String employeeNumber;
    private String firstName;
    private String surname;
    private Set<String> connectedPatients;

    /**
     * Default constructor.
     */
    public HealthCarePersonnel(){

    }

    public HealthCarePersonnel(String employeeNumber, String firstName, String surname) {
        this.employeeNumber = employeeNumber.toLowerCase();
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();
    }

    public HealthCarePersonnel(String employeeNumber, String firstName, String surname, Set<String> connectedPatients) {
        this.employeeNumber = employeeNumber.toLowerCase();
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();
        this.connectedPatients = new HashSet<>(connectedPatients); //To ensure uniqueness
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<String> getConnectedPatients(){
        return connectedPatients;
    }

    public void setConnectedPatients(Set<String> connectedPatient) {
        this.connectedPatients = connectedPatient;
    }

    public void addPatientNumber(String patientId) {
        this.connectedPatients.add(patientId);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Employee number: " + employeeNumber);
        string.append("\nFirst name: " + firstName);
        string.append("\nSurname: " + surname);
        if (connectedPatients != null) {
            string.append("\nConnected patients:");
            for (String patient : connectedPatients) {
                string.append("\n" + patient);
            }
        }

        return string.toString();
    }

    







}