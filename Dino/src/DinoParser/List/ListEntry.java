package DinoParser.List;

public class ListEntry
{
    private String listEntry;
    private double baseProbability;
    private int[] traitMap;
    private Trait[] traits;


    /***************************************************************************
     * ListEntry Constructor
     *
     * @param listEntry
     * @param baseProbability
     * @param traits
     **************************************************************************/
    public ListEntry(String listEntry, double baseProbability,
              Trait[] traits)
    {
        this.listEntry = listEntry;
        this.baseProbability = baseProbability;

        if(traits != null)
        {
            this.traits = new Trait[traits.length];

            for(int i = 0; i < traits.length; i++)
            {
                this.traits[i] = new Trait(traits[i]);
            }
        }

    }

    /***************************************************************************
     * ListEntry Constructor
     *
     * @param listEntry
     * @param baseProbability
     **************************************************************************/
    public ListEntry(String listEntry, double baseProbability)
    {
        this(listEntry,baseProbability,null);
    }

    /***************************************************************************
     * ListEntry
     *
     * @param other
     **************************************************************************/
    public ListEntry(ListEntry other)
    {
        this.listEntry = other.listEntry;
        this.baseProbability = other.baseProbability;

        if(other.traitMap != null)
        {
           this.traitMap = new int[other.traitMap.length];

            System.arraycopy(other.traitMap,0,
                    this.traitMap, 0, this.traitMap.length);

        }

        if(other.traits != null)
        {
            this.traits = new Trait[other.traits.length];

            for(int i = 0; i < this.traits.length; i++)
            {
                this.traits[i] = new Trait(other.traits[i]);
            }
        }
    }

    public void setBaseProbability(double baseProbability)
    {
        this.baseProbability = baseProbability;
    }

    public double getBaseProbability()
    {
        return this.baseProbability;
    }

    public void setTraits(Trait[] traits)
    {
        this.traits = traits;
    }


    /***************************************************************************
     * initializeTraits
     *
     * @param allTraits
     **************************************************************************/
    public void initializeTraits(String[] allTraits)
    {
        if(traits != null && allTraits != null)
        {
            this.traitMap = new int[this.traits.length];

            for(int i = 0; i < this.traitMap.length; i++)
            {
                for(int j = 0; j < allTraits.length; j++)
                {
                    if(allTraits[j].equals(this.traits[i].getName()))
                    {
                        this.traitMap[i] = j;
                        break;
                    }
                }
            }
        }
    }

    public double getUpdatedProbability(double[] allTraits)
    {
        if(this.traits == null)
        {
            return this.baseProbability;
        }

        double sum = 0;
        int count = 0;

        for(int i = 0; i < this.traits.length; i++)
        {
            int traitIndex = this.traitMap[i];

            double result = this.traits[i].getProbability(
                    allTraits[traitIndex]);

            if(result != 0)
            {
                sum += result;
                count++;
            }
        }

        if(count == 0)
        {
            return this.baseProbability;
        }
        else
        {
            return sum / count;
        }

    }


    /***************************************************************************
     * getListEntry
     *
     * @return
     **************************************************************************/
    public String getListEntry()
    {
        return this.listEntry;
    }

    public Trait[] getTraits()
    {
        if(this.traits == null)
            return null;

        Trait[] traits = new Trait[this.traits.length];

        System.arraycopy(this.traits, 0, traits,
                0, traits.length);

        return traits;
    }



    /***************************************************************************
     * toString
     *
     * @return
     **************************************************************************/
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("\"");
        builder.append(this.listEntry);
        builder.append("\"");
        builder.append(" | ");
        builder.append(" Base Probability: ");
        builder.append(baseProbability);
        builder.append(" | ");


        if(this.traits != null)
        {
            for (Trait trait : traits) {
                builder.append(trait.toString());
            }
        }

        return builder.toString();
    }

    public String toFileString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(this.listEntry);
        builder.append("\n");
        builder.append(baseProbability);
        builder.append(" ");

        if(this.traits != null)
        {
            for(Trait trait : traits)
            {
                builder.append(trait.toFileString());
            }
        }

        return builder.toString();
    }



}
