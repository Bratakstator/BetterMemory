package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertActivityOrDay;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

@ExtendWith(MockitoExtension.class)
public class ActivityQueStateCheckerTest {
    @Mock
    private StaticContainerHandler<ActivityDTO> mockArrayHandler;
    @Mock
    private InsertActivityOrDay<Activity> mockInsertActivity;
    @InjectMocks
    private ActivityQueStateChecker stateChecker;

    @BeforeEach
    void setup() {
        stateChecker = new ActivityQueStateChecker(mockArrayHandler, mockInsertActivity);
    }

    // I will come back to these.
    @Test
    public void testCheckQueStateWhenConcluded() {
    }
}
