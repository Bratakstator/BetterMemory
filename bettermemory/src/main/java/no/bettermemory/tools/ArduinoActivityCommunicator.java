package no.bettermemory.tools;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;
import no.bettermemory.models.DTO.ActivityDTO;
import no.bettermemory.models.activity.Activity;

public class ArduinoActivityCommunicator {
    private StaticContainerHandler<ActivityDTO> arrayHandeler;

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


    //opens port
    public SerialPort OpenPort(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(9600);
        if (comPort.openPort()) {
            System.out.println("Port is open!");
            
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
    public void ClosePort(SerialPort comportRecived){
        SerialPort comPort = comportRecived;
        comPort.closePort();
        System.out.println("Port is closed!");
    }


    //Send activety
    public void ArduinoSendActivity(SerialPort comportRecived){
        Activity activity = arrayHandeler.getAttributeOf(0, ActivityDTO::getActivity);
        String longDescription = activity.getLongDescription();
        String shortDescription = activity.getShortDescription();
        int hour = activity.getHour();
        int minutes = activity.getMinutes();

        compiledString = BuildMessage(shortDescription, longDescription, hour, minutes);
        SerialPort comPort = comportRecived;

        // Convert the message to bytes and send it
        byte[] messageBytes = compiledString.getBytes();
        comPort.writeBytes(messageBytes, messageBytes.length);


        try ( // looks after a respons from the arduino
                Scanner data = new Scanner(comPort.getInputStream())) {


            // Keep checking for data in a loop
            while (true) {
                // Check if there is any data available in the serial buffer
                if (comPort.bytesAvailable() > 0) {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("I will read");
                    // Read the data into the buffer
                    String line = data.nextLine().trim();
                    //System.out.println(receivedData);
                
                    if (line.equals("ButtonPressed")) {
                        activity.setConcluded(true);
                    }
                    break;
                }   
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
