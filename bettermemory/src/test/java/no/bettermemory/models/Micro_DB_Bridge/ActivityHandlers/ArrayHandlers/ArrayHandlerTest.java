package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.ArrayHandlers;

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
    private ObjectId mockObjectId;
    @Mock
    private Activity mockActivity;
    @Mock
    private int mockYear;
    @Mock
    private int mockWeekNumber;
    @Mock
    private String mockDayName;

    @Test
    public void testArrayShift() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[5]);
        ActivityDTO[] activities = array.getArray();

        activities[0] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);
        activities[1] = null;
        activities[2] = null;
        activities[3] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);
        activities[4] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);

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
        array.getArray()[0] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);

        // Act
        boolean hasNull = array.hasNulls();

        // Assert
        assertEquals(false, hasNull);
    }

    @Test
    public void testGetFirstNullWithNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[2]);
        array.getArray()[0] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);
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
        array.getArray()[0] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);

        // Act
        Exception exception = assertThrows(Exception.class, () -> array.getFirstNullIndex());

        // Assert
        assertEquals("No nulls in array.", exception.getMessage());
    }

    @Test
    public void testGetAttributeOfElementFromIndexInBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[2]);
        array.getArray()[0] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);
        array.getArray()[1] = new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName);

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
            array.add(new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName));
        } catch (Exception e) {
            System.err.println(e);
        }

        // Assert
        assertNotNull(array.getArray()[0]);
    }

    @Test
    public void testAddToListWithoutNulls() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(
            new ActivityDTO[]{new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName)}
        );

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> array.add(new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName))
        );

        // Assert
        assertEquals("No more space in array.", exception.getMessage());
    }

    @Test
    public void testAddAtIndexInBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[3]);

        // Act
        try {
            array.addAtIndex(
                1,
                new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName)
            );
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e);
        }

        // Assert
        assertNotNull(array.getArray()[1]);
    }

    @Test
    public void testAddAtIndexOutOfBounds() {
        // Arrange
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(new ActivityDTO[1]);

        // Act
        IndexOutOfBoundsException exception = assertThrows(
            IndexOutOfBoundsException.class,
            () -> array.addAtIndex(1, new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName))
        );

        // Assert
        assertEquals("Index out of bounds by an amount: 1", exception.getMessage());
    }

    @Test
    public void testGetInBounds() {
        // Assert
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(
            new ActivityDTO[]{new ActivityDTO(mockObjectId, mockActivity, mockYear, mockWeekNumber, mockDayName)}
        );

        // Act
        ActivityDTO activity;
        try {
            activity = array.get(0);
        } catch (IndexOutOfBoundsException e) {
            activity = null;
            System.err.println(e);
        }

        // Assert
        assertNotNull(activity);
    }

    @Test
    public void testGetOutOfBounds() {
        // Assert
        ArrayHandler<ActivityDTO> array = new ArrayDTOHandler<>(
            new ActivityDTO[1]
        );

        // Act
        IndexOutOfBoundsException exception = assertThrows(
            IndexOutOfBoundsException.class,
            () -> array.get(2)
        );

        // Assert
        assertEquals("Index out of bounds by an amount: 2", exception.getMessage());
    }
}
