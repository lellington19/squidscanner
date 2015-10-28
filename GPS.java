
import rxtxrobot.*;

public class GPS{
  public static void main(String[] args){
    RXTXRobot r = new ArduinoNano();
    r.setPort("COM3");
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
