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
    private JSpinner spinner;

    public Trait_Setting_Slider(String name, int min, int max, int value)
    {
        this.traitName.setText(name);
        this.min.setText("" + min);
        this.max.setText("" + max);
        this.slider.setMinimum(min);
        this.slider.setMaximum(max);
        this.slider.setValue(value);
        this.spinner.setValue(value);
        this.panel.setMinimumSize(new Dimension(250, 70));
        this.panel.setMaximumSize(new Dimension(300, 70));
        this.panel.setPreferredSize(new Dimension(-1, 70));
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public int getSliderValue() { return slider.getValue(); }

    public int getSpinnerValue() { return (int) spinner.getValue(); }

    public void sliderListener(ChangeListener changeListener) { slider.addChangeListener(changeListener); }

    public void spinnerListener(ChangeListener changeListener) { spinner.addChangeListener(changeListener); }

    public void setSlider(int i) { slider.setValue(i); }

    public void setSpinner(int i) { spinner.setValue(i); }
}