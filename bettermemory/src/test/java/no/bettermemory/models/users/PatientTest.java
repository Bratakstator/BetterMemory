package no.bettermemory.models.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PatientTest {
    
    @Test
    public void addCloseRelative() {
        // Arrange
        Patient patient = new Patient("123", "John", "Doe");

        // Act
        patient.addCloseRelative("Jane", "Doe");
        patient.addCloseRelative("Jack", "Doe");
        patient.addCloseRelative("John", "Doe jr.");

        // Assert
        ArrayList<CloseRelative> closeRelatives = patient.getCloseRelatives();
        Assertions.assertNotNull(closeRelatives.get(0));
        Assertions.assertNotNull(closeRelatives.get(1));
        Assertions.assertNotNull(closeRelatives.get(2));
    }
}
