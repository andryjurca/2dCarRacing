import javax.swing.JPanel;
import java.awt.*;

public class Object extends JPanel{

    int x, y, angle;

    public Object (int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void updating() {
        ;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}

