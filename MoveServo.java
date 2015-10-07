// This example shows how to move both servos simultaneously.  This is better than moving each one separately, as they move as close to simultaneously as possible.
import rxtxrobot.*;

public class MoveServo
{
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano();  // Create RXTXRobot object
		r.setPort("COM3"); // Set port to COM3
		r.connect();

		r.attachServo(RXTXRobot.SERVO1, 9); //Connect the servos to the Arduino
		System.out.println("test1");
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(3000);

		System.out.println("test2");
		r.moveServo(RXTXRobot.SERVO1, 180);
		r.sleep(3000);
		r.refreshAnalogPins();
		if(r.getAnalogPin(0).getValue() < 0){ System.out.println("STAHP!"); r.close(); return; }

		System.out.println("test3");
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(3000);


 // Move Servo 1 to position 20, Servo 2 to position 170, and Servo 3 to position 0.
		r.close();
	}
}
