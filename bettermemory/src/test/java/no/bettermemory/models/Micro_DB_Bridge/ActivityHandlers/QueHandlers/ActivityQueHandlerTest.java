package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers.ActivityQueHandler;
import no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers.ActivityQueInserter;
import no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.QueHandlers.ActivityQueStateChecker;

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
