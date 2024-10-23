package no.bettermemory;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.tools.ArduinoActivityCommunicator;

public class ArduinoRunSplitTest {
static String shortDescription = "Drink Water"; 
    static String longDescription = "2 glasses of water before 2 pm";
    static int hour = 13;
    static int minutes = 00;
public static void main(String[] args) {
    ArduinoActivityCommunicator Arduino1 = new ArduinoActivityCommunicator();
    SerialPort comport = Arduino1.OpenPort();
    Arduino1.ArduinoSendActivety(shortDescription, longDescription, hour, minutes, comport);
    Arduino1.ClosePort(comport);
}
}
