// This example shows how to get the Analog pin sensor data from the Arduino.  It shows the value of every Analog pin once after connecting to the Arduino.
import rxtxrobot.*;

public class robot
{
    public static void main(String[] args)
    {
	    // All sensor data will be read from the analog pins

	  RXTXRobot r = new ArduinoNano(); //Create RXTXRobot object    boolean crash = false;
		r.setPort("COM3"); // Sets the port to COM5

		r.connect();
    AnalogPin temp = r.getAnalogPin(0);
		 // Cache the Analog pin information

    for(int c = 0; c < 100; c++){
      r.refreshAnalogPins();

      System.out.println("------------------------------------------------");

    	for (int x=0; x < 7; ++x)
  		{
  			temp = r.getAnalogPin(x);
  			System.out.println("Sensor " + x + " has value: " + temp.getValue());
        System.out.println("The volatage read at was: " + (temp.getValue() * (5.0/1023)));

  		}
      if(r.getAnalogPin(0).getValue() < 0){ System.out.println("STAHP!"); r.close(); return; }

      try {
        Thread.sleep(3000);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
		r.close();
  }
}
