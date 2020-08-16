package DinoText_GUI.TABLE_MODULE.TraitEditor;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Probabilities;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Model;
import DinoText_GUI.Util.TraitModel;

import java.util.ArrayList;


/*******************************************************************************
 * Editor Model
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Editor_Model
{
    private ArrayList<Creator_Model> traits = new ArrayList<>();

    private Table_Probabilities probabilities;
    private final int rowIndex;

    private Creator_Model activeModel;
    private int activeIndex = -1;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Editor_Model(Table_Probabilities probabilities, int row)
    {
        this.probabilities = probabilities;
        this.rowIndex = row;
    }

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Editor_Model(Trait[] traits, Table_Probabilities probabilities, int row)
    {
        this.probabilities = probabilities;
        this.rowIndex = row;

        for(int i = 0; i < traits.length; i++)
        {
            TraitModel nextTrait = new TraitModel(traits[i]);
            Creator_Model nextModel = new Creator_Model(nextTrait);

            nextModel.setProbabilities(this.probabilities);
            nextModel.setRowIndex(rowIndex);

            this.traits.add(i,nextModel);
        }

        if(this.traits.size() > 0)
        {
            this.activeModel = this.traits.get(0);
            this.activeIndex = 0;
        }
    }

    /***************************************************************************
     * Get Active Model
     **************************************************************************/
    public Creator_Model getActiveModel()
    {
        return this.activeModel;
    }

    /***************************************************************************
     * Get Trait Count
     **************************************************************************/
    public int getTraitCount()
    {
        return this.traits.size();
    }

    /***************************************************************************
     * Add Trait
     **************************************************************************/
    public void addTrait()
    {
        Creator_Model newTrait = new Creator_Model();
        newTrait.setProbabilities(this.probabilities);
        newTrait.setRowIndex(this.rowIndex);
        
        this.traits.add(traits.size(), newTrait);
        this.activeModel = this.traits.get(traits.size() - 1);
        this.activeIndex = traits.size() - 1;
    }

    /***************************************************************************
     * Remove Active Trait
     **************************************************************************/
    public void removeActiveTrait()
    {
        int currentIndex = this.activeIndex;
        traits.remove(activeIndex);

        if(this.traits.size() == 0)
        {
            this.activeModel = null;
            this.activeIndex = -1;
        }
        else if(currentIndex > 0)
        {
            //Default is to go one tab left if possible
            currentIndex -= 1;
            this.switchModel(currentIndex);
        }
        else
        {
            //Otherwise we go one tab to the right
            this.switchModel(currentIndex);
        }
    }

    /***************************************************************************
     * Get Traits
     **************************************************************************/
    public Trait[] getTraits()
    {
        Trait[] retVal = new Trait[this.traits.size()];

        for(int i = 0; i < this.traits.size(); i++)
        {
            retVal[i] = this.traits.get(i).getTrait();
        }

        return retVal;
    }

    /***************************************************************************
     * Get Trait Name At
     **************************************************************************/
    public String getTraitNameAt(int index)
    {
        return this.traits.get(index).getName();
    }

    /***************************************************************************
     * Switch Model
     **************************************************************************/
    public void switchModel(int index)
    {
        this.activeModel = this.traits.get(index);
        this.activeIndex = index;
    }
}
