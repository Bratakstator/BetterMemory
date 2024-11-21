package no.bettermemory.tools;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

/**
 * This class is ment to be a tool for communicating to the arduino, it is formatted in a way that the arduino.ino file can read
 * 
 * @param arrayHandeler - used to hold the activitys that will be sendt to arduino
 * @param running - indicates that the run thread is active
 * @param compiledString - holds message after compiling together diferent values like minuits and houres
 * @param minutesText - holds minuits that has been turned into string format (9:5 -> 09:05)
 * @param hourText - holds houres that has been turned into string format (9:5 -> 09:05)
 * 
 * @author Hans Henrik Disen & Joakim Klemsdal Bøe
 * 
 * @code
 * When using arduinoSendActivity you have to openPort befor, and cloePort after
 * <pre>{@code 
 * ArduinoActivityCommunicator arduinoActivityCommunicator = new ArduinoActivityCommunicator(arrayDTOHandler);
 * arduinoSendActivity(openPort());
 * } </pre>
 */


public class ArduinoActivityCommunicator implements Runnable {
    private StaticContainerHandler<ActivityDTO> arrayHandeler;
    boolean running = true;
    private String compiledString = "";
    private String minutesText;
    private String hourText;



    public ArduinoActivityCommunicator(StaticContainerHandler<ActivityDTO> arrayHandeler){
        this.arrayHandeler = arrayHandeler;
    }



    /**
     * This method is compiling the String that gets sent to the arduino
     * 
     * @param shortDescription - Title of activity
     * @param longDescription - Description of the activity 
     * @param hour
     * @param minutes
     * @return
     * 
     * @author Hans Henrik Disen
     */

    public String BuildMessage(String shortDescription, String longDescription, int hour, int minutes){
        if (hour < 10){hourText = "0" + hour;} // gives time format eks: 09:05 insted of 9:50
        else{hourText = "" + hour;}

        if (minutes < 10){minutesText = "0" + minutes;} // gives time format eks: 10:05 insted of 10:5
        else{minutesText = "" + minutes;}

        String message = shortDescription + ": " + longDescription + "@" + hourText + ":" + minutesText + "@\n";
        return message;
    }


    /**
     * This funktion is used to start the thread process where, this ensures that the communicator can 
     * continue to update the arduino on what activity is active
     * 
     * @author Joakim Klemsdal Bøe
     */
    @Override
    public void run() {
        arduinoSendActivity(openPort());
    }


    public void stop() {
        running = false;
    }



    /**
     * this method is used to initiate the usb object
     * 
     * @param comPort - object that is conected to usb writing and reading
     * 
     * @author Hans Henrik Disen
     */

    public SerialPort openPort(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(9600); //sets communication speed so that both sides can read correctly
        if (comPort.openPort()) { //opens port here!
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


    /**
     *This function closes the comport, this is required if you would connect it to another program, like the arduino IDE
     * 
     * @param comportRecived - this function is dependent on having an item to close, that is the 
     * 
     * @author Hans Henrik Disen
     */
    public void closePort(SerialPort comportRecived){
        SerialPort comPort = comportRecived;
        comPort.closePort();
        System.out.println("Port is closed!");
    }

    /**
     * This is the main function of this class, it is responsible for sending the activity to the arduino
     * And it is dependant on that the arrayHandeler is holding activities
     * 
     * @param comportRecived - used for comunication
     * 
     * @author Hans Henrik Disen & Joakim Klemsdal Bøe
     */
    //Send activety
    public void arduinoSendActivity(SerialPort comportRecived){
        SerialPort comPort = comportRecived;

        try ( // looks after a respons from the arduino
                Scanner data = new Scanner(comPort.getInputStream())) {
            // Keep checking for data in a loop
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (arrayHandeler) { 
                        //the following lines retrieves values from the active activity that is first in the array handler 
                        Activity activity = arrayHandeler.getAttributeOf(0, ActivityDTO::getActivity);
                        String longDescription = activity.getLongDescription();
                        String shortDescription = activity.getShortDescription();
                        int hour = activity.getHour();
                        int minutes = activity.getMinutes();
                        //compiles the values into a string that the arduino understands
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
