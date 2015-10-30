import rxtxrobot.*;
import java.util.Scanner;

public class Diag{
	public static void main(String[] args){
    String option = "";
    Scanner input = new Scanner(System.in);
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3"); // Set port to COM3, will need to be changed for diff comps
    option = menu(input); //opens menu for users
    while(!option.equals("9")){
        switch(option){
          case "1": //runs sensor diagnostic, reads out voltages and ADC codes at analogs
            Sensor(input, r);
            break;
          case "2": //runs motor indefinitely untill bump sensor is tripped
            Motor(input, r);
            break;
          case "3": //runs motors for specified tick count
            EncodedMotor(input, r);
            break;
          case "4"://runs Servo test, give angle between 0 and 180, starting pos is 1=90
            Servo(input, r);
            break;
          case "5": //gives temperature reading from thermistor probe
            Temp(input, r);
            break;
          case "6": //Ping sensor
            Ping(input, r);
            break;
					case "7": //conductivity reading
						Conductivity(input, r);
						break;
					case "8": //gps reading
						gps(input, r);
						break;
          default: //offers chance at redemtion if invalid input is detected
            System.out.println("Try again");
            option = menu(input);
        }
        option = menu(input);
    }
    System.out.println("Quitting..."); //end program
	}

  static String menu(Scanner input){ //menu method, used to print options for diag
    String option = "";
    System.out.println("+----------------------------------+");
    System.out.println("What would you like to do?");
    System.out.println("1 - Sensor Testing");
    System.out.println("2 - Motor Testing");
    System.out.println("3 - Encoded Motor Testing");
    System.out.println("4 - Servo Testing");
    System.out.println("5 - Thermistor Reading");
    System.out.println("6 - Ping Sensor Readings");
		System.out.println("7 - Conductivity Reading");
		System.out.println("8 - GPS Readout");
    System.out.println("9 - Quit");
    System.out.print("Selection: ");
    option = input.next();
    input.nextLine();
    return option;
  }

  static void Sensor(Scanner input, RXTXRobot r){ //prints ADC values from ARD
    System.out.println("Sensor Testing Selected");
    int count = 0;
    System.out.print("Enter loop count: ");
    count = input.nextInt();
    input.nextLine();
    r.connect();
    AnalogPin temp = r.getAnalogPin(0);
    for(int c = 0; c < count; c++){ //allows refresh of pin info
      r.refreshAnalogPins();
      System.out.println("------------------------------------------------");
      for (int x=0; x < 7; ++x){
        temp = r.getAnalogPin(x);
        System.out.println("Sensor " + x + " has value: " + temp.getValue());
        System.out.println("The voltage read at was: " + (temp.getValue() * (5.0/1023)));
      }
      if(r.getAnalogPin(0).getValue() < 0){ System.out.println("STAHP!"); r.close(); return; }
      try { //sleep to add pause
        Thread.sleep(2000);
      }
      catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
    r.close();
  }

  static void Motor(Scanner input, RXTXRobot r){ //runs motor indefinitley until bump is detected
    System.out.println("You have selected motor bump test");
    System.out.print("Select Speed: ");
    int speed = input.nextInt();
    double x = 0;
    r.connect();
    while(x < 4.5){
      r.refreshAnalogPins();
      AnalogPin temp = r.getAnalogPin(1);
      r.runMotor(RXTXRobot.MOTOR1, (speed), RXTXRobot.MOTOR2, (-speed + 22), 0); //runs both motors, motor 2 has nerfed speed
      x = (temp.getValue() * (5.0/1023));
    }
    r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors
    r.close();
  }
  static void EncodedMotor(Scanner input, RXTXRobot r){ //Runs motor untill tick count met
    int speed = 0;
    int tick = 0;
    System.out.println("You have selected Encoded Motor testing");
    System.out.print("Select Motor Speed: ");
    speed = input.nextInt();
    input.nextLine();
    System.out.print("Select Tick Count: ");
    tick = input.nextInt();
    input.nextLine();
		r.connect();
		r.runEncodedMotor(RXTXRobot.MOTOR1, (speed), tick, RXTXRobot.MOTOR2, (-speed + 19), tick); // Run both motors forward, motor 2 has nerfed speed
		r.close();
  }

  static void Servo(Scanner input, RXTXRobot r){ //runs servo
    System.out.println("You have Selected Servo Testing");
    int angle = 0;
    System.out.print ("Enter Angle: ");
    angle = input.nextInt();
    input.nextLine();
    r.connect();
		r.attachServo(RXTXRobot.SERVO1, 9); //connect the servos to the Arduino
		System.out.println("Moving...");
		r.moveServo(RXTXRobot.SERVO1, angle);
		r.sleep(1000);
    r.close();
  }

  static void Temp(Scanner input, RXTXRobot r){ //gets temp
    System.out.println("Your have selected Thermister Reading...");
    int count = 0;
    double sum, voltage, temp;
    double slope = -8.375174981;
    double intercept = 768.8072281;
    System.out.print("Reading");
    sum = 0;
    r.connect();
    while(count <= 10){
      r.refreshAnalogPins();
      sum = sum + r.getAnalogPin(0).getValue();
      try {
        Thread.sleep(100);
      }
      catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
      System.out.print(" .");
      count = count + 1;
    }
    System.out.println("Average is: " + (sum/count));
    temp = ((sum/count) - intercept) / slope;
    System.out.println("The temperature is: " + temp);
    r.close();
  }
  static void Ping(Scanner input, RXTXRobot r){ //returns specified number of pings
    System.out.println("You have selected Ping Reading");
    int count = 0;
    System.out.print("Enter ping count: ");
    count = input.nextInt();
    input.nextLine();
    r.connect();
		for (int x=0; x < count; ++x)
		{
			System.out.println("Response: " + r.getPing(13) + " cm");
			r.sleep(300);
		}
		r.close();
  }
	static void Conductivity(Scanner input, RXTXRobot r){
		System.out.println("You have selected Conductivity Reading");
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
		r.close();
	}

	static void gps(Scanner input, RXTXRobot r){
		r.connect();
		r.attachGPS();

		double[] coordinates = r.getGPSCoordinates();

		System.out.println("Degrees latitude: " + coordinates[0]);
		System.out.println("Minutes latitude: " + coordinates[1]);
		System.out.println("Degrees longitude: " + coordinates[2]);
		System.out.println("Minutes longitude: " + coordinates[3]);

		r.close();
	}
}
