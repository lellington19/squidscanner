import rxtxrobot.*;
import java.util.Scanner;

public class Diag2{
	public static void main(String[] args){
    String option = "";
    Scanner input = new Scanner(System.in);
		RXTXRobot m = new ArduinoNano(); // Create RXTXRobot object
		RXTXRobot s = new ArduinoNano();
		m.setPort("COM3");
		s.setPort("COM4");
		s.connect();
		m.connect();
		m.attachMotor(RXTXRobot.MOTOR1,5);
		m.attachMotor(RXTXRobot.MOTOR2,6);
		m.attachMotor(RXTXRobot.MOTOR3,4);
		m.attachServo(RXTXRobot.SERVO1, 7);
		s.attachGPS();
 // Set port to COM4, will need to be changed for diff comps
		option = menu(input); //opens menu for user
    while(!option.equals("4")){
        switch(option){
          case "1": //runs sensor diagnostic, reads out voltages and ADC codes at analogs
            Start(s, m);
            break;
          case "2": //runs motor indefinitely untill bump sensor is tripped
            Water(s, m);
            break;
          case "3": //runs motors for specified tick count
            Air(s, m);
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
    System.out.println("1 - Starting Procedure");
    System.out.println("2 - Water Conductivity Testing");
    System.out.println("3 - Elevated Air Testing");
    System.out.println("4 - Quit");
    System.out.print("Selection: ");
    option = input.next();
    input.nextLine();
    return option;
  }
	static void Start(RXTXRobot s, RXTXRobot m){ //runs motor indefinitley until bump is detected
		System.out.println("You have selected start.");
		double[] coordinates = s.getGPSCoordinates();

System.out.println("Degrees latitude: " + coordinates[0]);
System.out.println("Minutes latitude: " + coordinates[1]);
System.out.println("Degrees longitude: " + coordinates[2]);
System.out.println("Minutes longitude: " + coordinates[3]);

s.sleep(1000);

		m.runMotor(RXTXRobot.MOTOR1,-200,RXTXRobot.MOTOR2,188,28000);
		m.runMotor(RXTXRobot.MOTOR1,-200,RXTXRobot.MOTOR2,188,8000);
		m.sleep(10000);

	}
	static void Water(RXTXRobot s, RXTXRobot m){
		System.out.println("You have selected Water Conductivity Reading.");



		int d = s.getPing(7);
		s.sleep(400);
*while(d>30||d==-1)
{
  System.out.println(d);
	m.runMotor(RXTXRobot.MOTOR1,-200,RXTXRobot.MOTOR2,215,0);
	d = s.getPing(7);
	s.sleep(400);
}
System.out.println(d);
while(d>9||d==-1)
{
  System.out.println(d);
	m.runMotor(RXTXRobot.MOTOR1,-100,RXTXRobot.MOTOR2,104,0);
	d = s.getPing(7);
	s.sleep(400);
}
m.runMotor(RXTXRobot.MOTOR1,-200,RXTXRobot.MOTOR2,208,5);

m.sleep(500);
m.moveServo(RXTXRobot.SERVO1,0);
m.sleep(100);

double distance = .6;
double area = 1;
double code = 0;
double resist = 0;
double ans = 0;
code = s.getConductivity();
ans = ((1044.6 - code)/23.78);
System.out.println("The conductivity is: " + code);
System.out.println("The moisture content is: " + ans);
m.sleep(100);
m.moveServo(RXTXRobot.SERVO1,90);
m.sleep(500);
//m.runMotor(RXTXRobot.MOTOR1,200,RXTXRobot.MOTOR2,-208,1000);

}
	static void Air(RXTXRobot s, RXTXRobot m){
		System.out.println("You have selected Elevated Air Reading.");

		m.runMotor(RXTXRobot.MOTOR1,-300,RXTXRobot.MOTOR2,330,6000);


    m.sleep(200);

		//Raise antenna
		m.runMotor(RXTXRobot.MOTOR3,-500, 5500);
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
	m.runMotor(RXTXRobot.MOTOR3,500, 6000);


	}

}
