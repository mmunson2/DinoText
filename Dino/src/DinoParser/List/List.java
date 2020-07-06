package DinoParser.List;

import DinoParser.ListParser;

import java.util.ArrayList;

public class List
{
    private String name;

    private ListEntry[] list;
    private Distribution distribution;


    public List(ListParser parser)
    {
        this.name = parser.getName();
        this.list = parser.getList();

        double weights[] = new double[this.list.length];

        for(int i = 0; i < this.list.length; i++)
        {
            weights[i] = this.list[i].getBaseProbability();

        }

        this.distribution = new Distribution(weights);
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
        double[] weights = new double[this.list.length];

        for(int i = 0; i < this.list.length; i++)
        {
            weights[i] = this.list[i].getUpdatedProbability(allTraits);
        }


        this.distribution = new Distribution(weights);
    }


    public String getEntry()
    {
        int index = this.distribution.sample();

        return this.list[index].getListEntry();
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
