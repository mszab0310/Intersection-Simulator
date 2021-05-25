import java.awt.*;

public class Semaphore {
    private boolean isPresent;
    private boolean verticalRed;
    private boolean horizontalRed;

    public boolean isVerticalRed() {
        return verticalRed;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setVerticalRed(boolean verticalRed) {
        this.verticalRed = verticalRed;
    }

    public boolean isHorizontalRed() {
        return horizontalRed;
    }

    public void setHorizontalRed(boolean horizontalRed) {
        this.horizontalRed = horizontalRed;
    }

    public Semaphore() {
        isPresent = false;
        verticalRed = true;
        horizontalRed = false;
    }

    public void draw(Graphics g) {
        if (verticalRed) {
            g.setColor(Color.RED);
            g.fillRect(465, 365, 10, 10);
            g.setColor(Color.GREEN);
            g.fillRect(465, 425, 10, 10);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(465, 365, 10, 10);
            g.setColor(Color.RED);
            g.fillRect(465, 425, 10, 10);
        }
    }
}
