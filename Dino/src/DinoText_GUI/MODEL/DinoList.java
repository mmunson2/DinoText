package DinoText_GUI.MODEL;

import DinoParser.List.ListEntry;

import java.util.ArrayList;


/*******************************************************************************
 * List Prototype
 *
 * @author Matthew Munson
 * Date: 6/17/2020
 * @version 0.25-alpha
 *
 * This class is used in the creation of a List to be written to file. It
 * differs from the DinoParser List class primarily in that it can expand
 * dynamically.
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
     *
     * @since 0.25-alpha
     **************************************************************************/
    public DinoList(String name)
    {
        this.name = name;

        list = new ArrayList<>();
    }

    /***************************************************************************
     * setSkipWrite
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void setSkipWrite(boolean skipWrite)
    {
        this.skipWrite = skipWrite;
    }

    /***************************************************************************
     * skipWrite
     *
     * @since 0.25-alpha
     **************************************************************************/
    boolean skipWrite()
    {
        return skipWrite;
    }

    /***************************************************************************
     * add
     *
     * Simple wrapper for ArrayList functions
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void add(String entry)
    {
        list.add(new ListEntry(entry, 1, null));
    }


    /***************************************************************************
     * setEntry
     *
     * Allows editing of list contents
     *
     * @since 0.3-alpha
     **************************************************************************/
    public void setEntry(int index, String entry)
    {
        ListEntry listEntry = this.list.get(index);
        listEntry.setListEntry(entry);
    }

    public double getProbability(int index)
    {
        return this.list.get(index).getBaseProbability();
    }


    /***************************************************************************
     * getName
     *
     * Returns the name of the list
     *
     * @since 0.25-alpha
     **************************************************************************/
    public String getName()
    {
        return name;
    }

    /***************************************************************************
     * setName
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void setName(String name) { this.name = name; }

    /***************************************************************************
     * size
     *
     * @since 0.25-alpha
     **************************************************************************/
    public int size()
    {
        return this.list.size();
    }

    /***************************************************************************
     * getEntry
     *
     * @since 0.25-alpha
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
     *
     * @since 0.25-alpha
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
     *
     * @since 0.25-alpha
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
     *
     * @since 0.25-alpha
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
     *
     * @since 0.25-alpha
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
