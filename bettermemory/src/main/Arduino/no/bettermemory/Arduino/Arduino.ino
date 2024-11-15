#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

//inititilazing display size and reset
#define SCREEN_WIDTH 128  // OLED display width, in pixels
#define SCREEN_HEIGHT 64  // OLED display height, in pixels
#define OLED_RESET    -1  

// Create SSD1306 display object
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

// Maximum length of text allowed (depends on the screen size and text size)
#define MAX_TEXT_LENGTH 80  

String receivedText = "";             // String to hold the received text
String receivedTime = "";             // String to hold the time of when the activety should be preformed
bool parsingTime = false;             // Flag to indicate if we are currently parsing the time
unsigned long timeStartActivity = 0;  // Variable to hold the time of when an activety is sendt to the arduino
unsigned long activityTimeDelay = 15000; // The time activety is held befor it counts it as not completed (1.8 mill = half an hour) but set to 15000 for testing purposes
const int buttonPin = 2;              // Pin where the button is connected
int buttonState = 0;                  // Variable for reading the buttonstate
int ledPin = 4;                       //Pin where led indicator is connected
bool activityActive = false;          // Flag to indicate if there is an activety waiting to be done


//This Arduino programs main objective is to receive text from the java program and display it
//on the OLED 128x64 pixels from adafruit, as well as responding to the java program in an appropriate time
//whether a button is pushed or not
//
// Author Hans Henrik Disen
//
//this is an example of a string received from java:
//"Take medisin: afternoon captopril @15:00@\n"


void setup() {
  // Initialize the serial communication at 9600 baud rate and button pin
  pinMode(buttonPin, INPUT);
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);

  // Initialize the OLED display
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {  // Address 0x3C for 128x64
    Serial.println(F("SSD1306 allocation failed"));
    for (;;);  // Don't proceed, loop forever
  }

  // Clear the display buffer
  display.clearDisplay();
  display.setTextSize(1);      // Set text size (adjustable)
  display.setTextColor(WHITE); // Set text color to white
  display.setCursor(0, 0);     // Start at the top-left corner
  display.display();           // Display the buffer

  // Print an initial message to the screen
  display.setCursor(0, 0);
  display.println("Waiting for input...");
  display.display();
}



void loop() {

  //
  //BUTTON PUSH METHOD
  // 

  buttonState = digitalRead(buttonPin);
  if (buttonState == HIGH && activityActive){
    Serial.println("ButtonPressed\n"); // Send data to Java when button is pressed
    delay(200); // Debounce delay
    activityActive = false;

    display.setCursor(0, 48); //displays that the activety registered sucsessfully
    display.println("Activity completed");
    display.display();// Update Display
    digitalWrite(ledPin, LOW);
  }
  else if (activityActive && (millis()-timeStartActivity) > activityTimeDelay){
    Serial.println("NoActivity\n");
    delay(200);
    activityActive = false;

    display.setCursor(0, 48); //displays that the activity registered sucsessfully
    display.println("Activity NOT complete");
    display.display();// Update Display
    digitalWrite(ledPin, LOW);
  }



  //
  //RECIVING TEXT METHOD
  //

  // Check if there is serial input available
  if (Serial.available() > 0) {
    // Read the incoming text from the Serial Monitor
    char incomingChar = Serial.read();  // Read one character at a time
    
    // If newline is received, process the message
    if (incomingChar == '\n') {
      // Clear the OLED display
      display.clearDisplay();
      display.setCursor(0, 0);  // Reset cursor to top-left
      display.println(receivedText);
      // Display the recived time on the bottem
      display.setCursor(0, 56);  // Reset cursor to bottem-left
      display.println(receivedTime);

      display.display();// Update Display

      if (receivedText == "Waiting for activity") {
        activityActive = false;
        digitalWrite(ledPin, LOW);
      } else {
        activityActive = true; // Primes putton reader
        digitalWrite(ledPin, HIGH);
      }
      
      // Clear the received text for the next message
      receivedText = "";
      receivedTime = "";
      timeStartActivity = millis(); // Timestamps when activity is recived

    } else if (incomingChar == '@' && !parsingTime) {
      // Detected start of time block
      parsingTime = true;
      receivedTime = "";  // Clear any previously received time
    } 

    else if (incomingChar == '@' && parsingTime) {
      // Detected end of time block
      parsingTime = false;
    }

    else {
      // Append the received character to either text or time, based on the flag
      if (parsingTime) {
        receivedTime += incomingChar;
      } else {
        receivedText += incomingChar;

        // Check if the received text exceeds the limit
        if (receivedText.length() > MAX_TEXT_LENGTH) {
          receivedText = receivedText.substring(0, MAX_TEXT_LENGTH);  // Truncate the text if too long
        }
      }
    }
  }
}



