package no.bettermemory.tools;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ArduinoActivityCommunicator implements Runnable {
    private StaticContainerHandler<ActivityDTO> arrayHandeler;
    boolean running = true;

    public ArduinoActivityCommunicator(StaticContainerHandler<ActivityDTO> arrayHandeler){
        this.arrayHandeler = arrayHandeler;
    }

    private String compiledString = "";
    private String minutesText;
    private String hourText;

    //creates massge that will be sendt to arduino
    public String BuildMessage(String shortDescription, String longDescription, int hour, int minutes){
        if (hour < 10){hourText = "0" + hour;} // gives time format eks: 09:05 insted of 9:50
        else{hourText = "" + hour;}

        if (minutes < 10){minutesText = "0" + minutes;} // gives time format eks: 10:05 insted of 10:5
        else{minutesText = "" + minutes;}
        String message = shortDescription + ": " + longDescription + "@" + hourText + ":" + minutesText + "@\n";
        return message;
    }

    public void run() {
        arduinoSendActivity(openPort());
    }

    public void stop() {
        running = false;
    }


    //opens port
    public SerialPort openPort(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(9600);
        if (comPort.openPort()) {
            System.out.println("Port is open!");
            clearInputBuffer(comPort);
        } else {
            System.out.println("Failed to open the port.");
        }

        // Wait for 2 seconds to give the Arduino time to reset
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return comPort;
    }


    //closes port
    public void closePort(SerialPort comportRecived){
        SerialPort comPort = comportRecived;
        comPort.closePort();
        System.out.println("Port is closed!");
    }


    //Send activety
    public void arduinoSendActivity(SerialPort comportRecived){
        SerialPort comPort = comportRecived;
        try ( // looks after a respons from the arduino
                Scanner data = new Scanner(comPort.getInputStream())) {

            // Keep checking for data in a loop
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (arrayHandeler) {
                        Activity activity = arrayHandeler.getAttributeOf(0, ActivityDTO::getActivity);
                        String longDescription = activity.getLongDescription();
                        String shortDescription = activity.getShortDescription();
                        int hour = activity.getHour();
                        int minutes = activity.getMinutes();

                        compiledString = BuildMessage(shortDescription, longDescription, hour, minutes);

                        // Convert the message to bytes and send it
                        byte[] messageBytes = compiledString.getBytes();
                        System.out.println("AAC "+activity);
                        comPort.writeBytes(messageBytes, messageBytes.length);
                        // Check if there is any data available in the serial buffer
                        if (comPort.bytesAvailable() > 0) {

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                                break;
                            }

                            System.out.println("AAC "+ "I will read");
                            // Read the data into the buffer
                            String line;
                            try {
                                line = data.nextLine();
                            } catch (NoSuchElementException e) {
                                System.err.println(e);
                                line = "";
                                continue;
                            }
                            //System.out.println(receivedData);
                        
                            if (line.trim().equals("ButtonPressed")) {
                                activity.setConcluded(true);
                            }
                        }   
                    }
                } catch (NullPointerException e) {
                    compiledString = "Waiting for activity\n";

                    // Convert the message to bytes and send it
                    byte[] messageBytes = compiledString.getBytes();
                    comPort.writeBytes(messageBytes, messageBytes.length);

                    System.err.println("AAC "+ "Is null");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clearInputBuffer(SerialPort comPort) {
        while (comPort.bytesAvailable() > 0) {
            try {
                comPort.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
