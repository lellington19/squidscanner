import rxtxrobot.*;
import java.util.Scanner;

public class SequenceFunctions
{
	public static RXTXRobot m = new ArduinoNano(); // Create RXTXRobot motor object
	public static RXTXRobot s = new ArduinoNano(); // Create RXTXRobot sensor object
	public static int m1High = 500;
	public static int m2High = -394;
	public static int m1Low = 250;
	public static int m2Low = -227;
	public static int m1Turn = 250;
	public static int m2Turn = 235;
	public static int turnTime = 1350;
	public static double lowV = 28.2;   //velocity in cm/s
	public static double highV = 36.5;  //velocity in cm/s


	public static void main(String[] args)
	{



		String option = "";
		Scanner input = new Scanner(System.in);
		m.setPort("COM3");
		m.connect();
		s.setPort("COM4");
		s.connect();
		m.attachMotor(RXTXRobot.MOTOR1,5);
		m.attachMotor(RXTXRobot.MOTOR2,6);
		m.attachMotor(RXTXRobot.MOTOR3,4);
		m.attachServo(RXTXRobot.SERVO1, 7);
		//turnRight();
		driveForward(500);
	/*
		option = menu(input); //opens menu for user
		while(!option.equals("5")){
				switch(option){
					case "1": //sequence for bottom left position  (if you walk out of Blanton, close to you on the right)
						Sequence1();
						break;
					case "2": //sequence for top left position
						Sequence2();
						break;
					case "3": //sequence for top right position
						Sequence3();
						break;
					case "4": //sequence for bottom right position
						Sequence4();
					default: //offers chance at redemtion if invalid input is detected
						System.out.println("Try again");
						option = menu(input);
				}
				option = menu(input);
		}
		System.out.println("Quitting..."); //end program
*/
		m.close();
		s.close();
	}
	static String menu(Scanner input){ //menu method, used to print options for diag
		String option = "";
		System.out.println("+----------------------------------+");
		System.out.println("What would you like to do?");
		System.out.println("1 - Position 1 Sequence");
		System.out.println("2 - Position 2 Sequence");
		System.out.println("3 - Position 3 Sequence");
		System.out.println("4 - Position 4 Sequence");
		System.out.println("5 - Quit");
		System.out.print("Selection: ");
		option = input.next();
		input.nextLine();
		return option;
	}

