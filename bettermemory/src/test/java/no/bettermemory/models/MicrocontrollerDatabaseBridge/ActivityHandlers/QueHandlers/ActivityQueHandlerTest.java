package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActivityQueHandlerTest {
    @Mock
    private ActivityQueStateChecker mockStateChecker;
    @Mock
    private ActivityQueInserter mockQueInserter;
    @InjectMocks
    private ActivityQueHandler queHandler;

    @BeforeEach
    void setUp() {
        queHandler = new ActivityQueHandler(mockStateChecker, mockQueInserter);
    }

    @Test
    public void testRunMethod() throws Exception {
        // Act
        queHandler.run();

        // Assert
        verify(mockStateChecker).checkQueState();
        verify(mockQueInserter).checkNullsAndAddToList();
    }

    @Test
    public void testRunMethodWithException() throws Exception {
        // Arrange
        doThrow(new Exception("Exception")).when(mockQueInserter).checkNullsAndAddToList();

        // Act
        queHandler.run();

        // Assert
        verify(mockStateChecker).checkQueState();
    }
}
