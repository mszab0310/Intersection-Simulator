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
            Car car = new Car(100,20,new VerticalVelocity(1),100 - controlWidget.getSliderValue());
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
                }
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
