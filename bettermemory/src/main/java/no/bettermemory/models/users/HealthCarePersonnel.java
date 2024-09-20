
package no.bettermemory.models.users;

import java.util.ArrayList;

public class HealthCarePersonnel {

    private String employeeNumber;
    private String firstName;
    private String surname;
    private ArrayList<String> connectedPatients;


    public HealthCarePersonnel(String employeeNumber, String firstName, String surname) {
        this.employeeNumber = employeeNumber.toLowerCase();
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();
    }

    public HealthCarePersonnel(String employeeNumber, String firstName, String surname, ArrayList<String> connectedPatients) {
        this.employeeNumber = employeeNumber.toLowerCase();
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();
        this.connectedPatients = connectedPatients;
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

    public ArrayList<String> getConnectedPatients(){
        return connectedPatients;
    }

    public void setConnectedPatients(ArrayList<String> connectedPatient) {
        this.connectedPatients = connectedPatient;
    }

    public void addPatientNumber(String patientId) {
        this.connectedPatients.add(patientId);
    }

    







}