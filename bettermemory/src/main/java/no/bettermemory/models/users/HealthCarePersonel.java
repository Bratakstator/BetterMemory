
package no.bettermemory.models.users;

public class HealthCarePersonel {

    private int employeeNumber;
    private String firstName;
    private String surname;

    public HealthCarePersonel(int employeeNumber, String firstName, String surname) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.surname = surname;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
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