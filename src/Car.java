import velocity.HorizontalVelocity;
import velocity.Point;
import velocity.Velocity;
import velocity.VerticalVelocity;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Car {
    private Point position;
    private int width;
    private int height;
    private Velocity velocity;
    private Color color;
    private int sleepTime;
    private char orientation;

    public Car(char orientation, int x, int y, int sleepTime) {
        this.orientation = orientation;
        this.position = new Point(x, y);
        this.sleepTime = sleepTime;
        if (orientation == 'v') {
            width = Dimensions.VERTICAL_CAR_WIDTH;
            height = Dimensions.VERTICAL_CAR_HEIGHT;
            this.velocity = new VerticalVelocity(1);
        } else {
            width = Dimensions.HORIZONTAL_CAR_WIDTH;
            height = Dimensions.HORIZONTAL_CAR_HEIGHT;
            this.velocity = new HorizontalVelocity(1);
        }
        color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

    public void move() {
        position = velocity.next(position);
    }

    public boolean isInBounds() {
        return position.getX() < Dimensions.PANEL_WIDTH && position.getY() < Dimensions.PANEL_HEIGHT;
    }

    public Point getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Velocity getVelocity() {
        return velocity;
    }


    public Color getColor() {
        return color;
    }

    public Optional<Car> getCloseCarInFront(int distance, List<Car> cars) {
        return cars.stream()
                .filter(car -> {
                    int verticalDist = car.getPosition().getY() - this.position.getY() - this.height;
                    int horizontalDist = car.getPosition().getX() - this.position.getX() - this.width;
                    return orientation == 'h' ? horizontalDist > 0 && horizontalDist < distance : verticalDist > 0 && verticalDist < distance;
                })
                .findFirst();
    }


    public boolean isCloseToIntersection(Point intersection) {
        return orientation == 'h' ? this.position.getX() >= intersection.getX() - 100 && this.position.getX() < intersection.getX() + 50 :
                this.getPosition().getY() + Dimensions.VERTICAL_CAR_HEIGHT >= intersection.getY() - 10;
    }

    public boolean isPastIntersection(Point intersection) {
        return this.position.getX() > intersection.getX() + 50;
    }

    public boolean checkPriority(Point intersection, List<Car> horizontalCars) {
        if (this.getPosition().getY() + Dimensions.VERTICAL_CAR_HEIGHT >= intersection.getY() - 100) {
            for (Car hcar : horizontalCars) {
                if (hcar.isCloseToIntersection(intersection)) {
                    return true;
                }
            }
        }
        return false;

    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setVelocityInt(int velocity) {
        this.velocity = orientation == 'h' ? new HorizontalVelocity(velocity) : new VerticalVelocity(velocity);
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void adjustSpeed(int distance, List<Car> cars) {
        Optional<Car> car = getCloseCarInFront(distance, cars);
        car.ifPresent(a -> {
            this.setVelocity(car.get().getVelocity());
            if (this.sleepTime < car.get().getSleepTime()) {
                this.setSleepTime(car.get().getSleepTime());
            }
        });
    }
}
