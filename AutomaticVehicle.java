import javax.swing.*;
import java.awt.event.KeyEvent;

public class AutomaticVehicle extends Vehicle {
    boolean isMoving = false;
    boolean isMovingBackwards = false;
    boolean turningRight = false;
    boolean turningLeft = false;
    boolean brake = false;
    int turningSpeed = 0;
    int maxSpeed = 500;
    int acceleration = 10;
    int maxSteeringSpeed = 1;
    int steeringAcceleration = 1;
    int control;
    int decrease = 3;

    public AutomaticVehicle(int x, int y, int angle, String imageFileName, int control) {
        super(x, y, angle, imageFileName);
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
        }

    }

    public void moveCar(JPanel panel) {
        double dx = speed * Math.cos(Math.toRadians(angle)); // Calculate the change in x based on the car's speed and heading
        double dy = speed * Math.sin(Math.toRadians(angle)); // Calculate the change in y based on the car's speed and heading
        x += dx / 100;
        y += dy / 100;
        panel.repaint();
    }

    /*public void moveBackwards(JPanel panel) {
        double dx = speed * Math.cos(Math.toRadians(angle)); // Calculate the change in x based on the car's speed and heading
        double dy = speed * Math.sin(Math.toRadians(angle)); // Calculate the change in y based on the car's speed and heading
        x -= dx / 100;
        y -= dy / 100;
        panel.repaint();
    }*/

    public void turnRight() {
        if (!(speed <= 0))
            angle += (turningSpeed);
    }

    public void turnLeft() {
        if (!(speed <= 0))
            angle -= (turningSpeed);
    }
    public void brake() {
        speed -= 10;
    }

    public void colliding(JPanel panel) {
        speed = 0;
        x -= Math.round(Math.cos(Math.toRadians(angle)) * (speed/10));
        y -= Math.round(Math.sin(Math.toRadians(angle)) * (speed/10));
        speed = 0;

        panel.repaint();

    }

    public void updating(JPanel panel) {
        if (!(speed <= decrease))
            speed -= decrease;
        else {
            speed = 0;
        }
        moveCar(panel);
        if (isMoving) {
            if (speed <= maxSpeed)
                speed+=acceleration;
        }
        if (brake) {
            brake();
        }

        /*if (isMovingBackwards) {
            moveBackwards(panel);
            if (speed <= maxSpeed) {

                speed -= acceleration;
            }

        }*/

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
