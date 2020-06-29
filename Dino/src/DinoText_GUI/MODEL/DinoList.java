package DinoText_GUI.MODEL;

import java.util.ArrayList;


/*******************************************************************************
 * DinoList Prototype
 *
 * @author Matthew Munson
 * Date: 6/17/2020
 * @version 0.1
 *
 * A variable length list used in the creation of dynamic text lists.
 *
 ******************************************************************************/
public class DinoList
{
    private String name;
    private ArrayList<String> list;


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
     * add
     *
     * Simple wrapper for ArrayList functions
     **************************************************************************/
    public void add(String entry)
    {
        list.add(entry);
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
     * Warning: This toString is used to write the DinoList to file. Changing
     * its order may require changes to the ListParser Class.
     **************************************************************************/
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Name: ");
        builder.append(this.name);
        builder.append("\n");

        builder.append("Size: ");
        builder.append(this.list.size());
        builder.append("\n");

        for(int i = 0; i < list.size(); i++)
        {
            builder.append(i + 1);
            builder.append(": ");
            builder.append(list.get(i));
            builder.append("\n");
        }

        return builder.toString();
    }

}
