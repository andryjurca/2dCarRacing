import javax.swing.*;
import java.awt.event.KeyEvent;


public class ManualVehicle extends Vehicle {
    boolean isMoving = false;
    boolean isMovingBackwards = false;
    boolean turningRight = false;
    boolean turningLeft = false;
    boolean brake = false;
    int turningSpeed = 0;
    int maxSpeed = 1000;
    int acceleration = 10;
    int maxSteeringSpeed = 1;
    int steeringAcceleration = 1;

    Engine engine = new Engine();
    int control;
    public ManualVehicle(int x, int y, int angle, String imageFile, int control) {
        super(x, y, angle, imageFile);
        this.control = control;
    }

    public void keyPressed(KeyEvent e, JPanel panel) {
        int keyCode = e.getKeyCode();
        if (control == 0) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    brake = false;
                    isMoving = true;
                    break;
                case KeyEvent.VK_DOWN:
                    brake = true;
                    break;
                case KeyEvent.VK_LEFT:
                    turningLeft = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    turningRight = true;
                    break;
            }
        }
        if (control == 1) {
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        brake = false;
                        isMoving = true;
                        break;
                    case KeyEvent.VK_S:
                        brake = true;
                        break;
                    case KeyEvent.VK_A:
                        turningLeft = true;
                        break;
                    case KeyEvent.VK_D:
                        turningRight = true;
                        break;
                }


        }

        panel.repaint();
    }

    public void keyReleased(KeyEvent e, JPanel panel) {
            if (control == 0) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    isMoving = false;
                    //speed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    brake = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    turningRight = false;
                    turningSpeed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    turningLeft = false;
                    turningSpeed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    engine.shiftUp();
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    engine.shiftDown();
                }
            }
            if (control == 1) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    isMoving = false;
                    //speed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    brake = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    turningRight = false;
                    turningSpeed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    turningLeft = false;
                    turningSpeed = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    engine.shiftUp();
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    engine.shiftDown();
                }
            }

    }

    public void moveCar(JPanel panel) {
        double dx = engine.getSpeed() * Math.cos(Math.toRadians(angle)); // Calculate the change in x based on the car's speed and heading
        double dy = engine.getSpeed() * Math.sin(Math.toRadians(angle)); // Calculate the change in y based on the car's speed and heading
        x += dx / 100;
        y += dy / 100;
        panel.repaint();
    }

    public void moveBackwards(JPanel panel) {
        double dx = engine.getSpeed() * Math.cos(Math.toRadians(angle)); // Calculate the change in x based on the car's speed and heading
        double dy = engine.getSpeed() * Math.sin(Math.toRadians(angle)); // Calculate the change in y based on the car's speed and heading
        x -= dx / 100;
        y -= dy / 100;
        panel.repaint();
    }

    public void turnRight() {
        if (!(engine.getSpeed() <= 0))
            angle += (turningSpeed);
    }

    public void turnLeft() {
        if (!(engine.getSpeed() <= 0))
            angle -= (turningSpeed);
    }
    public void brake() {
        engine.brake();
    }

    public void colliding(JPanel panel) {
        engine.restart();
        x -= Math.round(Math.cos(Math.toRadians(angle)) * (engine.getSpeed()/10));
        y -= Math.round(Math.sin(Math.toRadians(angle)) * (engine.getSpeed()/10));
        engine.restart();

        panel.repaint();

    }

    public void updating(JPanel panel) {
            engine.run();
            speed = engine.getSpeed();
            moveCar(panel);
            if (isMoving) {
                engine.pressAccelerator();
            }
            if (brake) {
                engine.brake();
            }


            if (turningRight) {
                turnRight();
                if (turningSpeed <= maxSteeringSpeed) {
                    turningSpeed += steeringAcceleration;
                }

            }
            if (turningLeft) {
                turnLeft();
                if (turningSpeed <= maxSteeringSpeed) {
                    turningSpeed += steeringAcceleration;
                }

        }



    }


}
