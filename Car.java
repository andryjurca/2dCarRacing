import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;

public class Car extends Object implements KeyListener {
    int angle = 0;
    int x;
    int y;
    int width = 50;
    int height = 30;
    private boolean isMoving = false;
    private boolean turningRight = false;
    private boolean turningLeft = false;
    private boolean brake = false;
    int speed = 0;
    private int turningSpeed = 0;
    private int maxSpeed = 1000;
    private int acceleration = 10;
    private int maxSteeringSpeed = 1;
    private int steeringAcceleration = 1;
    private BufferedImage carImage;
    private Image scaledImage;
    private int scaledWidth;
    private int scaledHeight;
    Rectangle rectangle = new Rectangle(100, 100, 500, 10);
    Rectangle rectangle2 = new Rectangle(100, 300, 500, 10);
    Rectangle[] rectList = {rectangle, rectangle2};
    Shape rotatedRect;
    Rectangle imageRect;
    Rectangle carRectangle;
    Graphics2D g2d;
    Area area1;
    Area area2;

    public Car(int x, int y, int angle) {
        super(x, y, angle);
        setBackground(Color.red);
//        Rectangle rect = new Rectangle(100, 100, 45);
//        add(rect);
        //setSize(100, 100);

        try {
            carImage = ImageIO.read(new File("mustang.png"));
            setPreferredSize(new Dimension(carImage.getWidth(), carImage.getHeight()));
        } catch (IOException e) {
            System.out.println("Error loading car image");
        }

        double scalingFactor = Math.min(100.0 / carImage.getWidth(), 100.0 / carImage.getHeight());
        scaledWidth = (int) (scalingFactor * carImage.getWidth());
        scaledHeight = (int) (scalingFactor * carImage.getHeight());
        scaledImage = carImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D g2dRect = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(angle), x + width / 2, y + height / 2);
        g2d.setColor(Color.RED);
        g2d.drawImage(scaledImage, x-scaledWidth/3, y-scaledHeight/2, null);

        imageRect = new Rectangle(x-scaledWidth/3, y-scaledHeight/2, scaledWidth, scaledHeight);
        Rectangle2D rect = new Rectangle2D.Double(imageRect.getX(), imageRect.getY(),
                imageRect.getWidth(), imageRect.getHeight());
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), x + width / 2, y + height / 2);
        rotatedRect = transform.createTransformedShape(rect);
        Rectangle2D bounds = rotatedRect.getBounds2D();
        area2 = new Area(rotatedRect);

        // g2dRect.draw(rotatedRect);
        for (Rectangle rectus : rectList) {
            g2dRect.draw(rectus);
            area1 = new Area(rectus);
            area1.intersect(area2);
            if (!area1.isEmpty()) {
                colliding();
            }
        }

        }


        //Area area1Copy = new Area(area1);






        //g2dRect.draw(carRectangle);



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                brake = false;
                isMoving = true;
                break;
            case KeyEvent.VK_LEFT:
                turningLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                turningRight = true;
                break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            isMoving = false;
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

    public void moveCar() {
        x += Math.round(Math.cos(Math.toRadians(angle)) * speed/100);
        y += Math.round(Math.sin(Math.toRadians(angle)) * speed/100);
        repaint();
    }

    public void turnRight() {
        if (isMoving) {
            angle += (turningSpeed);
        }
    }

    public void turnLeft() {
        if (isMoving) {
            angle -= (turningSpeed);
        }
    }

    public void colliding() {
        x -= Math.round(Math.cos(Math.toRadians(angle)) * speed/10);
        y -= Math.round(Math.sin(Math.toRadians(angle)) * speed/10);
        speed = 0;

        repaint();

    }

    @Override
    public void updating() {
        if (isMoving) {
            moveCar();
            if (speed <= maxSpeed) {

                speed += acceleration;
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