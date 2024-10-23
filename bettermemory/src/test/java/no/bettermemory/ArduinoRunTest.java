package no.bettermemory;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.tools.ArduinoActivityCommunicator;

public class ArduinoRunTest {
    static String shortDescription = "Drink Water"; 
    static String longDescription = "2 glasses of water before 2 pm";
    static int hour = 13;
    static int minutes = 00;

    static String shortDescription2 = "Eat Dinner"; 
    static String longDescription2 = "Consume the preprered frozen meal";
    static int hour2 = 16;
    static int minutes2 = 00;


    /*
     * This test aims to test the ArduinoSendActivety method that uses comports
     * the test requires to have an arduino conected to the com3 port of your computer
     * as well as having the Arduino.ino installed, and the correct conections on the board.
     * 
     */
public static void main(String[] args) {
    ArduinoActivityCommunicator Arduino1 = new ArduinoActivityCommunicator();
    SerialPort comport = Arduino1.OpenPort();
    Arduino1.ArduinoSendActivety(shortDescription, longDescription, hour, minutes, comport);

    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    Arduino1.ArduinoSendActivety(shortDescription2, longDescription2, hour2, minutes2, comport);
    Arduino1.ClosePort(comport);
}
}
