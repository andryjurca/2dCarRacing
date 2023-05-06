import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World extends JFrame {
    private List<GamePanel> gameObjects;

    public World() {
        super();
        gameObjects = new ArrayList<GamePanel>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        this.setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        setLocationRelativeTo(null);
        //setLayout(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        GamePanel gamePanel = new GamePanel(300, 300, 20);

        addKeyListener(gamePanel);
        addGameObject(gamePanel);

        validate();
        // Set up timer to update and repaint the window every 0.1 seconds
        Timer timer = new Timer(10, e -> {
            for (GamePanel obj : gameObjects) {
                obj.updating();
            }
            this.repaint();
        });
        timer.start();
    }

    public void addGameObject(GamePanel obj) {
        gameObjects.add(obj);
        add(obj);
    }

    public static void main(String[]args){
        World main = new World();
    }
}

