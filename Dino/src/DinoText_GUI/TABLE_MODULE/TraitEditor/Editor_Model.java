package DinoText_GUI.TABLE_MODULE.TraitEditor;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Model;
import DinoText_GUI.Util.TraitModel;

import java.util.ArrayList;
import java.util.Collections;

public class Editor_Model
{
    private ArrayList<Creator_Model> traits = new ArrayList<>();

    private Creator_Model activeModel;

    public Editor_Model()
    {

    }

    public Creator_Model getActiveModel()
    {
        return this.activeModel;
    }

    public Editor_Model(Trait[] traits)
    {
        for(int i = 0; i < traits.length; i++)
        {
            this.traits.add(i,new Creator_Model(new TraitModel(traits[i])));
        }

        if(this.traits.size() > 0)
        {
            this.activeModel = this.traits.get(0);
        }
    }

    public int getTraitCount()
    {
        return this.traits.size();
    }

    public void addTrait()
    {
        this.traits.add(traits.size(), new Creator_Model());
        this.activeModel = this.traits.get(traits.size() - 1);
    }

    public void deleteTrait(int traitIndex)
    {
        int traitCount = this.getTraitCount();

        if(traitCount == 1)
        {
            this.traits.remove(0);
            this.activeModel = null;
        }
        else
        {
            this.traits.remove(traitIndex);




        }


    }

    public Trait[] getTraits()
    {
        Trait[] retVal = new Trait[this.traits.size()];

        for(int i = 0; i < this.traits.size(); i++)
        {
            retVal[i] = this.traits.get(i).getTrait();
        }

        return retVal;
    }

    public String getTraitNameAt(int index)
    {
        return this.traits.get(index).getName();
    }

    public void switchModel(int index)
    {
        this.activeModel = this.traits.get(index);
    }
}
