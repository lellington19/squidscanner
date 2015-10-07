// This example shows how to get the Analog pin sensor data from the Arduino.  It shows the value of every Analog pin once after connecting to the Arduino.
import rxtxrobot.*;

public class Temp{
  public static void main(String[] args){
    RXTXRobot r = new ArduinoNano();
    System.out.println("Thermister Reading...");
    int count = 0;
    double sum, voltage, temp;
    double slope = -10;
    double intercept = 919;
    System.out.print("Reading");
    sum = 0;
    r.setPort("COM3");
    r.connect();
    while(count <= 10){
      r.refreshAnalogPins();
      sum = sum + r.getAnalogPin(0).getValue();
      try {
        Thread.sleep(100);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
      System.out.print(" .");
      count = count + 1;
    }
    System.out.println(sum/count);
    temp = ((sum/count) - intercept) / slope;
    System.out.println("The temperature is: " + temp);
    r.close();
  }
}
