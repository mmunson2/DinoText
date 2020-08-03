// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.TraitSetWindow;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/*******************************************************************************
 * Trait Set Slider
 ******************************************************************************/
public class Trait_Set_Slider
{
    private JPanel panel;
    private JSlider slider;
    private JLabel traitName;
    private JLabel min;
    private JLabel max;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Trait_Set_Slider(String name, int min, int max, int value)
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

    /***************************************************************************
     * Get Panel
     **************************************************************************/
    public JPanel getPanel()
    {
        return panel;
    }

    /***************************************************************************
     * Get Value
     **************************************************************************/
    public int getValue() { return slider.getValue(); }

    /***************************************************************************
     * Set Value
     **************************************************************************/
    public void setValue(int value)
    {
        slider.setValue(value);
    }

    /***************************************************************************
     * Add Slider Listener
     **************************************************************************/
    public void addSliderListener(ChangeListener changeListener) { slider.addChangeListener(changeListener); }

    /***************************************************************************
     * Remove Slider Listener
     **************************************************************************/
    public void removeSliderListener(ChangeListener changeListener)
    {
        slider.removeChangeListener(changeListener);
    }
}