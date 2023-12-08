public abstract class Polygon implements Shape {
    private Point[] shape;
    public Point position;

    public Polygon(Point[] shape, Point position) {
        this.shape = shape;
        this.position = position;


        Point origin = shape[0].clone();

        for (Point p : shape) {
            if (p.x < origin.x) origin.x = p.x;
            if (p.y < origin.y) origin.y = p.y;
        }

        for (Point p : shape) {
            p.x -= origin.x;
            p.y -= origin.y;
        }
    }

    public Point[] getPoints() {
        Point center = findCenter();
        Point[] points = new Point[shape.length];
        for (int i = 0; i < shape.length; i++) {
            Point p = shape[i];
            double x = ((p.x-center.x) * Math.cos(Math.toRadians(0)))
                    - ((p.y-center.y) * Math.sin(Math.toRadians(0)))
                    + center.x/2 + position.x;
            double y = ((p.x-center.x) * Math.sin(Math.toRadians(0)))
                    + ((p.y-center.y) * Math.cos(Math.toRadians(0)))
                    + center.y/2 + position.y;
            points[i] = new Point(x,y);
        }
        return points;
    }

    private Point findCenter() {
        Point sum = new Point(0,0);
        for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
            sum.x += (shape[i].x + shape[j].x)
                    * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
            sum.y += (shape[i].y + shape[j].y)
                    * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
        }
        double area = findArea();
        return new Point(Math.abs(sum.x/(6*area)),Math.abs(sum.y/(6*area)));
    }

    private double findArea() {
        double sum = 0;
        for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
            sum += shape[i].x*shape[j].y-shape[j].x*shape[i].y;
        }
        return Math.abs(sum/2);
    }

    public boolean contains(Point point) {
        Point[] points = getPoints();
        double crossingNumber = 0;
        for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
            if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
                    ((points[j].x < point.x) && (point.x <= points[i].x))) &&
                    (point.y > points[i].y + (points[j].y-points[i].y)/
                            (points[j].x - points[i].x) * (point.x - points[i].x))) {
                crossingNumber++;
            }
        }
        return crossingNumber%2 == 1;
    }

    public boolean collision (Polygon bird){
        Point[] points = bird.getPoints();
        for(Point p : points){
            if(this.contains(p)){
                return true;
            }
        }
        return false;
    }
}