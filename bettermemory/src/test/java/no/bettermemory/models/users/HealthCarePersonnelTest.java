package no.bettermemory.models.users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;



public class HealthCarePersonnelTest {
    @Test
    public void testHealthCarePersonelConstructor() {

        String employeeNumber = "D003809";
        String firstName = "Elissa";
        String surname = "Andersen";
        HealthCarePersonnel healthCarePersonel = new HealthCarePersonnel(employeeNumber, firstName, surname);

        assertNotNull(healthCarePersonel);
        assertEquals(employeeNumber.toLowerCase(), healthCarePersonel.getEmployeeNumber());
        assertEquals(firstName.toLowerCase(), healthCarePersonel.getFirstName());
        assertEquals(surname.toLowerCase(), healthCarePersonel.getSurname());
        

    }

    @Test 
    @DisplayName("Test if the HealthCarePersonel constructor initializes correctly with patient list.")
    public void testHealthCarePersonelConstructorWithPatientList() {
        
        ArrayList<String> patients = new ArrayList<>();
        patients.add("03030303");
        patients.add("9494949");
        patients.add("0303030");
        patients.add("9494994");

        ArrayList<String> copyPatients = new ArrayList<>(patients);
        
        
        String employeeNumber = "D003809";
        String firstName = "Elissa";
        String surname = "Andersen";
        HealthCarePersonnel healthCarePersonnel = new HealthCarePersonnel(employeeNumber, firstName, surname, patients);

        assertNotNull(healthCarePersonnel);
        assertEquals(employeeNumber.toLowerCase(), healthCarePersonnel.getEmployeeNumber());
        assertEquals(firstName.toLowerCase(), healthCarePersonnel.getFirstName());
        assertEquals(surname.toLowerCase(), healthCarePersonnel.getSurname());
        assertFalse(healthCarePersonnel.getConnectedPatients().isEmpty());

        
            
        for (int x = 0; x < patients.size(); x++) {
            assertTrue(patients.contains(copyPatients.get(x)));
        }
        



    }


    
    
}
