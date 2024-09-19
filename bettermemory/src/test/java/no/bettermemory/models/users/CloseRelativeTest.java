package no.bettermemory.models.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CloseRelativeTest {

    @Test
    public void createObject() {
        // Arrange
        Patient patient = new Patient("John Doe", 123);

        // Act
        CloseRelative closeRelative1 = new CloseRelative("Jack", "Doe", patient);
        CloseRelative closeRelative2 = new CloseRelative("Jane", "Doe", patient);
        CloseRelative closeRelative3 = new CloseRelative("John", "Doe jr.", patient);

        // Assert
        Assertions.assertEquals(patient.getPatientId()+"-1", closeRelative1.getId());
        Assertions.assertEquals(patient.getPatientId()+"-2", closeRelative2.getId());
        Assertions.assertEquals(patient.getPatientId()+"-3", closeRelative3.getId());
    }    
}
