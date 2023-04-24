import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;

public class GamePanel extends JPanel implements KeyListener {
    Rectangle rectangle = new Rectangle(0, 0, 800, 10);
    Rectangle rectangle2 = new Rectangle(0, 800, 800, 10);
    Rectangle rectangle3 = new Rectangle(0, 0, 10, 800);
    Rectangle rectangle4 = new Rectangle(800, 0, 10, 800);
    Rectangle rectangle5 = new Rectangle(400, 400, 100, 100);
    Rectangle[] rectList = {rectangle, rectangle2, rectangle3, rectangle4};
    Graphics2D g2d;
    Vehicle car = new Vehicle(400, 200, 45, 0);
    Vehicle car2 = new Vehicle(300, 300, 10, 1);

    public GamePanel(int x, int y, int angle) {
        setBackground(Color.gray);
        setPreferredSize(new Dimension(car.image.getWidth(), car.image.getHeight()));

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D g2dRect = (Graphics2D) g.create();
        Graphics2D g2dSecondcar = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(car.angle), car.x + car.width / 2, car.y + car.height / 2);
        g2d.setColor(Color.RED);
        g2d.drawImage(car.scaledImage, car.x-car.scaledWidth/3, car.y-car.scaledHeight/2, null);

        // adaugat

        g2dSecondcar.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dSecondcar.rotate(Math.toRadians(car2.angle), car2.x + car2.width / 2, car2.y + car2.height / 2);
        g2dSecondcar.setColor(Color.RED);
        g2dSecondcar.drawImage(car2.scaledImage, car2.x-car2.scaledWidth/3, car2.y-car2.scaledHeight/2, null);


        car.imageRect = new Rectangle(car.x-car.scaledWidth/3, car.y-car.scaledHeight/2, car.scaledWidth, car.scaledHeight);
        Rectangle2D rect = new Rectangle2D.Double(car.imageRect.getX(), car.imageRect.getY(),
                car.imageRect.getWidth(), car.imageRect.getHeight());
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(car.angle), car.x + car.width / 2, car.y + car.height / 2);
        car.rotatedRect = transform.createTransformedShape(rect);
        Rectangle2D bounds = car.rotatedRect.getBounds2D();
        car.area2 = new Area(car.rotatedRect);

        g2dRect.draw(car.rotatedRect);
        for (Rectangle rectus : rectList) {
            g2dRect.draw(rectus);
            car.area1 = new Area(rectus);
            car.area1.intersect(car.area2);
            if (!car.area1.isEmpty()) {
                car.colliding(this);
            }
        }

        }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        car.keyPressed(e, this);
        car2.keyPressed(e, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        car.keyReleased(e, this);
        car2.keyReleased(e, this);

    }

    public void updating() {
        car.updating(this);
        car2.updating(this);
    }
}