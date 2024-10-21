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
public class ArrayHandlerTest {
    @Mock
    private ObjectId objectId;
    @Mock
    private Activity activity;

    @Test
    public void testArrayShift() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[5]);
        ActivityDTO[] activities = displayArray.getArray();

        activities[0] = new ActivityDTO(objectId, activity);
        activities[1] = null;
        activities[2] = null;
        activities[3] = new ActivityDTO(objectId, activity);
        activities[4] = new ActivityDTO(objectId, activity);

        // Act
        displayArray.nullShiftRight();

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
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[1]);
        displayArray.getArray()[0] = null;

        // Act
        boolean hasNull = displayArray.hasNulls();

        // Assert
        assertEquals(true, hasNull);
    }

    @Test
    public void testHasNullWithNoNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[1]);
        displayArray.getArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        boolean hasNull = displayArray.hasNulls();

        // Assert
        assertEquals(false, hasNull);
    }

    @Test
    public void testGetFirstNullWithNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[2]);
        displayArray.getArray()[0] = new ActivityDTO(objectId, activity);
        displayArray.getArray()[1] = null;

        // Act
        int nullPos = -1;
        try {
            nullPos = displayArray.getFirstNullIndex();
        } catch (Exception e) {
            System.err.println(e);
        }

        // Assert
        assertEquals(1, nullPos);
    }

    @Test
    public void testGetFirstNullWithNoNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[1]);
        displayArray.getArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        Exception exception = assertThrows(Exception.class, () -> displayArray.getFirstNullIndex());

        // Assert
        assertEquals("Array does not contain null values.", exception.getMessage());
    }

    @Test
    public void testGetAttributeOfElementFromIndexInBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[2]);
        displayArray.getArray()[0] = new ActivityDTO(objectId, activity);
        displayArray.getArray()[1] = new ActivityDTO(objectId, activity);

        // Act
        Activity activity1 = displayArray.getAttributeOf(0,ActivityDTO::getActivity);
        Activity activity2 = displayArray.getAttributeOf(1, ActivityDTO::getActivity);

        // Assert
        assertNotNull(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetAttributeOfElementFromIndexOutOfBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> displayArray = new ArrayDTOHandler<>(new ActivityDTO[1]);

        // Act
        IndexOutOfBoundsException exception = assertThrows(
            IndexOutOfBoundsException.class,
            () -> displayArray.getAttributeOf(1, ActivityDTO::getActivity)
        );

        // Assert
        assertEquals("Index too large, Array only 1 long.", exception.getMessage());
    }
}
