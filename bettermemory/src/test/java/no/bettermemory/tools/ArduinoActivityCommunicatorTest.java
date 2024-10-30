package no.bettermemory.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.models.DTO.ActivityDTO;

class ArduinoActivityCommunicatorTest {

    @Mock
    private StaticContainerHandler<ActivityDTO> mockArrayHandler;

    @Mock
    private SerialPort mockSerialPort;

    @InjectMocks
    private ArduinoActivityCommunicator communicator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        communicator = new ArduinoActivityCommunicator(mockArrayHandler);
    }

    @Test
    void testBuildMessage() {
        String message = communicator.BuildMessage("ShortDesc", "LongDesc", 9, 5);
        assertEquals("ShortDesc: LongDesc@09:05@\n", message);
    }


    @Test
    void testClosePort() {
        communicator.closePort(mockSerialPort);
        verify(mockSerialPort).closePort();
    }

}