	public static void Sequence1(){
		driveForward(270);
	//  m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 6100); //drive 9 feet
		turnLeft();
	//  m.runMotor(RXTXRobot.MOTOR1, -250, RXTXRobot.MOTOR2, -215, 1930); //turn left
		driveForward(120);  //drive close to sandbox region
	//  m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 4000); //drive close to sandbox region
		enterSandbox();
	/*  int ping1 = s.getPing(6);
		while(ping1>15||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -200, 0);
			ping1 = s.getPing(6);
		}
		int t = (ping1 - 3)*(int)(1000/22.3);   //time = ping distance - 3 cm times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -200, t); //drive 3 cm before last ping reading
*/
		//Take conductivity reading

		turnLeft();
		//m.runMotor(RXTXRobot.MOTOR1, -250, RXTXRobot.MOTOR2, -215, 1930); //turn left
		driveForward(120);
	//	m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 2700); //drive 4 feet
		turnRight();
	//  m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, 215, 1930); //turn right
		approachObject(35);
	/*	ping1 = s.getPing(6);
		while(ping1>30||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -200, 0);  //drive until 20 cm from wall
			ping1 = s.getPing(6);
		}
	*/
		turnRight();
	//  m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, 215, 1300); //turn right
		driveForward(120);
	//	m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 4000); //drive 4 feet
		m.sleep(500);
		turnLeft();
	//  m.runMotor(RXTXRobot.MOTOR1, -250, RXTXRobot.MOTOR2, -215, 1300); //turn left
		driveForward(120);
	//  m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 2700); //drive 4 feet close to ramp
		m.sleep(500);
		climbRamp();
	/*  ping1 = s.getPing(6);
		while(ping1>60||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -200, 0);
			ping1 = s.getPing(6);
		}
		t = (ping1 + 150)*(int)(1000/44.6);   //time = ping distance + distance of ramp times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, t); //drive up ramp
		m.sleep(300);
		//raise boom
		//Take air readings
		m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
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
*/
		driveForward(300);
	//  m.runMotor(RXTXRobot.MOTOR1, 500, RXTXRobot.MOTOR2, -397, 8000); //drive 10 feet
		turnLeft();
	//  m.runMotor(RXTXRobot.MOTOR1, -250, RXTXRobot.MOTOR2, -215, 1500); //turn left
		approachObject(35);    //approach sandbox
	/*	while(ping1>40||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -200, 0);
			ping1 = s.getPing(6);
		}
		m.runMotor(RXTXRobot.MOTOR1, 10, RXTXRobot.MOTOR2, -10, 5); //stop motors
*/

		m.sleep(1000);    //run solar charging sequence
	}
	public static void Sequence2()
	{
		driveForward(300);      //drive until even with ramp
		turnRight();
		climbRamp();
		driveForward(250);      //drive toward sandbox region
		enterSandbox();
		turnLeft();
		driveForward(200);      //drive toward center wall
		approachObject(35);     //approach center wall
		turnRight();						//turn to void wall
		driveForward(120);
		turnLeft();
		driveForward(180);      //drive past wall
		turnLeft();
		driveForward(720);      //drive until even with solar charging region
		turnRight();
		approachObject(40);     //approach solar charging region
		m.sleep(1000);
	}
	public static void Sequence3()
	{
		driveForward(350);
		approachObject(35);
		turnLeft();
		driveForward(250);
		turnRight();
		climbRamp();
		driveForward(80);
		turnLeft();
		driveForward(200);
		approachObject(35);
		turnLeft();
		driveForward(140);
		turnRight();
		driveForward(250);
		enterSandbox();
		turnLeft();
		driveForward(720);
		turnRight();
		approachObject(35);  //approach solar charging region
	}
	public static void Sequence4()
	{
		driveForward(300);
		approachObject(35);
		turnRight();
		driveForward(360);
		turnLeft();
		driveForward(150);
		enterSandbox();
		turnLeft();
		driveForward(300);
		turnRight();
		approachObject(35);
		turnRight();
		driveForward(450);
		approachObject(35);
		turnRight();
		driveForward(240);
		turnLeft();
		driveForward(100);
		climbRamp();
	}
	/*
	public static void driveForward(int distance)
	{
		int t = (distance)*(int)(1000/highV);   //time = ping distance - 3 cm times 1/velocity
    m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, t); //drive 3 cm before last ping reading
	}
	*/
	public static void driveForward(int distance)
	{
		int d = s.getPing(6);
		System.out.println("Ping 1: "+d);
		int t = (distance)*(int)(1000/highV);   //time = distance times 1/velocity
		long tStart = System.currentTimeMillis();
		long tEnd;
		long tDelta = 0;
		int d2 = -1;
		boolean goodRead = false;   //fix it turn to false
		while((d>40||d==-1) &&(int)tDelta<t)
		{
		  m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, 0);
			tEnd = System.currentTimeMillis();
			tDelta = tEnd - tStart;
			System.out.println("Time elapsed: " + tDelta);
			d2 = d;
			d = s.getPing(6);
			System.out.println("Ping 1: "+d);
			/*if(d<=60&&d>15&&d2<=40&&d2>15)
				goodRead = true;
			else
				goodRead = false;
				*/
	  }
		m.runMotor(RXTXRobot.MOTOR1, 5, RXTXRobot. MOTOR2, 5, 5);   //stop motors
		if(d<=40&&(int)tDelta<t)
		{
			avoidObject(t-(int)tDelta);
		}
	}

	public static void driveTimeLow(int time)
	{
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, time);
	}
	public static void driveTimeHigh(int time)
	{
		long tStart = System.currentTimeMillis();
		long tEnd;
		long tDelta = 0;
		int d = s.getPing(7);
		System.out.println("Ping 1: "+d);
		while(d>40&&(int)tDelta<time)
		{
			m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, 0);
			tEnd = System.currentTimeMillis();
			tDelta = tEnd - tStart;
			d = s.getPing(6);
				System.out.println("Ping 1: "+d);
		}
		m.runMotor(RXTXRobot.MOTOR1, 5, RXTXRobot. MOTOR2, 5, 5);   //stop motors
		if(d<=40&&(int)tDelta<time)
		{
			avoidObject((int)tDelta);
		}
	}
	public static void avoidObject(int timeLeft)
	{
		System.out.println("Time left in drive: " + timeLeft);
		int dObject = s.getPing(6);         //likely won't need b/c only testing for close obstacles
		turnRight();
		m.sleep(500);
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 1500);
		int ping2 = s.getPing(7);
		System.out.println("Ping 2: "+ping2);
		long tStart = System.currentTimeMillis();
		long tEnd;
		long tDelta = 0;
		while(ping2<(dObject+15)&&ping2>(dObject-25))
		{
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			ping2 = s.getPing(7);
			System.out.println("Ping 2: "+ping2);
			tEnd = System.currentTimeMillis();
			tDelta = tEnd - tStart;
			System.out.println("Time elapsed: "+ tDelta);
		}
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 500);
	//	driveForward(90);
		turnLeft();
		int time1 = 3500;
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, time1);
		ping2 = s.getPing(7);
		System.out.println("Ping 2: "+ping2);
		long tStart2 = System.currentTimeMillis();
		long tEnd2;
		long tDelta2 = 0;
	/*	while(ping2<50&&ping2>1)
		{
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			ping2 = s.getPing(7);
			System.out.println("Ping 2: "+ping2);
			tEnd2 = System.currentTimeMillis();
			tDelta2 = tEnd2 - tStart2;
			System.out.println("Time elapsed: "+ tDelta2);
		}
	*/
	int time2 = 500;
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 500);
		m.sleep(500);
		turnLeft();
		driveTimeLow(500+1500+(int)tDelta);
		turnRight();              //return to original orientation
		int tDrive = timeLeft - (time1 + time2 + (int)tDelta2);
		System.out.println("Time to drive: " + tDrive);
		int dDrive = (int)(tDrive*highV)/1000;
		driveForward(dDrive);    //continue path

	}
	public static void turnRight()
	{
		m.sleep(1000);
		System.out.println("Turning Right");
		m.runMotor(RXTXRobot.MOTOR1, m1Turn, RXTXRobot.MOTOR2, m2Turn, turnTime);
	}
	public static void turnLeft()
	{
		m.sleep(1000);
		System.out.println("Turning Left");
		m.runMotor(RXTXRobot.MOTOR1, m1Turn*-1, RXTXRobot.MOTOR2, m2Turn*-1, turnTime);
	}
	public static void approachObject(int distance)
	{
		System.out.println("Approaching Object to "+ distance);
		//drive until distance from wall
		int dTemp = distance;  //dTemp is when robot stops using ping sensor
		if(dTemp<20)
			dTemp = 21;
		int ping1 = s.getPing(6);
		while(ping1>dTemp||ping1==-1)
		{
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			ping1 = s.getPing(6);
		}
		if(dTemp==21)
		{
		int t = (ping1 - distance)*(int)(1000/lowV);   //time = last ping reading - distance times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, t); //drives until distance away from object
	  }
		m.runMotor(RXTXRobot.MOTOR1, 100, RXTXRobot.MOTOR2, 100, 5); //stop motors
	}
	public static void enterSandbox()
	{
		System.out.println("Entering Sandbox");
		approachObject(3);
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		System.out.println("Getting Conductivity Readings: ");
		double code = 0;
		double ans = 0;
		code = s.getConductivity();
		ans = -.1328*code+133.38;
		System.out.println("The %H20 is: " + ans+"%");
		m.moveServo(RXTXRobot.SERVO1, 90);
		m.sleep(750);
		m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low, 1000); //reverse from sandbox for 1 s
		m.sleep(200);
	}
	public static void climbRamp()
	{
		System.out.println("Climbing Ramp");
		approachObject(60);
		driveForward(60+150); //drive distance of 60 + distance of ramp

		m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
		m.sleep(500);
		System.out.println("Getting Air Readings: ");
		s.refreshAnalogPins();
		int code1 = s.getAnalogPin(0).getValue();
		int code2 = s.getAnalogPin(1).getValue();
		double temp1 = code1 * -.1305 + 145.45;
		double temp2 = code2 * -.1378 + 151.98;
		double wind = .4534*(temp1-temp2)-.37;
		System.out.println("Temp 1: " + temp1);
		System.out.println("Temp 2: " + temp2);
		System.out.println("Wind speed: " + wind);
	m.runMotor(RXTXRobot.MOTOR3,500, 4500); //lower boom
	}


}
