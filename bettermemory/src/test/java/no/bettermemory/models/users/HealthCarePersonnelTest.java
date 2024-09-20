package no.bettermemory.models.users;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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

    
    
}
