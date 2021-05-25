import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoadPanel extends JPanel {

    private List<Car> verticalCars;
    private List<Car> horizontalCars;
    private  Semaphore  semaphore;
    public RoadPanel(List<Car> verticalCars, List<Car> horizontalCars,Semaphore semaphore){
        this.semaphore = semaphore;
        setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH,Dimensions.PANEL_HEIGHT));
        this.verticalCars = verticalCars;
        this.horizontalCars = horizontalCars;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0,0,475,375);
        g.fillRect(525,0,475,375);
        g.fillRect(0,425,475,375);
        g.fillRect(525,425,475,375);
        if(semaphore.isPresent()){
            semaphore.draw(g);
        }
        verticalCars.forEach(car -> {
            car.draw(g);
        });
        horizontalCars.forEach(car->{
            car.draw(g);
        });

    }
}
