import rxtxrobot.*;

public class SequenceFunctions
{
	int m1High = 500;
	int m2High = -397;
	int m1Low = 250;
	int m2Low = -200;
	int m1Turn = 250;
	int m2Turn = 215;
	int turnTime = 1930;

	public void driveForward(int distance)
	{
		int t = (distance)(1000/44.6);   //time = distance times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, t); //drive 3 cm before last ping reading
	}
	public void turnRight()
	{
		m.runMotor(RXTXRobot.MOTOR1, m1Turn, RXTXRobot.MOTOR2, m2Turn, turnTime);
	}
	public void turnLeft()
	{
		m.runMotor(RXTXRobot.MOTOR1, m1Turn*-1, RXTXRobot.MOTOR2, m2Turn*-1, turnTime);
	}
	public void approachObject(int distance)
	{
		//drive until ping reads below distance
		int ping1 = s.getPing(6);
		while(ping1>distance||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			ping1 = s.getPing(6);
		}
		m.runMotor(RXTXRobot.MOTOR1, 100, RXTXRobot.MOTOR2, 100, 5); //stop motors
	}
	public void enterSandbox()
	{
		approachObject(15);
		int d = s.getPing(6);
		int t = (d - 3)(1000/22.3);   //time = ping distance - 3 cm times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, t); //drive 3 cm before last ping reading
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		double distance = .6;
		double area = 1;
		double code = 0;
		double resist = 0;
		double ans = 0;
		r.connect();
		code = r.getConductivity();
		resist = (-2000 * code)/(code -1);
		ans = distance/(area * resist);
		System.out.println("The conductivity is: " + code);
		m.moveServo(RXTXRobot.SERVO1, 90);
	}
	public void climbRamp()
	{
		int ping1 = s.getPing(6);
    while(ping1>60||ping1==-1)
    {
      System.out.println("Ping 1 Read: "+ ping1);
      m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
      ping1 = s.getPing(6);
    }
		driveForward(ping1+150); //drive distance of last ping reading + 100 cm

		m.runMotor(RXTXRobot.MOTOR3,-500, 5500); //raise boom
		m.sleep(500);
		int count = 0;
		double sum, sum2, voltage, temp, temp2;
		double slope = -8.375174981;
		double intercept = 768.8072281;
		System.out.print("Reading");
		sum = 0.0;
		sum2 = 0.0;
		while(count <= 10){
			s.refreshAnalogPins();
			sum = sum + s.getAnalogPin(0).getValue();
			sum2 = sum2 + s.getAnalogPin(1).getValue();
			try {
				Thread.sleep(100);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out.print(" .");
			count = count + 1;
		}
		System.out.println("\nAverage of first thermistor is: " + (sum/count));
		temp = ((sum/count) - intercept) / slope;

		System.out.println("The temperature of the first thermistor is: " + temp);

		System.out.println("Average of second thermistor is: " + (sum2/count));
		temp2 = ((sum2/count) - intercept) / slope;

		System.out.println("The temperature of the second thermistor is: " + temp2);
	m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom
	}
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3"); // Set port to COM2
		r.connect();
		r.attachMotor(RXTXRobot.MOTOR1,5);
		r.attachMotor(RXTXRobot.MOTOR2,6);
		r.close();
	}
}
