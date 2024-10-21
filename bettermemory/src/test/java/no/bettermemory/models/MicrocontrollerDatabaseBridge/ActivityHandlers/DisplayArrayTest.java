package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

@ExtendWith(MockitoExtension.class)
public class DisplayArrayTest {
    @Mock
    private ObjectId objectId;
    @Mock
    private Activity activity;

    /*@Test
    public void testArrayShift() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(5);
        ActivityDTO[] activities = displayArray.getActivityArray();

        activities[0] = new ActivityDTO(objectId, activity);
        activities[1] = null;
        activities[2] = null;
        activities[3] = new ActivityDTO(objectId, activity);
        activities[4] = new ActivityDTO(objectId, activity);

        // Act
        displayArray.arrayShift();

        // Assert
        assertNotEquals(null, activities[0]);
        assertNotEquals(null, activities[1]);
        assertNotEquals(null, activities[2]);
        assertEquals(null, activities[3]);
        assertEquals(null, activities[4]);
    }

    @Test
    public void testHasNullWithNulls() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(1);
        displayArray.getActivityArray()[0] = null;

        // Act
        boolean hasNull = displayArray.hasNull();

        // Assert
        assertEquals(true, hasNull);
    }

    @Test
    public void testHasNullWithNoNulls() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(1);
        displayArray.getActivityArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        boolean hasNull = displayArray.hasNull();

        // Assert
        assertEquals(false, hasNull);
    }

    @Test
    public void testGetFirstNullWithNulls() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(2);
        displayArray.getActivityArray()[0] = new ActivityDTO(objectId, activity);
        displayArray.getActivityArray()[1] = null;

        // Act
        int nullPos = -1;
        try {
            nullPos = displayArray.getFirstNull();
        } catch (Exception e) {
            System.err.println(e);
        }

        // Assert
        assertEquals(1, nullPos);
    }

    @Test
    public void testGetFirstNullWithNoNulls() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(1);
        displayArray.getActivityArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        Exception exception = assertThrows(Exception.class, () -> displayArray.getFirstNull());

        // Assert
        assertEquals("Array does not contain null values.", exception.getMessage());
    }

    @Test
    public void testGetValueFromIndexInBounds() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(2);
        displayArray.getActivityArray()[0] = new ActivityDTO(objectId, activity);
        displayArray.getActivityArray()[1] = new ActivityDTO(objectId, activity);

        // Act
        Activity activity1 = displayArray.getValueFromIndex(0);
        Activity activity2 = displayArray.getValueFromIndex(1);

        // Assert
        assertNotNull(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetValueFromIndexOutOfBounds() {
        // Arrange
        ArrayHandler displayArray = new ArrayHandler(1);

        // Act
        IndexOutOfBoundsException exception = assertThrows(
            IndexOutOfBoundsException.class,
            () -> displayArray.getValueFromIndex(1)
        );

        // Assert
        assertEquals("Index too large, Array only 1 long.", exception.getMessage());
    }*/
}
