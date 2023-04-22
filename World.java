import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class World extends JFrame {
    private List<Object> gameObjects;

    public World() {
        super();
        gameObjects = new ArrayList<Object>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        this.setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        setLocationRelativeTo(null);
        //setLayout(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);


        // Create and add some GameObjects
        Car mustang = new Car(200, 300, 0);
        // Rectangle rectangle = new Rectangle(300, 300, 0);
        //Rectangle rect = new Rectangle(200, 200, 10);
        addKeyListener(mustang);
        addGameObject(mustang);
        // addGameObject(rectangle);
        //addGameObject(rect);

        validate();

        // Set up timer to update and repaint the window every 0.1 seconds
        Timer timer = new Timer(1, e -> {

//            if (!mustang.area1.isEmpty()) {
//                System.out.println("ciomageala");
//                mustang.x -= Math.round(Math.cos(Math.toRadians(mustang.angle)) * 100);
//                mustang.y -= Math.round(Math.sin(Math.toRadians(mustang.angle)) * 100);
//                mustang.speed = 0;
//                repaint();
//            }
            for (Object obj : gameObjects) {
                obj.updating();
            }
            this.repaint();
        });
        timer.start();
    }

    public void addGameObject(Object obj) {
        gameObjects.add(obj);
        add(obj);
    }

    public static void main(String[]args){
        World main = new World();
    }
}

