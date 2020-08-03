// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.TraitSetWindow;

import Dino.Dino;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

/*******************************************************************************
 * Trait Set View
 ******************************************************************************/
public class Trait_Set_View extends JFrame
{
    private  JPanel content;

    private ArrayList<Trait_Set_Slider> sliders;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Trait_Set_View()
    {
        this.setContentPane(content);
        this.setAlwaysOnTop(true);
        this.setSize(300, 300);
        this.setTitle("Trait Settings");

        this.sliders = new ArrayList<>();
    }

    /***************************************************************************
     * Add Slider
     **************************************************************************/
    public void addSlider(String name, int min, int max, int startValue)
    {
        Trait_Set_Slider tss = new Trait_Set_Slider(name, 0, 100, startValue);
        this.sliders.add(tss);
    }

    /***************************************************************************
     * Initialize
     **************************************************************************/
    public void initialize()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setPreferredSize(new Dimension(300, 300));

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        for (int i = 0; i < this.sliders.size(); i++)
        {
            panel.add(this.sliders.get(i).getPanel());
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        //panel.add(Box.createVerticalGlue());
        this.content.add(scroller, BorderLayout.CENTER);
    }

    /***************************************************************************
     * Get Slider Values
     **************************************************************************/
    public int[] getSliderValues()
    {
        int[] retVal = new int[this.sliders.size()];

        for(int i = 0; i < this.sliders.size(); i++)
        {
            retVal[i] = this.sliders.get(i).getValue();
        }

        return retVal;
    }

    /***************************************************************************
     * Set Slider Values
     **************************************************************************/
    public void setSliderValues(int[] sliderValues)
    {
        assert(sliderValues != null);
        assert(sliderValues.length == this.sliders.size());

        for(int i = 0; i < this.sliders.size(); i++)
        {
            this.sliders.get(i).setValue(sliderValues[i]);
        }
    }

    /***************************************************************************
     * Add Slider Listeners
     **************************************************************************/
    public void addSliderListeners(ChangeListener listener)
    {
        for(Trait_Set_Slider slider : sliders)
        {
            slider.addSliderListener(listener);
        }
    }

    /***************************************************************************
     * Remove Slider Listeners
     **************************************************************************/
    public void removeSliderListeners(ChangeListener listener)
    {
        for(Trait_Set_Slider slider : sliders)
        {
            slider.removeSliderListener(listener);
        }
    }
}
