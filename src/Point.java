public class Point implements Cloneable {
    double x,y;
    public Point(double x, double y)
    {
        this.x = x; this.y = y;
    }
    public Point clone() {
        return new Point(x, y);
    }
}