package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
        assertNotEquals(activities[0], null);
        assertNotEquals(activities[1], null);
        assertNotEquals(activities[2], null);
        assertEquals(activities[3], null);
        assertEquals(activities[4], null);
    }
}
