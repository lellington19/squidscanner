
import rxtxrobot.*;

public class Sensor{


//Your main method, where your program starts

public static void main(String[] args) {

 //Connect to the arduino
 robot = new ArduinoNano();
 robot.setPort("COM3");
 robot.connect();

 //Get the average thermistor reading
 int thermistorReading = getThermistorReading();

 //Print the results
 System.out.println("The probe read the value: " + thermistorReading);
 System.out.println("In volts: " + (thermistorReading * (5.0/1023.0)));
 }

public static int getThermistorReading() {
 int sum = 0;
 int readingCount = 10;

 //Read the analog pin values ten times, adding to sum each time
 for (int i = 0; i < readingCount; i++) {

 //Refresh the analog pins so we get new readings
 robot.refreshAnalogPins();
 int reading = robot.getAnalogPin(0).getValue();
 sum += reading;
 }

 //Return the average reading
 return sum / readingCount;
 }
 }
