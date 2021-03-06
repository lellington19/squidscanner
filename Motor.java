// This example moves each servo individually.  While this method should be used to move one servo individually, it is recommended to use moveBothServos if both servos must be moved simultaneously
import rxtxrobot.*;

public class Motor
{
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
        String OSName = System.getProperty("os.name");
        if(OSName.equals("Mac OS X"))
        {
            r.setPort("/dev/tty.wch ch341 USB=>RS232 1420");
            //r.setPort("/dev/tty.Bluetooth-Incoming-Port");
            System.out.println(OSName);
        }
        else
        {
            r.setPort("COM3");
            System.out.println(OSName);
        }
		r.setVerbose(true); // Turn on debugging messages
		r.connect();
		r.attachServo(RXTXRobot.SERVO1, 9); //Connect the servos to the Arduino
		r.attachServo(RXTXRobot.SERVO2, 10);
		r.moveServo(RXTXRobot.SERVO1, 30); // Move Servo 1 to location 30
		r.moveServo(RXTXRobot.SERVO2, 170); // Move Servo 2 to location 170
		r.close();
	}
}
