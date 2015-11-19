import rxtxrobot.*;
import java.util.Scanner;

public class SequenceFunctions
{

	public static int m1High = 500;
	public static int m2High = -394;
	public static int m1Low = 250;
	public static int m2Low = -227;
	public static int m1Turn = 250;
	public static int m2Turn = 235;
	public static int turnRTime = 1350;
	public static int turnLTime = 1415;
	public static double lowV = 28.2;   //velocity in cm/s
	public static double highV = 36.5;  //velocity in cm/s


	public static void main(String[] args)
	{
		RXTXRobot m = new ArduinoNano(); // Create RXTXRobot motor object
		RXTXRobot s = new ArduinoNano(); // Create RXTXRobot sensor object

		m.setPort("COM3");
		m.connect();
		s.setPort("COM4");
		s.connect();
		m.refreshAnalogPins();
		s.refreshAnalogPins();
		m.attachMotor(RXTXRobot.MOTOR1,5);
		m.attachMotor(RXTXRobot.MOTOR2,6);
		m.attachMotor(RXTXRobot.MOTOR3,4);
		m.attachServo(RXTXRobot.SERVO1, 7);
		String option = "";
		Scanner input = new Scanner(System.in);
		//driveHighD(100,m);
		option = menu(input); //opens menu for user
		while(!option.equals("9")){
				switch(option){
					case "1": //sequence for bottom left position  (if you walk out of Blanton, close to you on the right)
						Sequence1(m,s);
						break;
					case "2": //sequence for top left position
						Sequence2(m,s);
						break;
					case "3": //sequence for top right position
						Sequence3(m,s);
						break;
					case "4": //sequence for bottom right position
						Sequence4(m,s);
					case "5": //sequence for bottom left position  (if you walk out of Blanton, close to you on the right)
						Sequence1Hard(m,s);
						break;
					case "6": //sequence for top left position
						Sequence2Hard(m,s);
						break;
					case "7": //sequence for top right position
					  Sequence3Hard(m,s);
						break;
					case "8": //sequence for bottom right position
						Sequence4Hard(m,s);
						break;
					default: //offers chance at redemtion if invalid input is detected
						System.out.println("Try again");
						option = menu(input);
				}
				option = menu(input);
		}
		System.out.println("Quitting..."); //end program

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
		System.out.println("5 - Position 1 Hard Sequence");
		System.out.println("6 - Position 2 Hard Sequence");
		System.out.println("7 - Position 3 Hard Sequence");
		System.out.println("8 - Position 4 Hard Sequence");
		System.out.println("9 - Quit");
		System.out.print("Selection: ");
		option = input.next();
		input.nextLine();
		return option;
	}
	public static void Sequence1(RXTXRobot m, RXTXRobot s)
	{
		driveForward(270,m,s);
		turnLeft(m);
		driveForward(120,m,s);  //drive close to sandbox region
		enterSandbox(m,s);
		turnLeft(m);
		driveHighD(120,m);
		turnRight(m);

		approachObject(35,m,s);
		turnRight(m);
		driveForward(60,m,s);
		turnLeft(m);
		driveForward(120,m,s);
		climbRamp(m,s);
		driveForward(300,m,s);
		turnLeft(m);
		approachObject(35,m,s);
		m.sleep(1000);    //run solar charging sequence
	}
	public static void Sequence1Hard(RXTXRobot m, RXTXRobot s){
			driveHighD(230,m);
			turnLeft(m);
			driveHighD(230,m);  //drive close to sandbox region
			//enterSandbox(m,s);
			m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
			System.out.println("Getting Conductivity Readings: ");
			double code = 0;
			double ans = 0;
			code = s.getConductivity();
			ans = -.1328*code+133.38;
			System.out.println("The %H20 is: " + ans+"%");
			m.moveServo(RXTXRobot.SERVO1, 90);
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException ex) {
				System.out.println("Thread Interrupt");
				Thread.currentThread().interrupt();
			}
			m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low*-1, 1000); //reverse from sandbox for 1 s
			try {
				Thread.sleep(200);
			}
			catch(InterruptedException ex) {
				System.out.println("Thread Interrupt");
				Thread.currentThread().interrupt();
			}

			turnLeft(m);
			driveHighD(120,m);
			turnRight(m);
			driveHighD(150,m);
			turnRight(m);
			driveHighD(120,m);
			turnLeft(m);
			driveHighD(120,m);
			driveHighD(200,m);
			m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
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
		m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom
		//	climbRamp(m,s);
			driveHighD(300,m);
			turnLeft(m);
			driveHighD(180,m);
		//	approachObject(35,m,s);    //approach sandbox
			m.sleep(1000);    //run solar charging sequence
	}
	public static void Sequence2(RXTXRobot m, RXTXRobot s)
		{
			driveForward(210,m,s);
			turnRight(m);
			climbRamp(m,s);
			driveForward(400,m,s);
			enterSandbox(m,s);
			turnLeft(m);
			driveForward(250,m,s);
			turnLeft(m);
			driveForward(660,m,s);
			turnRight(m);
			driveForward(300,m,s);
		}
	public static void Sequence2Hard(RXTXRobot m, RXTXRobot s)
	{
		driveHighD(200,m);      //drive until even with ramp
		turnRight(m);
		//climbRamp(m,s);
		driveHighD(300,m);
			m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException ex) {
				System.out.println("Thread Interrupt");
				Thread.currentThread().interrupt();
			}
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
		m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom

		driveHighD(380,m);      //drive toward sandbox region

		//enterSandbox(m,s);
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		System.out.println("Getting Conductivity Readings: ");
		double code = 0;
		double ans = 0;
		code = s.getConductivity();
		ans = -.1328*code+133.38;
		System.out.println("The %H20 is: " + ans+"%");
		m.moveServo(RXTXRobot.SERVO1, 90);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			System.out.println("Thread Interrupt");
			Thread.currentThread().interrupt();
		}
		m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low*-1, 1000); //reverse from sandbox for 1 s
		try {
			Thread.sleep(200);
		}
		catch(InterruptedException ex) {
			System.out.println("Thread Interrupt");
			Thread.currentThread().interrupt();
		}

		turnLeft(m);
		driveHighD(350,m);      //drive toward center wall
		turnLeft(m);     //approach center wall
		driveHighD(420,m);
		turnRight(m);
		driveHighD(250,m);      //drive past wall
		m.sleep(1000);
	}

	public static void Sequence3(RXTXRobot m, RXTXRobot s)
	{
		driveForward(350,m,s);
		approachObject(35,m,s);
		turnLeft(m);
		driveForward(200,m,s);
		turnRight(m);
		climbRamp(m,s);
		driveForward(80,m,s);
		turnLeft(m);
		driveForward(200,m,s);
		approachObject(35,m,s);
		turnLeft(m);
		driveForward(140,m,s);
		turnRight(m);
		driveForward(250,m,s);
		enterSandbox(m,s);
		turnLeft(m);
		driveForward(720,m,s);
		turnRight(m);
		approachObject(35,m,s);  //approach solar charging region
	}
	public static void Sequence3Hard(RXTXRobot m, RXTXRobot s)
	{
		driveHighD(420,m);
		turnLeft(m);
		driveHighD(200,m);
		turnRight(m);
		driveHighD(250,m);
		m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			System.out.println("Thread Interrupt");
			Thread.currentThread().interrupt();
		}
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
	m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom
		driveHighD(80,m);
		turnLeft(m);
		driveHighD(250,m);
		turnLeft(m);
		driveHighD(140,m);
		turnRight(m);
		driveHighD(250,m);
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		System.out.println("Getting Conductivity Readings: ");
		double code = 0;
		double ans = 0;
		code = s.getConductivity();
		ans = -.1328*code+133.38;
		System.out.println("The %H20 is: " + ans+"%");
		m.moveServo(RXTXRobot.SERVO1, 90);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			System.out.println("Thread Interrupt");
			Thread.currentThread().interrupt();
		}
		m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low*-1, 1000); //reverse from sandbox for 1 s
		turnLeft(m);
		driveHighD(720,m);
		turnRight(m);
		driveHighD(300,m);  //approach solar charging region
	}
	public static void Sequence4(RXTXRobot m, RXTXRobot s)
	{
		driveForward(300,m,s);
		approachObject(35,m,s);
		turnRight(m);
		driveForward(360,m,s);
		turnLeft(m);
		driveForward(150,m,s);
		enterSandbox(m,s);
		turnLeft(m);
		driveForward(300,m,s);
		turnRight(m);
		approachObject(35,m,s);
		turnRight(m);
		driveForward(450,m,s);
		approachObject(35,m,s);
		turnRight(m);
		driveForward(240,m,s);
		turnLeft(m);
		driveForward(100,m,s);
		climbRamp(m,s);
	}
	public static void Sequence4Hard(RXTXRobot m, RXTXRobot s)
	{
		driveHighD(420,m);
		turnRight(m);
		driveHighD(360,m);
		turnLeft(m);
		driveHighD(150,m);
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		System.out.println("Getting Conductivity Readings: ");
		double code = 0;
		double ans = 0;
		code = s.getConductivity();
		ans = -.1328*code+133.38;
		System.out.println("The %H20 is: " + ans+"%");
		m.moveServo(RXTXRobot.SERVO1, 90);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low*-1, 1000); //reverse from sandbox for 1 s
		turnLeft(m);
		driveForward(300,m,s);
		turnRight(m);
		driveForward(300,m,s);
		try {
			Thread.sleep(10000);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		turnRight(m);
		driveHighD(480,m);
		turnRight(m);
		driveHighD(240,m);
		turnLeft(m);
		driveHighD(350,m);
		m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
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
  	m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom
	}
	public static int driveHighD(int distance, RXTXRobot m)
	{
		int t = (distance)*(int)(1000/highV);   //time = ping distance - 3 cm times 1/velocity
    m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, t); //drive 3 cm before last ping reading
		System.out.println("Dank memes");
		return 1;
	}
	public static void driveLowD(int distance, RXTXRobot m)
	{
		int t = (distance)*(int)(1000/lowV);   //time = ping distance - 3 cm times 1/velocity
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, t); //drive 3 cm before last ping reading
	}
	public static void driveForward(int distance, RXTXRobot m, RXTXRobot s)
	{
		int d = s.getPing(6);
		int ping2 = s.getPing(7);
		System.out.println("Ping 1: "+d);
		int t = (distance)*(int)(1000/highV);   //time = distance times 1/velocity
		long tStart = System.currentTimeMillis();
		long tEnd;
		long tDelta = 0;
		int d2 = -1;
		boolean goodRead = false;   //fix it turn to false
		while((d>50||d==-1)/*&&(ping2>50||ping2==-1)*/&&(int)tDelta<t)
		{
		  m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			tEnd = System.currentTimeMillis();
			tDelta = tEnd - tStart;
			System.out.println("Time elapsed: " + tDelta);
			d2 = d;
			d = s.getPing(6);
		//	ping2 = s.getPing(7);
			System.out.println("Ping 1: "+d);
		//	System.out.println("Ping 2: "+ping2);
			/*if(d<=60&&d>15&&d2<=40&&d2>15)
				goodRead = true;
			else
				goodRead = false;
			*/
		}
		m.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot. MOTOR2, 0, 500);   //stop motors
		if((d<=40||ping2<=40)&&(int)tDelta<t)
		{
			avoidObject(t-(int)tDelta,m,s);
		}

	}

	public static void driveTimeLow(int time, RXTXRobot m)
	{
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, time);
	}
	public static void driveTimeHigh(int time, RXTXRobot m)
	{
		m.runMotor(RXTXRobot.MOTOR1, m1High, RXTXRobot.MOTOR2, m2High, time);
	}
	public static void avoidObject(int timeLeft, RXTXRobot m, RXTXRobot s)
	{
		System.out.println("Time left in drive: " + timeLeft);
		int dObject = s.getPing(7);         //likely won't need b/c only testing for close obstacles
		turnRight(m);
		try {
			Thread.sleep(1000);
			System.out.println("Sleeping for 1 s.");
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		int timeA = 2500;
		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, timeA);
		int ping2 = s.getPing(7);
		System.out.println("Ping 2: "+ping2);
		long tStart = System.currentTimeMillis();
		long tEnd;
		long tDelta = 0;
	/*	while(ping2<(dObject+15)&&ping2>(dObject-25))
		{
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);
			ping2 = s.getPing(7);
			System.out.println("Ping 2: "+ping2);
			tEnd = System.currentTimeMillis();
			tDelta = tEnd - tStart;
			System.out.println("Time elapsed: "+ tDelta);
		}
		*/
	//	int timeB = 500;
	//	m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, timeB);
	//	driveForward(90,m,s);
		turnLeft(m);
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
//	int time2 = 500;
//		m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 500);

		turnLeft(m);
		driveTimeLow(timeA+(int)tDelta,m);
		turnRight(m);              //return to original orientation
		int tDrive = timeLeft - (time1 + (int)tDelta2);
		System.out.println("Time to drive: " + tDrive);
		int dDrive = (int)(tDrive*highV)/1000;
		driveForward(dDrive,m,s);    //continue path

	}
	public static void turnRight(RXTXRobot m)
	{
		try {
			System.out.println("Sleeping for 1 s.");
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		System.out.println("Turning Right");
		m.runMotor(RXTXRobot.MOTOR1, m1Turn, RXTXRobot.MOTOR2, m2Turn, turnRTime);
	}
	public static void turnLeft(RXTXRobot m)
	{
		try {
			System.out.println("Sleeping for 1 s");
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		System.out.println("Turning Left");
		m.runMotor(RXTXRobot.MOTOR1, m1Turn*-1, RXTXRobot.MOTOR2, m2Turn*-1, turnLTime);
	}
	public static void approachObject(int distance, RXTXRobot m, RXTXRobot s)
	{
		System.out.println("Approaching Object to "+ distance);
		//drive until distance from wall
		int dTemp = distance;  //dTemp is when robot stops using ping sensor
		if(dTemp<20)
			dTemp = 21;
		int ping1 = s.getPing(7);
		while(ping1>dTemp||ping1==-1)
		{
			ping1 = s.getPing(7);
			System.out.println("Ping 1 Read: "+ ping1);
			m.runMotor(RXTXRobot.MOTOR1, m1Low, RXTXRobot.MOTOR2, m2Low, 0);

		}
		if(dTemp==21)
		{
			System.out.println("Distance to travel: " + (ping1 - distance));
		int t = (ping1 - distance)*(int)(1000/lowV);   //time = last ping reading - distance times 1/velocity
		driveTimeLow(t,m); //drives until distance away from object
	  }
		else
		{
		m.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 500); //stop motors
		turnt(m,s);
		}
	}
	public static void enterSandbox(RXTXRobot m, RXTXRobot s)
	{
		System.out.println("Entering Sandbox");
		approachObject(7,m,s);
		m.moveServo(RXTXRobot.SERVO1,0);   //lower servo
		System.out.println("Getting Conductivity Readings: ");
		double code = 0;
		double ans = 0;
		code = s.getConductivity();
		ans = -.1328*code+133.38;
		System.out.println("The %H20 is: " + ans+"%");
		m.moveServo(RXTXRobot.SERVO1, 90);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		m.runMotor(RXTXRobot.MOTOR1, m1Low*-1, RXTXRobot.MOTOR2, m2Low*-1, 1000); //reverse from sandbox for 1 s
		try {
			Thread.sleep(200);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	public static void climbRamp(RXTXRobot m, RXTXRobot s)
	{
		System.out.println("Climbing Ramp");
		approachObject(60,m,s);
		turnt(m,s);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		int t = (60+150)*(int)(1000/highV);
		driveTimeHigh(t,m); //drive distance of 60 + distance of ramp

		m.runMotor(RXTXRobot.MOTOR3,-500, 6000); //raise boom
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
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
	m.runMotor(RXTXRobot.MOTOR3,500, 6000); //lower boom
	}

public static void turnt(RXTXRobot m, RXTXRobot s){
	    System.out.println("Aligning...");
    for(int i = 0; i < 10; i ++){
	      s.refreshAnalogPins();
	      int ping1 = s.getPing(6);
	      int ping2 = s.getPing(7);
	      System.out.println(ping1 + " " + ping2);
	      if(ping1 < ping2){
	        m.runEncodedMotor(0, 200, 5, 1, 200, 5);

	      }
	      else if(ping1 > ping2 ){
	        m.runEncodedMotor(0, -200, 10, 1, -200, 1);
	      }
	      else if(ping1 == ping2){
	        System.out.println("Nah Fam");
					break;
	      }
	    }
	    System.out.println("We Done");
}
}
