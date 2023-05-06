import java.util.Scanner;

public class Engine {
    private static final int MIN_RPM = 1000;
    private static final int MAX_RPM = 6000; // maximum RPM
    private static final int MAX_GEAR = 6; // maximum gear
    private static final int MIN_GEAR = 1; // minimum gear
    private static final double MAX_SPEED = 300; // maximum speed in km/h
    private static final int ACCELERATOR = 50;
    private static final int TORQUE = 300;
    private static final int HP = 200;
    private static final int FRICTION = 3;
    private int rpm;
    private int gear;
    private double speed;
    boolean accelerated = false;




    public Engine() {
        rpm = 0;
        gear = 1;
        speed = 0;
    }
    public void restart() {
        rpm = 0;
        gear = 1;
        speed = 0;
    }


    public void run() {

        if (rpm >= FRICTION) {
            rpm -= FRICTION;
        }
        if (rpm <= FRICTION) {
            rpm = 0;
        }
        if (!goodRpm()) {
            restart();
        }
        EngineSound.playEngineSound(rpm);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
    public boolean goodRpm() {
        if (MIN_RPM <= rpm && rpm <= MAX_RPM) {
            return true;
        }
        if (gear == 1 && rpm <= MAX_RPM) {
            return true;
        }
        return false;

    }
    public void brake() {
        if (rpm >= 50)
            rpm -= 50;
        speed = ((double)rpm * gear / MAX_RPM * MAX_SPEED)/2;
    }
    public void pressAccelerator() {
        accelerated = true;
        rpm += ACCELERATOR / gear;

        // calculate speed based on rpm and gear
        speed = ((double)rpm * gear / MAX_RPM * MAX_SPEED)/2;

    }

    public void shiftUp() {
        if (gear < MAX_GEAR) {
            gear++;
            rpm = (int)(rpm * 0.6);
        }

        // calculate speed based on rpm and gear
        speed = ((double)rpm * gear / MAX_RPM * MAX_SPEED)/2;
    }

    public void shiftDown() {
        if (gear > MIN_GEAR) {
            gear--;
            rpm = (int)(rpm * 1.4);
        }

        // calculate speed based on rpm and gear
        speed = ((double)rpm * gear / MAX_RPM * MAX_SPEED)/2 ;
    }

    public double getSpeed() {
        return ((double)rpm * gear / MAX_RPM * MAX_SPEED)/2;
    }

    public int getRPM() {
        return rpm;
    }

    public int getGear() {
        return gear;
    }

    public static void main(String[] args) {
        Engine car = new Engine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            car.run();
            System.out.println("Current speed: " + car.getSpeed() + " km/h, RPM: " + car.getRPM() + ", Gear: " + car.getGear());
            System.out.println("Press 'a' to accelerate, 'l' to shift up, 'j' to shift down, or 'q' to quit:");

            String input = scanner.nextLine();

            if (input.equals("a")) {
                car.pressAccelerator();
            }
            else if (input.equals("b")) {
                car.brake();
            } else if (input.equals("l")) {
                car.shiftUp();
            } else if (input.equals("j")) {
                car.shiftDown();
            } else if (input.equals("q")) {
                break;
            }
        }

        scanner.close();
    }
}