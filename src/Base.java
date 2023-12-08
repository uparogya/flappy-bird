import java.awt.*;

public class Base extends Polygon{
    public Base(Point[] shape, Point position) {
        super(shape, position);
    }

    @Override
    public void paint(Graphics brush, Color color) {
        Point[] points = getPoints();
        brush.setColor(color);

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

    @Override
    public boolean move() {
        return false;
    }
}
