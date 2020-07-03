package DinoParser.List;

/***************************************************************************
 * Trait Class
 *
 * @author Matthew Munson
 * Date: 6/29/2020
 * @version 0.2-alpha
 **************************************************************************/
public class Trait
{
    private String name;
    private double lowerBound;
    private double upperBound;
    private double probability;

    /***************************************************************************
     * Trait Constructor
     *
     * @param name
     * @param lowerBound
     * @param upperBound
     * @param probability
     **************************************************************************/
    public Trait(String name, double lowerBound,
                 double upperBound, double probability)
    {
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.probability = probability;
    }

    /***************************************************************************
     * Trait Copy Constructor
     *
     * @param other
     **************************************************************************/
    public Trait(Trait other)
    {
        this.name = other.name;
        this.lowerBound = other.lowerBound;
        this.upperBound = other.upperBound;
        this.probability = other.probability;
    }

    /***************************************************************************
     * getProbability
     *
     * @param traitVal
     * @return
     **************************************************************************/
    public double getProbability(double traitVal)
    {
        if(traitVal > lowerBound && traitVal < upperBound)
        {
            return probability;
        }
        else
        {
            return 0;
        }
    }

    /***************************************************************************
     * getName
     *
     * @return
     **************************************************************************/
    public String getName()
    {
        return this.name;
    }


    /***************************************************************************
     * toString
     *
     * @return
     **************************************************************************/
    @Override
    public String toString()
    {
        String retVal = "";

        retVal += "Trait: " + this.name + " | ";
        retVal += "LowerBound: " + this.lowerBound + " | ";
        retVal += "UpperBound: " + this.upperBound + " | ";
        retVal += "Probability: " + this.probability;


        return retVal;
    }


    public String toFileString()
    {
        String retVal = "";

        retVal += this.name + ":";
        retVal += this.lowerBound + ":";
        retVal += this.upperBound + ":";
        retVal += this.probability + " ";

        return retVal;
    }





}
