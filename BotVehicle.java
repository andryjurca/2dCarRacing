import javax.swing.*;
import java.awt.event.KeyEvent;

public class BotVehicle extends Vehicle {
    public BotVehicle(int x, int y, int angle, String imageFileName) {
        super(x, y, angle, imageFileName);

        this.speed = 5;
    }

    public void keyPressed(KeyEvent e, JPanel panel) {;}

    public void keyReleased(KeyEvent e, JPanel panel) {;}

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

        if ((int) y <= 100 && normalizedAngle == 270) {
            angle += 90;
        }

        if ((int) x >= 600 && normalizedAngle == 0) {
            angle += 90;

        }
        if ((int) y >= 650 && normalizedAngle == 90) {
            angle += 90;
        }
        if ((int) x <= 150 && normalizedAngle == 180) {
            angle += 90;
        }
    }
}
