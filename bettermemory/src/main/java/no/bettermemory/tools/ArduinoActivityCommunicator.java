package no.bettermemory.tools;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;

public class ArduinoActivityCommunicator {
    private String compiledString = "";

    public void ArduinoActivitySend(String shortDescription, String longDescription, int hour, int minutes){
        
        compiledString = shortDescription + ": " + longDescription + "@" + hour + ":" + minutes + "@\n";
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(9600);
        
        // Open the port
        if (comPort.openPort()) {
            System.out.println("Port is open!");
        } else {
            System.out.println("Failed to open the port.");
        }

        // Wait for 2 seconds to give the Arduino time to reset
        try {
            Thread.sleep(2000);  // Wait 2 seconds before sending data
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Convert the message to bytes and send it
        byte[] messageBytes = compiledString.getBytes();
        comPort.writeBytes(messageBytes, messageBytes.length);


        
        // Close the port after sending the message
        comPort.closePort();
        System.out.println("Port is closed!");

    public void ArduinoActivitySend(){

    }

}
