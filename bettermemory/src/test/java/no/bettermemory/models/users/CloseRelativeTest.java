package no.bettermemory.models.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CloseRelativeTest {

    @Test
    public void createObject() {
        // Arrange
        Patient patient = new Patient("123", "John", "Doe");

        // Act
        patient.addCloseRelative("Jane", "Doe");
        patient.addCloseRelative("Jack", "Doe");
        patient.addCloseRelative("John", "Doe jr.");

        // Assert
        ArrayList<CloseRelative> closeRelatives = patient.getCloseRelatives();
        int i = 0;
        for (CloseRelative closeRelative : closeRelatives) {
            i++;
            Assertions.assertEquals(patient.getPatientId() + "-" + i, closeRelative.getId());
        }
    }    
}
