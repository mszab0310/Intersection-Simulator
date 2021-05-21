package velocity;

public class Velocity {
    private int x;
    private int y;

    public Velocity(Point pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public Point next(Point point) {
        return new Point(point.getX() + this.x, point.getY() + this.y);
    }
}
