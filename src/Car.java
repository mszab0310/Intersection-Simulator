import velocity.Point;
import velocity.Velocity;

import java.awt.*;

public class Car {
    private Point position;
    private int width;
    private int height;
    private Velocity velocity;
    private Color color;

    public Car(int x,int y,Velocity velocity){
        this.position = new Point(x,y);
        this.velocity = velocity;
        width = 40;
        height = 80;
        color = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(position.getX(),position.getY(),width,height);
    }

    public void  move(){
        position = velocity.next(position);
    }

    public boolean isInBounds(){
        return position.getX() < Dimensions.PANEL_WIDTH && position.getY() < Dimensions.PANEL_HEIGHT;
    }
}
