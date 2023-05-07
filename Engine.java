import java.util.Scanner;

public class Engine {
    private static final int MIN_RPM = 1000;
    private static final int MAX_RPM = 6000;
    private static final int MAX_GEAR = 6;
    private static final double HP = 300;
    private static final int ACCELERATOR = 50;
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
        speed = getSpeed();
    }
    public void pressAccelerator() {
        accelerated = true;
        rpm += ACCELERATOR / gear;
        speed = getSpeed();
    }
    public void shiftUp() {
        if (gear < MAX_GEAR) {
            gear++;
            rpm = (int)(rpm * 0.6);
        }
        speed = getSpeed();
    }
    public void shiftDown() {
        if (gear > 1) {
            gear--;
            rpm = (int)(rpm * 1.4);
        }
        speed = getSpeed();
    }

    public double getSpeed() {
        speed = ((double)rpm * gear / MAX_RPM * HP)/3;
        return speed;
    }
    public int getRPM() {
        return rpm;
    }
    public int getGear() {
        return gear;
    }

}