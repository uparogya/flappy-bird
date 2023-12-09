import java.awt.*;
import java.awt.event.KeyEvent;

public class Bird extends Polygon implements java.awt.event.KeyListener{

    public static final int BIRD_WIDTH = 70;
    public static final int BIRD_HEIGHT = 70;
    public static double POSITION;
    private boolean moveStatus = false;
    private boolean initialAnimation;
    private int initialAnimationCount;
    public Bird(Point[] shape, Point position) {
        super(shape, position);
        this.initialAnimation = true;
        this.initialAnimationCount = 0;
    }

    private enum Movement {
        up,
        down,
        wave
    }

    private Movement current = Movement.wave;

    public void paint(Graphics brush, Color color) {
        Point[] points = getPoints();
        brush.setColor(color);

        // Default pipe //
        int[] xCoors = new int[points.length];
        int[] yCoors = new int[points.length];

        int i = 0;
        for (Point p : points) {
            xCoors[i] = (int) p.x;
            yCoors[i] = (int) p.y;
            i++;
        }
        brush.fillPolygon(xCoors,yCoors, points.length);
    }

    public boolean move() {
        POSITION = position.x;
        if (initialAnimation) {
//            if (initialAnimationCount < 100) {
//                position.y -= 2;
//                initialAnimationCount++;
//            }
        } else {
            switch (current) {
                case up:
                    position.y -= 10;
                    break;
                case down:
                    position.y += 4;
                    break;
                case wave:
                    for (int i = 0; i < 100; i++) {
                        if (i % 2 == 0) {
                            position.y += 4;
                        } else {
                            position.y -= 4;
                        }
                    }
                    break;
            }
        }
        return moveStatus;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        initialAnimation = false;
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            moveStatus = true;
            current = Movement.up;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            current = Movement.down;
        }
    }
}
