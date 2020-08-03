package DinoText_GUI.DISPLAY_MODULE.TraitSetWindow;

import Dino.Dino;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*******************************************************************************
 * Trait Set Controller
 ******************************************************************************/
public class Trait_Set_Controller
{
    private Trait_Set_Model model;
    private Trait_Set_View view;

    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 100;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Trait_Set_Controller(Dino dino)
    {
        this.model = new Trait_Set_Model(dino);
        this.view = new Trait_Set_View();

        for(int i = 0; i < this.model.getTraitCount(); i++)
        {
            String name = this.model.getTraitName(i);
            int startValue = (int) this.model.getTraitValue(i);

            this.view.addSlider(name, DEFAULT_MIN, DEFAULT_MAX, startValue);
        }

        this.view.addSliderListeners(new sliderListener());
        this.view.initialize();
    }

    /***************************************************************************
     * Is Visible
     **************************************************************************/
    public boolean isVisible()
    {
        return this.view.isVisible();
    }

    /***************************************************************************
     * Set Visible
     **************************************************************************/
    public void setVisible(boolean visible)
    {
        this.view.setVisible(visible);
    }

    /***************************************************************************
     * Inner Class - Slider Listener
     **************************************************************************/
    public class sliderListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int[] newValues = view.getSliderValues();

            model.setTraitValues(newValues);
            newValues = model.getTraitValues();

            view.setSliderValues(newValues);

            System.out.println("____________________");
            System.out.println("New Slider Values:");
            for(int i = 0; i < newValues.length; i++)
            {
                System.out.println(i + ": " + newValues[i]);
            }
        }
    }






}
