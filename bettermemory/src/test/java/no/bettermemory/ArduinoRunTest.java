package no.bettermemory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.ArrayHandlers.ArrayDTOHandler;
import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.ArduinoActivityCommunicator;

@ExtendWith(MockitoExtension.class)
public class ArduinoRunTest {
    @Mock
    private ArrayDTOHandler<ActivityDTO> arrayHandeler;

    @Mock
    private Activity activity;

    static String shortDescription = "Drink Water"; 
    static String longDescription = "2 glasses of water before 2 pm";
    static int hour = 13;
    static int minutes = 00;


    /*
     * This test aims to test the ArduinoSendActivety method that uses comports
     * the test requires to have an arduino conected to the com3 port of your computer
     * as well as having the Arduino.ino installed, and the correct conections on the board.
     * 
     */
    @Test
    public void testArduinoComAndReceive() {
        Mockito.when(arrayHandeler.getAttributeOf(eq(0), any())).thenReturn(activity);
        Mockito.when(activity.getShortDescription()).thenReturn(shortDescription);
        Mockito.when(activity.getLongDescription()).thenReturn(longDescription);
        Mockito.when(activity.getHour()).thenReturn(hour);
        Mockito.when(activity.getMinutes()).thenReturn(minutes);

        ArduinoActivityCommunicator Arduino1 = new ArduinoActivityCommunicator(arrayHandeler);
        SerialPort comport = Arduino1.openPort();
        Arduino1.arduinoSendActivity(comport);
        Arduino1.closePort(comport);
    }
}
