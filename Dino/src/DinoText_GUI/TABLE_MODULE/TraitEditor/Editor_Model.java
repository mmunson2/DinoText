package DinoText_GUI.TABLE_MODULE.TraitEditor;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;

import java.util.ArrayList;
import java.util.Collections;

public class Editor_Model
{
    private ArrayList<Trait> traits = new ArrayList<>();

    public Editor_Model()
    {

    }

    public Editor_Model(Trait[] traits)
    {
        Collections.addAll(this.traits, traits);
    }

    public int getTraitCount()
    {
        return this.traits.size();
    }

    public void addTrait(Trait trait)
    {
        this.traits.add(trait);
    }




}
