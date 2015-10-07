import rxtxrobot.*;
import java.util.Scanner;

public class MotorStop
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		double x = 0;
		r.setPort("COM3"); // Set port to COM2
		r.connect();

		while(x < 4.5){

			r.refreshAnalogPins();
			AnalogPin temp = r.getAnalogPin(1);
			r.runMotor(RXTXRobot.MOTOR1, -500, RXTXRobot.MOTOR2, 500, 0); // Run both motors forward indefinitely
			 // Pause execution for 5 seconds, but the motors keep running.
			x = (temp.getValue() * (5.0/1023));
		}
		r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors
		r.close();
	}
}
