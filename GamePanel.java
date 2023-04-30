import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class GamePanel extends JPanel implements KeyListener {
    Rectangle rectangle = new Rectangle(0, 0, 800, 10);
    Rectangle rectangle2 = new Rectangle(0, 800, 800, 10);
    Rectangle rectangle3 = new Rectangle(0, 0, 10, 800);
    Rectangle rectangle4 = new Rectangle(800, 0, 10, 800);
    Rectangle rectangle5 = new Rectangle(210, 130, 300, 500);
    Rectangle finishRect = new Rectangle(10, 400, 200, 10);
    Area finishArea = new Area(finishRect);
    Rectangle[] rectList = {rectangle, rectangle2, rectangle3, rectangle4, rectangle5};
    Graphics2D g2d;
    Vehicle car = new Vehicle(100, 200, -90);
    BotVehicle car2 = new BotVehicle(30, 300, -90);
    long startTime;
    Long elapsedTime;
    int score;
    boolean down = false;
    boolean up = false;
    int lap = 0;
    int laps = 2;
    boolean lapped = false;
    long endTime;

    public GamePanel(int x, int y, int angle) {
        setBackground(Color.gray);
        setPreferredSize(new Dimension(car.image.getWidth(), car.image.getHeight()));
        startTime = System.currentTimeMillis();


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D g2dRect = (Graphics2D) g.create();
        Graphics2D g2dSecondcar = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(car.angle), car.x + car.width / 2, car.y + car.height / 2);
        g2d.setColor(Color.RED);
        g2d.drawImage(car.scaledImage, (int) (car.x-car.scaledWidth/3), (int) (car.y-car.scaledHeight/2), null);

        // adaugat

        g2dSecondcar.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dSecondcar.rotate(Math.toRadians(car2.angle), car2.x + car2.width / 2, car2.y + car2.height / 2);
        g2dSecondcar.setColor(Color.RED);
        g2dSecondcar.drawImage(car2.scaledImage, (int) (car2.x-car2.scaledWidth/3), (int) (car2.y-car2.scaledHeight/2), null);


        car.imageRect = new Rectangle((int) (car.x-car.scaledWidth/3), (int) (car.y-car.scaledHeight/2), car.scaledWidth, car.scaledHeight);
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
        if (lap < laps) {
            endTime = System.currentTimeMillis(); // get the end time
            elapsedTime = endTime - startTime; // calculate the elapsed time
        }




        g2dRect.draw(finishRect);
        car.area2.intersect(finishArea);
        if (!car.area2.isEmpty()) {
            if (car.y <= 390) {
                up = true;
                if (!down)
                    car.y -= car.engine.getSpeed() / 10;
                    //car.engine.restart();

            }
            if (car.y >= 400) {
                down = true;
                if (!up) {

                    if (lap == laps) {

                        if (elapsedTime < score) {
                            writeScore();
                        }
                    }

                    if (!lapped) {
                        lap += 1;
                        lapped = true;
                    }
                }

            }

        }
        else {
            up = false;
            down = false;
            lapped = false;
        }



        Font font = new Font("Arial", Font.PLAIN, 20); // set the font and size
        g2dRect.setFont(font);
        g2dRect.drawString("Speed: " + (int) car.engine.getSpeed() / 4, 10, 100);
        g2dRect.drawString("RPM: " + car.engine.getRPM(), 130, 100);
        g2dRect.drawString("Gear: " + car.engine.getGear(), 250, 100);
        g2dRect.drawString("Highscore: " + readScore(), 350, 100);
        g2dRect.drawString("Score: " + elapsedTime, 550, 100);
        g2dRect.drawString("Lap: " + lap + " / " + laps, 700, 100);

        }

        public int readScore() {
            try {
                File file = new File("score.txt");
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    score = Integer.parseInt(line);
                }


                bufferedReader.close();
                fileReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return score;
        }
        public void writeScore() {
            try {
                FileWriter fileWriter = new FileWriter("score.txt", false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(Long.toString(elapsedTime));
                score = Math.toIntExact(elapsedTime);

                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        car.keyPressed(e, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        car.keyReleased(e, this);

    }

    public void updating() {
        car.updating(this);
        car2.updating(this);

    }
}