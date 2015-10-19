import rxtxrobot.*;
import java.util.Scanner;

public class motorDiag{
	public static void main(String[] args){
    Scanner input = new Scanner(System.in);
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3"); // Set port to COM3, will need to be changed for diff comps
    for(int i = 0; i < 100; i ++){
    motorTest(input, r);
    }
  }

  static void motorTest(Scanner input, RXTXRobot r){
    int speed1 = 0, speed2 = 0;
    int tick1 = 0, tick2 = 0;
    System.out.println("You have selected Encoded Motor testing");
    System.out.print("Select Motor 1 Speed: ");
    speed1 = input.nextInt();
    input.nextLine();
    System.out.print("Select Motor 2 Speed: ");
    speed2 = input.nextInt();
    input.nextLine();
    System.out.print("Select Motor 1 Tick Count: ");
    tick1 = input.nextInt();
    input.nextLine();
    System.out.print("Select Motor 2 Tick Count: ");
    tick2 = input.nextInt();
    input.nextLine();

		r.connect();
    r.runEncodedMotor(RXTXRobot.MOTOR1, speed1, tick1, RXTXRobot.MOTOR2, -speed2, tick1); // Run both motors forward, motor 2 has nerfed speed
      r.close();
  }
}
