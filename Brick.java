import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Brick {
    private int XSIZE = 50;
    private int YSIZE = 15 ;

    private Ball ball;
    private JPanel panel;
    private Dimension dimension;
    private int x;
    private int y;
    private boolean isAlive;

    Graphics2D g2;
    private Color backgroundColor;

    public Brick(JPanel p, int X, int Y, Ball b){
        panel = p;
        Graphics g = panel.getGraphics ();
        g2 = (Graphics2D) g;
        backgroundColor = panel.getBackground ();

        ball = b;
        dimension = panel.getSize();
        x = X;
        y = Y;
        isAlive = true;
    }

    public void draw (Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.fill(new Rectangle2D.Double(x, y, XSIZE, YSIZE));
    }

    public void erase (Graphics2D g2) {
        g2.setColor (backgroundColor);
        g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
    }

    public boolean ballHitsBrick () {

        Rectangle2D.Double rectBall = ball.getBoundingRectangle();
        Rectangle2D.Double rectBrick = getBoundingRectangle();

        if (rectBall.intersects(rectBrick)) {
            System.out.println("Hit!");
            isAlive = false;
            return true;
        }else
            return false;
    }

    public boolean checkStatus(){
        return isAlive;
    }
}
