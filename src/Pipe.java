import java.awt.*;

public class Pipe extends Polygon{
    public Pipe(Point[] shape, Point position) {
        super(shape, position);
    }

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
        position.x -= 2;
        scoreCheck();
        if(position.x < -100){
            return true;
        }
        return false;
    }

    public boolean scoreCheck() {
        if(position.x - Bird.POSITION == 1){
            FlappyBird.NEW_SCORE++;
        }
        return true;
    }

}
