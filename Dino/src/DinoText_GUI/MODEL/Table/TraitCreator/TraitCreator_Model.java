package DinoText_GUI.MODEL.Table.TraitCreator;

import DinoParser.List.Trait;
import DinoText_GUI.MODEL.Table.Table_Probabilities;
import DinoText_GUI.MODEL.Table.Traits.TraitModel;

public class TraitCreator_Model
{
    TraitModel trait;
    Table_Probabilities probabilities;
    int rowIndex = -1;

    public TraitCreator_Model()
    {
        trait = new TraitModel("name", 0, 100, 0);
    }

    public TraitCreator_Model(TraitModel trait)
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
