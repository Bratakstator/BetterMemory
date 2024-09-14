package no.bettermemory.models.users;

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
