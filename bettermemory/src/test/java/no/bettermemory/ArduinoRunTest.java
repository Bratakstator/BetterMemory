package no.bettermemory;

import no.bettermemory.tools.ArduinoActivityCommunicator;


public class ArduinoRunTest {

    /*
     * This test aims to test the arduinoActivetyComunicator class
     */
    static String shortDescription = "Drink Water"; 
    static String longDescription = "2 glasses of water before 2 pm";
    static int hour = 13;
    static int minutes = 00;
public static void main(String[] args) {
    ArduinoActivityCommunicator Arduino1 = new ArduinoActivityCommunicator();
    Arduino1.ArduinoActivitySend(shortDescription, longDescription, hour, minutes);
     
}
}
