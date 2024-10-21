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
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[5]);
        ActivityDTO[] activities = array.getArray();

        activities[0] = new ActivityDTO(objectId, activity);
        activities[1] = null;
        activities[2] = null;
        activities[3] = new ActivityDTO(objectId, activity);
        activities[4] = new ActivityDTO(objectId, activity);

        // Act
        array.nullShiftRight();

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
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);
        array.getArray()[0] = null;

        // Act
        boolean hasNull = array.hasNulls();

        // Assert
        assertEquals(true, hasNull);
    }

    @Test
    public void testHasNullWithNoNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);
        array.getArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        boolean hasNull = array.hasNulls();

        // Assert
        assertEquals(false, hasNull);
    }

    @Test
    public void testGetFirstNullWithNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[2]);
        array.getArray()[0] = new ActivityDTO(objectId, activity);
        array.getArray()[1] = null;

        // Act
        int nullPos = -1;
        try {
            nullPos = array.getFirstNullIndex();
        } catch (Exception e) {
            System.err.println(e);
        }

        // Assert
        assertEquals(1, nullPos);
    }

    @Test
    public void testGetFirstNullWithNoNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);
        array.getArray()[0] = new ActivityDTO(objectId, activity);

        // Act
        Exception exception = assertThrows(Exception.class, () -> array.getFirstNullIndex());

        // Assert
        assertEquals("No nulls in array.", exception.getMessage());
    }

    @Test
    public void testGetAttributeOfElementFromIndexInBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[2]);
        array.getArray()[0] = new ActivityDTO(objectId, activity);
        array.getArray()[1] = new ActivityDTO(objectId, activity);

        // Act
        Activity activity1 = array.getAttributeOf(0, ActivityDTO::getActivity);
        Activity activity2 = array.getAttributeOf(1, ActivityDTO::getActivity);

        // Assert
        assertNotNull(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetAttributeOfElementFromIndexOutOfBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);

        // Act
        IndexOutOfBoundsException exception = assertThrows(
            IndexOutOfBoundsException.class,
            () -> array.getAttributeOf(1, ActivityDTO::getActivity)
        );

        // Assert
        assertEquals("Index out of bounds by an amount: 1", exception.getMessage());
    }

    @Test
    public void testAddToListWithNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);

        // Act
        try {
            array.add(new ActivityDTO(objectId, activity));
        } catch (Exception e) {
            System.err.println(e);
        }

        // Assert
        assertNotNull(array.getArray()[0]);
    }
}
