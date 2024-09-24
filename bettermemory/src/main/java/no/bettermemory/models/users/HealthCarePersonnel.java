
package no.bettermemory.models.users;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is ment to represent a healthCarePerosnel-object. This object work as way to include
 * new heealthCarePerosnel to the system. This class will contain information abot the employee and 
 * who which patients this employee is allowed to wiev data on.
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
        HealthCarePersonnel healthCarePersonel = new HealthCarePersonnel(employeeNumber, firstName, surname);} </pre>
 */
public class HealthCarePersonnel {

    private String employeeNumber;
    private String firstName;
    private String surname;
    private Set<String> connectedPatients;


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

    







}