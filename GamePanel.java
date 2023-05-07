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
    Rectangle rectangle = new Rectangle(0, 0, 8000, 10);
    Rectangle rectangle2 = new Rectangle(0, 830, 8000, 10);
    Rectangle rectangle3 = new Rectangle(0, 0, 10, 830);
    Rectangle rectangle4 = new Rectangle(1560, 0, 10, 800);
    Rectangle rectangle5 = new Rectangle(210, 130, 200, 500);
    Rectangle rectangle6 = new Rectangle(400, 430, 200, 200);
    Rectangle rectangle7 = new Rectangle(500, 0, 300, 300);
    Rectangle rectangle8 = new Rectangle(600, 430, 600, 200);
    Rectangle rectangle9 = new Rectangle(1000, 300, 300, 200);
    Rectangle finishRect = new Rectangle(10, 400, 200, 10);
    Area finishArea = new Area(finishRect);
    Rectangle[] rectList = {rectangle, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, rectangle7, rectangle8, rectangle9};
    Graphics2D g2d;
    ManualVehicle car = new ManualVehicle(100, 200, -90, "audi.png", 1);
    AutomaticVehicle car2 = new AutomaticVehicle(90, 200, -90, "mustang.png", 0);
    long startTime;
    Long elapsedTime;
    long elapsedTime2;
    int score;

    int laps = 5;

    long endTime;
    boolean car1won;
    boolean finishedRace = false;


    public GamePanel(int x, int y, int angle) {
        setBackground(Color.lightGray);
        //setPreferredSize(new Dimension(car.image.getWidth(), car.image.getHeight()));
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

        car2.imageRect = new Rectangle((int) (car2.x-car2.scaledWidth/3), (int) (car2.y-car2.scaledHeight/2), car2.scaledWidth, car2.scaledHeight);
        Rectangle2D rect2 = new Rectangle2D.Double(car2.imageRect.getX(), car2.imageRect.getY(),
                car2.imageRect.getWidth(), car2.imageRect.getHeight());
        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(Math.toRadians(car2.angle), car2.x + car2.width / 2, car2.y + car2.height / 2);
        car2.rotatedRect = transform2.createTransformedShape(rect2);
        Rectangle2D bounds2 = car2.rotatedRect.getBounds2D();
        car2.area2 = new Area(car2.rotatedRect);

        //g2dRect.draw(car.rotatedRect);
        //g2dRect.draw(car2.rotatedRect);
        for (Rectangle rectus : rectList) {
            g2dRect.fill(rectus);
            car.area1 = new Area(rectus);
            car2.area1 = new Area(rectus);
            car.area1.intersect(car.area2);
            car2.area1.intersect(car2.area2);
            if (!car.area1.isEmpty()) {
                car.colliding(this);
            }
            if (!car2.area1.isEmpty()) {
                car2.colliding(this);
            }
        }


        if (car.lap < laps) {
            endTime = System.currentTimeMillis(); // get the end time
            elapsedTime = endTime - startTime; // calculate the elapsed time
        }
        if (car2.lap < laps) {
            elapsedTime2 = System.currentTimeMillis()-startTime;
        }
        if (car.lap == laps) {
            finishedRace = true;
            if (elapsedTime2>elapsedTime)
                car1won = true;
        }
        if (car2.lap == laps) {
            finishedRace = true;
            if (elapsedTime>elapsedTime2)
                car1won = false;
        }

        g2dRect.draw(finishRect);
        car.area2.intersect(finishArea);
        if (!car.area2.isEmpty()) {
            if (car.y <= finishRect.y-finishRect.height) {
                car.up = true;
                if (!car.down)
                    car.y -= car.speed / 10;
                    //car.engine.restart();

            }
            if (car.y >= finishRect.y) {
                car.down = true;
                if (!car.up) {

                    if (car.lap == laps) {

                        if (elapsedTime < score) {
                            writeScore(elapsedTime);
                        }
                    }

                    if (!car.lapped) {
                        car.lap += 1;
                        car.lapped = true;
                    }
                }

            }

        }
        else {
            car.up = false;
            car.down = false;
            car.lapped = false;
        }

        car2.area2.intersect(finishArea);
        if (!car2.area2.isEmpty()) {
            if (car2.y <= 390) {
                car2.up = true;
                if (!car2.down)
                    car2.y -= car2.speed / 10;
                //car.engine.restart();

            }
            if (car2.y >= 400) {
                car2.down = true;
                if (!car2.up) {

                    if (car2.lap == laps) {

                        if (elapsedTime2 < score) {
                            writeScore(elapsedTime2);
                        }
                    }

                    if (!car2.lapped) {
                        car2.lap += 1;
                        car2.lapped = true;
                    }
                }

            }

        }
        else {
            car2.up = false;
            car2.down = false;
            car2.lapped = false;
        }



        Font font = new Font("Arial", Font.PLAIN, 20); // set the font and size
        g2dRect.setFont(font);
        g2dRect.drawString("Speed: " + (int) car.engine.getSpeed() / 4, 10, 100);
        g2dRect.drawString("RPM: " + car.engine.getRPM(), 130, 100);
        g2dRect.drawString("Gear: " + car.engine.getGear(), 250, 100);
        g2dRect.drawString("Highscore: " + readScore(), 950, 100);
        g2dRect.drawString("Score: " + elapsedTime, 1150, 100);
        g2dRect.drawString("Lap: " + car.lap + " / " + laps, 1300, 100);

//        g2dRect.drawString("Speed: " + (int) car2.speed / 4, 10, 700);
        g2dRect.drawString("Speed: " + (int) car2.speed / 4, 10, 700);
//        g2dRect.drawString("RPM: " + car2.engine.getRPM(), 130, 700);
//        g2dRect.drawString("Gear: " + car2.engine.getGear(), 250, 700);
        // pana aici
        g2dRect.drawString("Highscore: " + readScore(), 950, 700);
        g2dRect.drawString("Score: " + elapsedTime2, 1150, 700);
        g2dRect.drawString("Lap: " + car2.lap + " / " + laps, 1300, 700);
        if (finishedRace) {
            if (car1won)
                g2dRect.drawString("Car 1 Won!!!" , 90, 380);
            if (!car1won)
                g2dRect.drawString("Car 2 Won!!!" , 90, 380);
        }


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
    public void writeScore(long elapsedTime) {
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