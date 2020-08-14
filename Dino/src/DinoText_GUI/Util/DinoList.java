package DinoText_GUI.Util;

import Dino.List.ListEntry;
import Dino.List.Trait;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


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
    private File directory;
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

    public void setDirectory(File directory)
    {
        this.directory = directory;
    }

    /***************************************************************************
     * skipWrite
     *
     * @since 0.25-alpha
     **************************************************************************/
    public boolean skipWrite()
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
     * addTrait
     *
     * Adds a trait to a list entry
     *
     * @since 0.5-beta
     **************************************************************************/
    public void addTrait(int index, Trait trait)
    {
        ListEntry listEntry = this.list.get(index);
        ArrayList<Trait> currentTraits = getTraitsAsList(index);

        if(currentTraits == null)
        {
            currentTraits = new ArrayList<>();
        }

        currentTraits.add(trait);

        setTraits(index, currentTraits);
    }

    public void setTraits(int index, Trait[] traits)
    {
        ArrayList<Trait> newTraits = new ArrayList<>();

        Collections.addAll(newTraits, traits);

        setTraits(index, newTraits);
    }

    /***************************************************************************
     * setTrait
     *
     * Sets a trait, using the name it had before in case it was changed,
     * to a new Trait.
     *
     * @since 0.5-beta
     **************************************************************************/
    public void setTrait(int index, String oldTraitName, Trait trait)
    {
        ArrayList<Trait> currentTraits = getTraitsAsList(index);

        int targetIndex = -1;

        for(int i = 0; i < currentTraits.size(); i++)
        {
            if(currentTraits.get(i).getName().equals(oldTraitName))
            {
                targetIndex = i;
                break;
            }
        }

        if(targetIndex == -1)
        {
            System.err.println("Error in DinoList, Trait " + oldTraitName + " does not exist.");
        }
        else
        {
            currentTraits.set(targetIndex, trait);
        }

        setTraits(index, currentTraits);
    }

    /***************************************************************************
     * deleteTrait
     *
     * Removes a trait from a ListEntry
     *
     * @since 0.5-beta
     **************************************************************************/
    public void deleteTrait(int index, String traitName)
    {
        ArrayList<Trait> currentTraits = getTraitsAsList(index);

        int targetIndex = -1;
        for(int i = 0; i < currentTraits.size(); i++)
        {
            if(currentTraits.get(i).getName().equals(traitName))
            {
                targetIndex = i;
                break;
            }
        }

        currentTraits.remove(targetIndex);

        setTraits(index, currentTraits);
    }

    public String getDirectoryString()
    {
        if(this.directory == null)
        {
            return "";
        }
        else
        {
            return this.directory.getAbsolutePath();
        }
    }

    public File getDirectory()
    {
        return this.directory;
    }

    /***************************************************************************
     * getTraits
     *
     * Returns the Trait Array
     *
     * @since 0.5-beta
     **************************************************************************/
    public Trait[] getTraits(int index)
    {
        return this.list.get(index).getTraits();
    }

    private ArrayList<Trait> getTraitsAsList(int index)
    {
        ListEntry listEntry = this.list.get(index);
        Trait[] currentTraitArray = listEntry.getTraits();

        if(currentTraitArray == null)
        {
            return null;
        }

        return new ArrayList(Arrays.asList(currentTraitArray));
    }

    private void setTraits(int index, ArrayList<Trait> traits)
    {
        ListEntry listEntry = this.list.get(index);

        listEntry.setTraits(traits.toArray(new Trait[0]));
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
