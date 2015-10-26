//Created for sprint

import rxtxrobot.*;

import java.util.Scanner;

public class Diag{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("COM3");
		System.out.println("Welcome to Pathfinding Attempt 1");
		System.out.println("Would you like to begin? (Y/N): ");
		String begin = input.next();
		input.nextLine();
		if(begin.equalsIgnoreCase("Y")){
			//begin
			}
		else{
			return;
			}
		}

	public static int distance(){

	}
	public static void turn(int direction){

		r.runEncodedMotor(RXTXRobot.MOTOR1, 300, tick, RXTXRobot.MOTOR2, (300 - 19), tick);
	}

	public static double[] move(double[] newPoint, int direct,){
		double x = getX();
		double y = getY();
		double xDist = newPoint[0] - x;
		double yDist = newPoint[1] - y;
		if(xDist < 0){
		turn(2);
		}
		else{
		turn(4);
		}
		tick = 100; //needs constant
		r.runEncodedMotor(RXTXRobot.MOTOR1, 300, tick, RXTXRobot.MOTOR2, (-300 + 19), tick);
		if(yDist <= 0){
		turn(1);
		}
		else{
		turn(3);
		}
		tick = 100; //needs constant
		r.runEncodedMotor(RXTXRobot.MOTOR1, 300, tick, RXTXRobot.MOTOR2, (-300 + 19), tick);
	}

	public static double[][] getData
}
