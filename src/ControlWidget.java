import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlWidget {
    private JSlider slider;
    private JButton button;
    private JPanel widgetPanel;

    public ControlWidget(ActionListener actionListener){
        slider = new JSlider(0,100,50);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        button = new JButton("Start");
        button.addActionListener(actionListener);
        widgetPanel = new JPanel();
        widgetPanel.setPreferredSize(new Dimension(200,400));
        widgetPanel.add(slider, BorderLayout.CENTER);
        widgetPanel.add(button,BorderLayout.CENTER);
    }

    public JPanel getWidgetPanel() {
        return widgetPanel;
    }

    public int getSliderValue(){
        return slider.getValue();
    }
}
