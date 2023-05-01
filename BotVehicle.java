import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BotVehicle {
    double x;
    double y;
    int angle;
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
    int speed = 4;
    public BotVehicle(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
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

    public void keyPressed(KeyEvent e, JPanel panel) {;}

    public void keyReleased(KeyEvent e, JPanel panel) {;}

    public void colliding(JPanel panel) {
        int speed = 0;
        x -= Math.round(Math.cos(Math.toRadians(angle)) * (speed/10));
        y -= Math.round(Math.sin(Math.toRadians(angle)) * (speed/10));
        speed = 0;

        panel.repaint();

    }

    private int normalizeAngle(int angle) {
        int normalizedAngle = angle % 360;

        if (normalizedAngle < 0) {
            normalizedAngle += 360;
        }
        if (normalizedAngle == 360) {
            normalizedAngle = 0;
        }

        return normalizedAngle;
    }

    public void updating(JPanel panel) {
        x += speed*Math.cos(Math.toRadians(angle));
        y += speed*Math.sin(Math.toRadians(angle));
        panel.repaint();
        int normalizedAngle = normalizeAngle(angle);
        System.out.println((int) x);

        if ((int) y <= 100 && normalizedAngle == 270) {
            angle += 90;
            System.out.println("primul");
        }

        if ((int) x >= 600 && normalizedAngle == 0) {
            angle += 90;
            System.out.println("doi");

        }
        if ((int) y >= 650 && normalizedAngle == 90) {
            angle += 90;
            System.out.println("trei");
        }
        if ((int) x <= 150 && normalizedAngle == 180) {
            angle += 90;
            System.out.println("patru");
        }
    }
}
