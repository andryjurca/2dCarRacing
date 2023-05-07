import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Vehicle {
    double x;
    double y;
    int angle = 0;
    int width = 50;
    int height = 30;
    BufferedImage image;
    boolean imageLoaded;
    Image scaledImage;
    int scaledWidth;
    int scaledHeight;
    Shape rotatedRect;
    Rectangle imageRect;
    Area area1;
    Area area2;
    double speed;
    boolean up;
    boolean down;
    int lap = 0;
    boolean lapped;
    public Vehicle(int x, int y, int angle, String imageFileName) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        try {
            image = ImageIO.read(new File(imageFileName));
            imageLoaded = false;

        } catch (IOException e) {
            System.out.println("Error loading car image");
            imageLoaded = false;
        }
        double scalingFactor = Math.min(80.0 / image.getWidth(), 100.0 / image.getHeight());
        scaledWidth = (int) (scalingFactor * image.getWidth());
        scaledHeight = (int) (scalingFactor * image.getHeight());
        scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
    }

    public abstract void keyPressed(KeyEvent e, JPanel panel);

    public abstract void keyReleased(KeyEvent e, JPanel panel);

    public void colliding(JPanel panel) {
        // Code for handling collision goes here
    }

    public void updating(JPanel panel) {

        // Code for handling track boundary goes here
    }
}
