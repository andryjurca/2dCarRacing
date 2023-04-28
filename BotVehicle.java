import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

    public void updating(JPanel panel) {
        x += 2*Math.cos(Math.toRadians(angle));
        y += 2*Math.sin(Math.toRadians(angle));
        panel.repaint();


        if (y <= 100 && x <= 88) {
            angle+=2;
        }
        if (x >= 650 && y <= 100) {
            angle+=2;
        }
        if (y >= 650 && x >= 650) {
            angle+=2;
        }
        if (y >= 650 && x <= 88) {
            angle += 2;
        }
    }
}
