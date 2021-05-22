import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoadPanel extends JPanel {

    private List<Car> verticalCars;
    private List<Car> horizontalCars;
    public RoadPanel(List<Car> verticalCars, List<Car> horizontalCars){
        setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH,Dimensions.PANEL_HEIGHT));
        this.verticalCars = verticalCars;
        this.horizontalCars = horizontalCars;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        verticalCars.forEach(car -> {
            car.draw(g);
        });
        horizontalCars.forEach(car->{
            car.draw(g);
        });

    }
}
