package DinoText;

import DinoParser.List.ListEntry;

import java.util.ArrayList;


/*******************************************************************************
 * List Prototype
 *
 * @author Matthew Munson
 * Date: 6/17/2020
 * @version 0.2-alpha
 *
 * A variable length list used in the creation of dynamic text lists.
 *
 ******************************************************************************/
public class DinoList
{
    private String name;
    private ArrayList<ListEntry> list;
    private boolean skipWrite = false;


    /***************************************************************************
     * Constructor
     *
     * A list is created by defining its name
     **************************************************************************/
    public DinoList(String name)
    {
        this.name = name;

        list = new ArrayList<>();
    }

    /***************************************************************************
     * setSkipWrite
     **************************************************************************/
    void setSkipWrite(boolean skipWrite)
    {
        this.skipWrite = skipWrite;
    }

    /***************************************************************************
     * skipWrite
     **************************************************************************/
    boolean skipWrite()
    {
        return skipWrite;
    }

    /***************************************************************************
     * add
     *
     * Simple wrapper for ArrayList functions
     **************************************************************************/
    public void add(String entry)
    {
        list.add(new ListEntry(entry, 1, null));
    }


    /***************************************************************************
     * getName
     *
     * Returns the name of the list
     **************************************************************************/
    public String getName()
    {
        return name;
    }

    /***************************************************************************
     * setName
     **************************************************************************/
    public void setName(String name) { this.name = name; }

    /***************************************************************************
     * size
     **************************************************************************/
    public int size()
    {
        return this.list.size();
    }

    /***************************************************************************
     * getEntry
     **************************************************************************/
    public ListEntry getEntry(int index)
    {
        if(index < 0 || index > this.list.size())
        {
            System.err.println("Error: Index " + index + " is out of bounds");
            RuntimeException e = new RuntimeException();
            e.printStackTrace();
            System.exit(-1);
        }

        return this.list.get(index);
    }

    /***************************************************************************
     * setProbability
     **************************************************************************/
    public void setProbability(int index, double probability)
    {
        if(index < 0 || index > this.list.size())
        {
            System.err.println("Error: Index " + index + " is out of bounds");
            RuntimeException e = new RuntimeException();
            e.printStackTrace();
            System.exit(-1);
        }

        this.list.get(index).setBaseProbability(probability);
    }

    /***************************************************************************
     * equals
     *
     * Checks two lists for equality, first by their names, then by their
     * contents.
     **************************************************************************/
    @Override
    public boolean equals(Object other)
    {
        if(other instanceof DinoList)
        {
            DinoList otherList = (DinoList) other;

            if(!this.name.equals(otherList.getName()))
                return false;

            return this.list.equals(otherList.list);
        }

        return false;
    }

    /***************************************************************************
     * hashCode
     *
     * Based on the list name. Overridden for use in a Set structure.
     **************************************************************************/
    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    /***************************************************************************
     * toString
     *
     * Warning: This toString is used to write the List to file. Changing
     * its order may require changes to the ListParser Class.
     **************************************************************************/
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Name: ");
        builder.append(this.name);
        builder.append("\n");

        builder.append("Size: ");
        builder.append(this.list.size());
        builder.append("\n");

        for (int i = 0; i < list.size(); i++) {
            builder.append(i + 1);
            builder.append(": ");
            builder.append(list.get(i).toFileString());
            builder.append("\n");
        }

        return builder.toString();

    }
}
