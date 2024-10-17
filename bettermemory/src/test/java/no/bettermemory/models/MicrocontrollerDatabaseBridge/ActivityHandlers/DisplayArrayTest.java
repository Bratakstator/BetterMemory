package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Test
    public void testArrayShift() {
        // Arrange
        DisplayArray displayArray = new DisplayArray(5);
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
        DisplayArray displayArray = new DisplayArray(1);
        displayArray.getActivityArray()[0] = null;

        // Act
        boolean hasNull = displayArray.hasNull();

        // Assert
        assertEquals(true, hasNull);
    }
}
