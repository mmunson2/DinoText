package DinoParser.List;

import DinoParser.ListParser;

/*******************************************************************************
 * List
 *
 * This class represents a List of ListEntries, each of which can contain a
 * list of traits.
 *
 * @author Matthew Munson
 * Date: 6/29/2020
 * @version 0.25-alpha
 ******************************************************************************/
public class List
{
    private String name;

    private ListEntry[] list;
    private Distribution distribution;

    /***************************************************************************
     * List Constructor
     *
     * @since 0.25-alpha
     **************************************************************************/
    public List(ListParser parser)
    {
        this.name = parser.getName();
        this.list = parser.getList();

        double[] weights = new double[this.list.length];

        for(int i = 0; i < this.list.length; i++)
        {
            weights[i] = this.list[i].getBaseProbability();

        }

        this.distribution = new Distribution(weights);
    }


    /***************************************************************************
     * getName
     *
     * @since 0.25-alpha
     **************************************************************************/
    public String getName()
    {
        return this.name;
    }


    /***************************************************************************
     * initializeTraits
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void initializeTraits(String[] allTraits)
    {
        for(int i = 0; i < this.list.length; i++)
        {
            this.list[i].initializeTraits(allTraits);
        }
    }

    /***************************************************************************
     * updateTraits
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void updateTraits(double[] allTraits)
    {
        double[] weights = new double[this.list.length];

        for(int i = 0; i < this.list.length; i++)
        {
            weights[i] = this.list[i].getUpdatedProbability(allTraits);
        }


        this.distribution = new Distribution(weights);
    }


    /***************************************************************************
     * getEntry
     *
     * @since 0.25-alpha
     **************************************************************************/
    public String getEntry()
    {
        int index = this.distribution.sample();

        return this.list[index].getListEntry();
    }

    /***************************************************************************
     * toString
     *
     * @since 0.25-alpha
     **************************************************************************/
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
