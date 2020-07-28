// Camden Brewster

package DinoText_GUI.VIEW.Display;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class Trait_Setting_Slider
{
    private JPanel panel;
    private JSlider slider;
    private JLabel traitName;
    private JLabel min;
    private JLabel max;

    public Trait_Setting_Slider(String name, int min, int max)
    {
        this.traitName.setText(name);
        this.min.setText("" + min);
        this.max.setText("" + max);

        this.slider.setMinimum(min);
        this.slider.setMaximum(max);
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public int getValue() { return slider.getValue(); }

    public int getMax() { return slider.getMaximum(); }

    public void sliderListener(ChangeListener changeListener) { slider.addChangeListener(changeListener); }

}