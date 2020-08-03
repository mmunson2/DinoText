// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Trait_Setting_Slider
{
    private JPanel panel;
    private JSlider slider;
    private JLabel traitName;
    private JLabel min;
    private JLabel max;

    public Trait_Setting_Slider(String name, int min, int max, int value)
    {
        this.traitName.setText(name);
        this.min.setText("" + min);
        this.max.setText("" + max);
        this.slider.setMinimum(min);
        this.slider.setMaximum(max);
        this.slider.setValue(value);
        this.panel.setMinimumSize(new Dimension(250, 50));
        this.panel.setMaximumSize(new Dimension(300, 50));
        this.panel.setPreferredSize(new Dimension(-1, 50));
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public int getValue() { return slider.getValue(); }

    public void sliderListener(ChangeListener changeListener) { slider.addChangeListener(changeListener); }
}