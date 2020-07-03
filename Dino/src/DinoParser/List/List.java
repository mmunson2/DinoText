package DinoParser.List;

import DinoParser.ListParser;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public class List
{
    private String name;

    private ListEntry[] list;
    private EnumeratedDistribution<String> distribution;


    public List(ListParser parser)
    {
        this.name = parser.getName();
        this.list = parser.getList();

        java.util.List<Pair<String, Double>> pairs = new ArrayList<>();

        boolean nonZero = false;

        for(int i = 0; i < this.list.length; i++)
        {
            Pair<String, Double> nextPair =
                    new Pair<String, Double>(this.list[i].getListEntry(),
                            this.list[i].getBaseProbability());

            pairs.add(nextPair);

            if(this.list[i].getBaseProbability() != 0)
                nonZero = true;
        }

        if(!nonZero) //Can't have all zeros in EnumeratedDistribution
        {
            pairs.add(new Pair<String, Double>("NO_ACTIVE", 1.0));
        }


        this.distribution = new EnumeratedDistribution<>(pairs);
    }


    public String getName()
    {
        return this.name;
    }

    public void initializeTraits(String[] allTraits)
    {
        for(int i = 0; i < this.list.length; i++)
        {
            this.list[i].initializeTraits(allTraits);
        }
    }

    public void updateTraits(double[] allTraits)
    {
        java.util.List<Pair<String, Double>> pairs = new ArrayList<>();

        boolean nonZero = false;

        for(int i = 0; i < this.list.length; i++)
        {
            double probability = this.list[i].getUpdatedProbability(allTraits);

            if(probability != 0)
            {
                nonZero = true;
            }

            String entry = this.list[i].getListEntry();

            Pair<String, Double> nextPair =
                    new Pair<String, Double>(entry,probability);

            pairs.add(nextPair);

        }

        if(!nonZero) //Can't have all zeros in EnumeratedDistribution
        {
            pairs.add(new Pair<String, Double>("NO_ACTIVE", 1.0));
        }


        this.distribution = new EnumeratedDistribution<>(pairs);
    }




    public String getEntry()
    {
        return this.distribution.sample();
    }




    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < list.length; i++)
        {
            builder.append(list[i].toString());
        }

        return builder.toString();
    }

}
