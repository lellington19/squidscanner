import java.util.Scanner;
import rxtxrobot.*;

public class SensorCalibration{
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    RXTXRobot sensor = new ArduinoNano();
    sensor.setPort("COM4");
    RXTXRobot motor = new ArduinoNano();
    motor.setPort("COM3");
    int option = 0;
    option = menu(input);
    while(option != -1){
      switch(option){
        case 1:
          conductivity(input, motor, sensor);
          break;
        case 2:
          thermistor(input, 0, sensor);
          break;
        case 3:
          thermistor(input, 1, sensor);
          break;
        case 4:
          thermistor(input, sensor);
          break;
        case 5:
          ping(input, 6, sensor);
          break;
        case 6:
          ping(input, 7, sensor);
          break;
        case 7:
          ping(input, sensor);
          break;
        case 8:
          raise(input, motor, sensor);
          break;
        case 9:
          differential(input, motor);
          break;
        case 10:
          turn(input, motor);
          break;
        case 11:
          whellie(motor);
          break;
        default:
          System.out.println("Try Again");
      }
      option = menu(input);
    }
  }

  public static int menu(Scanner input){
    System.out.println("1 - Conductivity Calibration");
    System.out.println("2 - Thermistor One Calibration");
    System.out.println("3 - Thermistor Two Calibration");
    System.out.println("4 - Both Thermistor Calibration");
    System.out.println("5 - Ping Sensor 1 Readout");
    System.out.println("6 - Ping Sensor 2 Readout");
    System.out.println("7 - Both Ping Senstor Readout");
    System.out.println("8 - Raised Anemometer");
    System.out.println("9 - Speed Differential Calibration");
    System.out.println("10 - Turning Test");
    System.out.println("11 - \"Sensor Reading\"");
    System.out.println("-1 - Quit");
    System.out.print("Enter Selection: ");
    int  option = input.nextInt();
    input.nextLine();
    return option;
  }

  public static void conductivity(Scanner input, RXTXRobot motor, RXTXRobot sensor){
    System.out.print("Enter time for testing: ");
    int time = input.nextInt();
    input.nextLine();
    motor.connect();
    sensor.connect();
    motor.attachServo(0, 7);
    motor.moveServo(0, 0);
    for(int counter = 0; counter < time; counter++){
      System.out.println(sensor.getConductivity());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    motor.moveServo(0, 90);
    sensor.close();
  }

  public static void thermistor(Scanner input, int pin, RXTXRobot sensor){
    System.out.print("Enter Loop Count: ");
    int loopCount = input.nextInt();
    input.nextLine();
    sensor.connect();
    for(int counter = 0; counter < loopCount; counter++){
      sensor.refreshAnalogPins();
      System.out.println(sensor.getAnalogPin(pin));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sensor.close();
  }

  public static void thermistor(Scanner input, RXTXRobot sensor){
    System.out.print("Enter Loop Count: ");
    int loopCount = input.nextInt();
    input.nextLine();
    sensor.connect();
    for(int counter = 0; counter < loopCount; counter++){
      sensor.refreshAnalogPins();
      System.out.println(sensor.getAnalogPin(0) + "  " + sensor.getAnalogPin(1));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sensor.close();
  }

  public static void ping(Scanner input, int pin, RXTXRobot sensor){
    System.out.print("Enter Loop Count: ");
    int loopCount = input.nextInt();
    input.nextLine();
    sensor.connect();
    for(int counter = 0; counter < loopCount; counter++){
      System.out.println(sensor.getPing(pin));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sensor.close();
  }

  public static void ping(Scanner input, RXTXRobot sensor){
    System.out.print("Enter Loop Count: ");
    int loopCount = input.nextInt();
    input.nextLine();
    sensor.connect();
    for(int counter = 0; counter < loopCount; counter++){
      sensor.refreshAnalogPins();
      System.out.println(sensor.getPing(6) + "  " + sensor.getPing(7));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sensor.close();
  }

  public static void raise(Scanner input, RXTXRobot motor, RXTXRobot sensor){
    motor.connect();
    motor.attachMotor(2, 4);
    motor.runMotor(2, -300, 13000);
    thermistor(input, sensor);
    motor.runMotor(2, 300, 13000);
    motor.close();
  }

  public static void differential(Scanner input, RXTXRobot motor){
    System.out.print("Enter run speed: ");
    int speed = input.nextInt();
    System.out.print("Enter differential: ");
    int diff = input.nextInt();
    System.out.print("Enter Time: ");
    int time = input.nextInt();
    motor.connect();
    motor.attachMotor(0, 5);
    motor.attachMotor(1, 6);
    motor.runMotor(0, speed, 1, (-speed + diff), time);
    motor.close();
  }

  public static void turn(Scanner input, RXTXRobot motor){
    System.out.print("Enter Motor One Speed: ");
    int speed1 = input.nextInt();
    input.nextLine();
    System.out.print("Enter Motor Two Speed: ");
    int speed2 = input.nextInt();
    input.nextLine();
    System.out.print("Enter Motor One Tick: ");
    int tick1 = input.nextInt();
    input.nextLine();
    System.out.print("Enter Motor One Tick: ");
    int tick2 = input.nextInt();
    input.nextLine();
    motor.connect();
    motor.attachMotor(0, 5);
    motor.attachMotor(1, 6);
    motor.runEncodedMotor(0, speed1, tick1, 1, speed2, tick2);
    motor.close();
  }

  public static void whellie(RXTXRobot motor){
    motor.connect();
    motor.attachMotor(0, 5);
    motor.attachMotor(1, 6);
    motor.runMotor(0, -500, 1, 500, 0);
    for(int i = 0; i < 1; i++){
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
    motor.runMotor(0, 500, 1, -500, 3000);
    motor.close();
  }

}
