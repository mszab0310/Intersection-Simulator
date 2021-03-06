import velocity.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



public class Frame {
    private JFrame frame;
    private RoadPanel roadPanel;
    private JPanel sidePanel;
    private JPanel bottomPanel;
    private ControlWidget verticalControlWidget;
    private ControlWidget horizontalControlWidget;
    private List<Car> verticalCars;
    private List<Car> horizontalCars;
    private Point intersection;
    private JCheckBox semaphoreCheckBox;
    private Semaphore semaphore;
    private int semaphoreTime;



    public Frame() {
        frame = new JFrame("Intersection");
        verticalCars = new ArrayList<>();
        horizontalCars = new ArrayList<>();
        intersection = new Point(475,385);
        semaphore = new Semaphore();
        semaphoreTime = 0;
        roadPanel = new RoadPanel(verticalCars,horizontalCars,semaphore);
        sidePanel = new JPanel();
        bottomPanel = new JPanel();
        semaphoreCheckBox = new JCheckBox();
        semaphoreCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(semaphoreCheckBox.isSelected()){
                    semaphore.setPresent(true);
                    roadPanel.repaint();
                }else{
                    semaphore.setPresent(false);
                    roadPanel.repaint();
                }
            }
        });

        verticalControlWidget = new ControlWidget(a -> {
            int followDistance = 50;
            Point position = new Point(485,0);
            if(verticalCars.size() > 0 && verticalCars.get(verticalCars.size() - 1).getPosition().getY()  <= position.getY() + Dimensions.VERTICAL_CAR_HEIGHT+followDistance){
                return;
            }
            Car car = new Car('v',position.getX(),position.getY(),101 - verticalControlWidget.getSliderValue());
            verticalCars.add(car);
            Thread thread = new Thread(() -> {
                while (car.isInBounds()){
                    roadPanel.repaint();
                    try {
                        Thread.sleep(car.getSleepTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    car.move();
                    car.adjustSpeed(followDistance, verticalCars);
                    if(semaphore.isPresent()){
                        semaphoreTime += car.getSleepTime() / 100;
                        System.out.println(semaphoreTime);
                        if(semaphoreTime > 500 ){
                            semaphoreTime = 0;
                            semaphore.setVerticalRed(semaphore.isHorizontalRed());
                            semaphore.setHorizontalRed(!semaphore.isHorizontalRed());
                        }
                        if(semaphore.isVerticalRed() && car.isCloseToIntersection(intersection)){
                            car.setVelocityInt(0);
                        }else{
                            car.setVelocityInt(1);
                        }
                        car.adjustSpeed(followDistance,verticalCars);
                    }else {
                        if (car.checkPriority(intersection, horizontalCars)) {
                            if (car.isCloseToIntersection(intersection)) {
                                car.setVelocityInt(0);
                            }
                        } else {
                            car.setVelocityInt(1);
                        }
                    }
                    car.adjustSpeed(followDistance,verticalCars);
                }
                verticalCars.remove(car);
            });
            thread.start();

        });
        horizontalControlWidget = new ControlWidget(a -> {
            int followDistance = 50;
            Point position = new Point(0,385);
            if(horizontalCars.size() > 0 && horizontalCars.get(horizontalCars.size() - 1).getPosition().getX()  <= position.getX() + Dimensions.HORIZONTAL_CAR_WIDTH+followDistance){
                return;
            }
            Car car = new Car('h',position.getX(),position.getY(),101 - horizontalControlWidget.getSliderValue());
            horizontalCars.add(car);
            Thread thread = new Thread(() -> {
                while (car.isInBounds()){
                    roadPanel.repaint();
                    try {
                        Thread.sleep(car.getSleepTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(semaphore.isPresent()){
                        semaphoreTime += car.getSleepTime() / 10;
                        System.out.println(semaphoreTime);
                        if(semaphoreTime > 500 ){
                            semaphoreTime = 0;
                            semaphore.setVerticalRed(semaphore.isHorizontalRed());
                            semaphore.setHorizontalRed(!semaphore.isHorizontalRed());
                        }
                        if(semaphore.isHorizontalRed() && car.isCloseToIntersection(intersection)){
                            car.setVelocityInt(0);
                        }else{
                            car.setVelocityInt(1);
                        }
                        car.adjustSpeed(followDistance,horizontalCars);
                    }
                    car.move();
                    car.adjustSpeed(followDistance, horizontalCars);
                }
                horizontalCars.remove(car);
            });
            thread.start();
        });
        sidePanel.setPreferredSize(new Dimension(200, Dimensions.PANEL_HEIGHT));
        sidePanel.add(verticalControlWidget.getWidgetPanel(),BorderLayout.NORTH);
        bottomPanel.setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH, 200));
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.LINE_AXIS));
        bottomPanel.add(horizontalControlWidget.getWidgetPanel());
        bottomPanel.add(Box.createRigidArea(new Dimension(950,0)));
        sidePanel.add(new JLabel("Semaphore"));
        sidePanel.add(semaphoreCheckBox);
        roadPanel.setBackground(new Color(135, 130, 130));
        frame.add(roadPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setSize(Dimensions.FRAME_WIDTH, Dimensions.FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
