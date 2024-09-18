
package no.bettermemory.models.users;

public class HealthCarePersonel {

    private String employeeNumber;
    private String firstName;
    private String surname;

    public HealthCarePersonel(String employeeNumber, String firstName, String surname) {
        this.employeeNumber = employeeNumber.toLowerCase();
        this.firstName = firstName.toLowerCase();
        this.surname = surname.toLowerCase();
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




}