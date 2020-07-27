package DinoText_GUI.MODEL.Traits;

import DinoParser.List.Trait;

public class TraitModel
{
    private String name;

    private double lowerBound; //Represented as percentage
    private double upperBound; //Represented as percentage

    //Todo: Support custom scale?

    private double weight;

    private double value;

    TraitModel(String name, double lowerBound, double upperBound, double weight)
    {
        this.name = name;

        if(lowerBound <= upperBound)
        {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        this.weight = weight;
    }

    TraitModel(Trait trait)
    {
        this.name = trait.getName();
        this.upperBound = trait.getUpperBound();
        this.lowerBound = trait.getLowerBound();
        this.weight = trait.getProbability(this.lowerBound);
    }

    public Trait toTrait()
    {
        return new Trait(name, lowerBound, upperBound, weight);
    }

    public boolean inBounds()
    {
        return (this.value <= this.upperBound && this.value >= this.lowerBound);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


}
