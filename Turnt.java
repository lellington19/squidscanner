 public static void turnt(RXTXRobot motor, RXTXRobot sensor){
    System.out.println("Aligning...");
    motor.connect();
    sensor.connect();
    motor.attachMotor(0, 5);
    motor.attachMotor(1, 6);
    for(int i = 0; i < 20; i ++){
      sensor.refreshAnalogPins();
      int ping1 = sensor.getPing(6) + 1;
      int ping2 = sensor.getPing(7);
      System.out.println(ping1 + " " + ping2);
      if(ping1 < ping2){
        motor.runEncodedMotor(0, 200, 5, 1, 200, 5);

      }
      else if(ping1 > ping2 ){
        motor.runEncodedMotor(0, -200, 10, 1, -200, 1);
      }
      else if(ping1 == ping2){
        System.out.println("Nah Fam");
      }
    }
    System.out.println("We Done");
  }
