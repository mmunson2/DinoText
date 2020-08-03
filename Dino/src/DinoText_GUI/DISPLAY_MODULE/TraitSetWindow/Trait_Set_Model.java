package DinoText_GUI.DISPLAY_MODULE.TraitSetWindow;

import Dino.Dino;

import java.util.Arrays;

/*******************************************************************************
 * Trait Set Model
 ******************************************************************************/
public class Trait_Set_Model
{
    private Dino dino;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Trait_Set_Model(Dino dino)
    {
        this.dino = dino;
    }

    /***************************************************************************
     * Get Trait Count
     **************************************************************************/
    public int getTraitCount()
    {
        return this.dino.getTraitCount();
    }

    /***************************************************************************
     * Get Trait Name
     **************************************************************************/
    public String getTraitName(int index)
    {
        return this.dino.getTraitName(index);
    }

    /***************************************************************************
     * Get Trait Value
     **************************************************************************/
    public double getTraitValue(int index)
    {
        return this.dino.getTraitValue(index);
    }

    /***************************************************************************
     * Get Trait Values
     **************************************************************************/
    public int[] getTraitValues()
    {
        double[] doubles = this.dino.getTraitValues();
        int[] retVal = new int[doubles.length];

        for(int i = 0; i < doubles.length; i++)
        {
            retVal[i] = (int) doubles[i];
        }

        return retVal;
    }

    /***************************************************************************
     * Set Trait Values
     **************************************************************************/
    public void setTraitValues(int[] values)
    {
        double[] doubles = new double[values.length];

        for(int i = 0 ; i < values.length; i++)
        {
           doubles[i] = values[i];
        }

        this.dino.setTraitValues(doubles);
    }
}
