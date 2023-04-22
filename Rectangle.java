import java.awt.*;

public class Rectangle extends Object {

    public Rectangle (int x, int y, int angle) {
        super(x, y, angle);
        this.angle = 45;
        this.setSize(50, 50);
    }

    @Override
    public void updating() {
        // Update position and rotation
//        this.x += 5;
//        if(x >= 100){
//            x = 0;
//        }
//
//        this.angle += 5;
//        if (this.angle >= 360) {
//            this.angle -= 360;
//        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(angle), x + 100 / 2, y + 100 / 2);

        g2d.fillRect(x, y, 100, 100);

    }
}