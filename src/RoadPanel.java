import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoadPanel extends JPanel {

    private List<Car> cars;
    public RoadPanel(List<Car> cars){
        setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH,Dimensions.PANEL_HEIGHT));
        this.cars = cars;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        cars.forEach(car -> {
            car.draw(g);
        });

    }
}
