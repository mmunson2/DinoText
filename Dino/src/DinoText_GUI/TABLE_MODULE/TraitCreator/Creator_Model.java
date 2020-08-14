package DinoText_GUI.TABLE_MODULE.TraitCreator;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Probabilities;
import DinoText_GUI.Util.TraitModel;

public class Creator_Model
{
    public static final int MAX = 100;
    public static final int MIN = 0;


    TraitModel trait;
    Table_Probabilities probabilities;
    int rowIndex = -1;

    public Creator_Model()
    {
        trait = new TraitModel("New Trait", MIN, MAX, 0);
    }

    public Creator_Model(TraitModel trait)
    {
        this.trait = trait;
    }

    public void setName(String name)
    {
        this.trait.setName(name);
    }

    public void setLowerBound(double lowerBound)
    {
        if(lowerBound < this.trait.getUpperBound())
        {
            this.trait.setLowerBound(lowerBound);
        }
    }

    public void setUpperBound(double upperBound)
    {
        if(upperBound > this.trait.getLowerBound())
        {
            this.trait.setUpperBound(upperBound);
        }
    }

    public void setProbabilities(Table_Probabilities probabilities)
    {
        this.probabilities = new Table_Probabilities(probabilities);

        updateProbabilities();
    }

    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;

        updateProbabilities();
    }

    public void setWeight(double weight)
    {
        this.trait.setWeight(weight);

        updateProbabilities();
    }

    public int getLowerBound()
    {
        return (int) trait.getLowerBound();
    }

    public int getUpperBound()
    {
        return (int) trait.getUpperBound();
    }

    public double getWeight()
    {
        return trait.getWeight();
    }

    public String getName()
    {
        return trait.getName();
    }

    public double getProbability()
    {
        if(this.probabilities != null && this.rowIndex != -1)
            return this.probabilities.getProbability(this.rowIndex);
        else
            return -1;
    }

    public Trait getTrait()
    {
        return this.trait.toTrait();
    }

    private void updateProbabilities()
    {
        if(this.probabilities != null && this.rowIndex != -1)
        {
            this.probabilities.updateWeight(rowIndex, this.getWeight());
        }
    }
}
