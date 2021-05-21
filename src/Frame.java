import velocity.Point;
import velocity.VerticalVelocity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Frame {
    private JFrame frame;
    private RoadPanel roadPanel;
    private JPanel sidePanel;
    private JPanel bottomPanel;
    private ControlWidget controlWidget;
    private List<Car> cars;



    public Frame() {
        frame = new JFrame("Intersection");
        cars = new ArrayList<>();
        roadPanel = new RoadPanel(cars);
        sidePanel = new JPanel();
        bottomPanel = new JPanel();
        controlWidget = new ControlWidget(a -> {
            Point position = new Point(100,10);
            if(cars.size() > 0 && cars.get(cars.size() - 1).getPosition().getY()  <= position.getY() + 130){
                return;
            }
            Car car = new Car(position.getX(),position.getY(),new VerticalVelocity(1),100 - controlWidget.getSliderValue());
            cars.add(car);
            Thread thread = new Thread(() -> {
                while (car.isInBounds()){
                    roadPanel.repaint();
                    try {
                        Thread.sleep(car.getSleepTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    car.move();
                    car.adjustSpeed(50,cars);
                }
                cars.remove(car);
            });
            thread.start();
        });

        sidePanel.setPreferredSize(new Dimension(200, Dimensions.PANEL_HEIGHT));
        sidePanel.add(controlWidget.getWidgetPanel(),BorderLayout.NORTH);
        bottomPanel.setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH, 200));
        roadPanel.setBackground(new Color(135, 130, 130));
        frame.add(roadPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setSize(Dimensions.FRAME_WIDTH, Dimensions.FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
