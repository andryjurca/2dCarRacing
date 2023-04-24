import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vehicle {
    int angle = 0;
    int x;
    int y;
    int width = 50;
    int height = 30;
    boolean isMoving = false;
    boolean isMovingBackwards = false;
    boolean turningRight = false;
    boolean turningLeft = false;
    boolean brake = false;
    int speed = 0;
    int turningSpeed = 0;
    int maxSpeed = 1000;
    int acceleration = 10;
    int maxSteeringSpeed = 1;
    int steeringAcceleration = 1;
    BufferedImage image;
    boolean imageLoaded;
    Image scaledImage;
    int scaledWidth;
    int scaledHeight;
    Shape rotatedRect;
    Rectangle imageRect;
    Area area1;
    Area area2;
    int control;

    public Vehicle(int x, int y, int angle, int control) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.control = control;

        try {
            image = ImageIO.read(new File("mustang.png"));
            imageLoaded = false;

        } catch (IOException e) {
            System.out.println("Error loading car image");
            imageLoaded = false;
        }

        double scalingFactor = Math.min(100.0 / image.getWidth(), 100.0 / image.getHeight());
        scaledWidth = (int) (scalingFactor * image.getWidth());
        scaledHeight = (int) (scalingFactor * image.getHeight());
        scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
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
                    brake = false;
                    isMovingBackwards = true;
                    break;
                case KeyEvent.VK_LEFT:
                    turningLeft = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    turningRight = true;
                    break;
            }
        if (control == 1) {
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        brake = false;
                        isMoving = true;
                        break;
                    case KeyEvent.VK_S:
                        brake = false;
                        isMovingBackwards = true;
                        break;
                    case KeyEvent.VK_A:
                        turningLeft = true;
                        break;
                    case KeyEvent.VK_D:
                        turningRight = true;
                        break;
                }
        }
        }

        panel.repaint();
    }

    public void keyReleased(KeyEvent e, JPanel panel) {
        if (control == 0) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                isMoving = false;
                speed = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                isMovingBackwards = false;
                speed = 0;
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
                speed = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                isMovingBackwards = false;
                speed = 0;
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
        x += Math.round(Math.cos(Math.toRadians(angle)) * speed/100);
        y += Math.round(Math.sin(Math.toRadians(angle)) * speed/100);
        panel.repaint();
    }

    public void moveBackwards(JPanel panel) {
        x += Math.round(Math.cos(Math.toRadians(angle)) * speed/100);
        y += Math.round(Math.sin(Math.toRadians(angle)) * speed/100);
        panel.repaint();
    }

    public void turnRight() {
        if (isMoving) {
            angle += (turningSpeed);
        }
        if (isMovingBackwards) {
            angle += (turningSpeed);
        }
    }

    public void turnLeft() {
        if (isMoving) {
            angle -= (turningSpeed);
        }
        if (isMovingBackwards) {
            angle -= (turningSpeed);
        }
    }

    public void colliding(JPanel panel) {
        x -= Math.round(Math.cos(Math.toRadians(angle)) * (speed/10));
        y -= Math.round(Math.sin(Math.toRadians(angle)) * (speed/10));
        speed = 0;

        panel.repaint();

    }

    public void updating(JPanel panel) {
        if (isMoving) {
            moveCar(panel);
            if (speed <= maxSpeed) {

                speed += acceleration;
            }

        }
        if (isMovingBackwards) {
            moveBackwards(panel);
            if (speed <= maxSpeed) {

                speed -= acceleration;
            }

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
