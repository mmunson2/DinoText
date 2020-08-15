package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs;

import Dino.List.ListEntry;
import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.Util.DinoList;
import DinoText_GUI.Util.DinoWriter;

import java.io.File;
import java.util.ArrayList;

public class Table_Data
{
    private int entryCount;
    private DinoList list;

    private File directory;
    private Table_Probabilities probabilities;

    private ArrayList<Boolean> emptyRows;

    public Table_Data()
    {
        this.list = new DinoList("Untitled List");
        this.emptyRows = new ArrayList<>();

        for(int i = 0; i < Table_Manager.DEFAULT_ROWS; i++)
        {
            this.emptyRows.add(true);
            this.list.add("");
            this.list.addEntryName("");
            this.list.setProbability(i, 0);
        }

        this.entryCount = Table_Manager.DEFAULT_ROWS;

        probabilities = new Table_Probabilities();
    }

    /***************************************************************************
     * addRow
     *
     **************************************************************************/
    public void addRow()
    {
        this.entryCount++;

        this.list.add("");
        this.emptyRows.add(true);
        this.list.setProbability(this.list.size() - 1, 0);

        this.probabilities.addWeight(0);
    }

    public void setName(String listName)
    {
        this.list.setName(listName);
    }

    public void setDirectory(File directory)
    {
        this.directory = directory;
    }

    public void setTraits(int row, Trait[] traits)
    {
        this.list.setTraits(row, traits);
        checkForEmptyRow(row);
    }

    public String getName()
    {
        return this.list.getName();
    }

    public DinoList getList()
    {
        return this.list;
    }

    public int getEntryCount()
    {
        return this.entryCount;
    }


    public File getDirectory()
    {
        return this.directory;
    }

    public Trait[] getTraitArray(int rowIndex)
    {
        return this.list.getTraits(rowIndex);
    }

    public Table_Probabilities getProbabilities()
    {
        return this.probabilities;
    }

    public void addTrait(int rowIndex, Trait newTrait)
    {
        this.list.addTrait(rowIndex, newTrait);
        checkForEmptyRow(rowIndex);
    }

    public void writeToFile()
    {
        DinoWriter writer = new DinoWriter();
        writer.writeListToFile(this.list);
    }

    public void writeToFile(File directory)
    {
        DinoWriter writer = new DinoWriter();
        this.list.setDirectory(directory);
        writer.writeListToFile(this.list);
    }

    public void addEntry(int nextEmpty, String entry, double weight)
    {
        this.addEntry(nextEmpty, entry, weight, null);
    }

    public void addEntry(int nextEmpty, ListEntry listEntry)
    {
        this.addEntry(nextEmpty, listEntry.getListEntry(), listEntry.getBaseProbability(), listEntry.getTraits());
    }

    /***************************************************************************
     * addEntry
     *
     **************************************************************************/
    public void addEntry(int nextEmpty, String entry, double weight, Trait[] traits)
    {
        int rowIndex = nextEmpty;

        if(rowIndex == -1) //Need to add a row
        {
            this.addRow();
            rowIndex = this.entryCount - 1;
        }

        //Add to existing entry
        this.list.setEntry(rowIndex, entry);
        this.list.setProbability(rowIndex, weight);

        this.probabilities.updateWeight(rowIndex, weight);

        if(traits != null)
        {
            for(Trait trait : traits)
            {
                this.addTrait(rowIndex, trait);
            }
        }

        checkForEmptyRow(rowIndex);
    }

    /***************************************************************************
     * setWeight
     *
     * Logic for setting the weight of a given row.
     *
     * Behavior:
     * • If a user sets the weight of a random row in the table, use
     * addEntry() to move the new row to the top of the table.
     * • If a user sets the weight of any other row, just update the value.
     *
     **************************************************************************/
    public void setWeight(int nextEmpty, int rowIndex, double weight)
    {
        if(this.isEmpty(rowIndex))
        {
            addEntry(nextEmpty, "", weight);
        }
        else
        {
            this.list.setProbability(rowIndex, weight);
            this.probabilities.updateWeight(rowIndex, weight);
            checkForEmptyRow(rowIndex);
        }
    }

    /***************************************************************************
     * setListEntry
     *
     * Making a change to the list entry logic? Congratulations! You've been
     * chosen for the setListEntry test program:
     *
     * 1) Create a list in a blank row where you'd expect the entry to go
     * 2) Create a list in a blank row out of order
     * 3) Set a list entry that has text so that it's blank
     * 4) Set a blank list entry so that it stays blank
     * 5) Repeat #4 in an out-of-order position
     * 6) Set the probability of a row first, then set its entry
     *
     **************************************************************************/
    public void setListEntry(int nextEmpty, int rowIndex, double currentWeight, String currentEntry, String newEntry)
    {
        if(newEntry.equals("")) //Setting a row to blank also unsets probability
        {
            this.list.setEntry(rowIndex, newEntry);
            this.list.setProbability(rowIndex, 0);
            //Todo: update the table so it stays ordered
        }
        else if(!currentEntry.equals("")) //If the row wasn't blank, don't add a newline
        {
            this.list.setEntry(rowIndex, newEntry);
        }
        else if(currentWeight == 0) //If the row is blank and currentWeight 0, set the weight to 1
        {
            this.addEntry(nextEmpty, newEntry, 1.0);
            //Todo: Only do this if there aren't any traits
        }
        else //If the probability was already set, don't mess with it
        {
            this.list.setEntry(rowIndex, newEntry);
        }
    }

    public boolean isEmpty(int row)
    {
        return this.emptyRows.get(row);
    }

    private void setEmpty(int row)
    {
        this.emptyRows.set(row, true);
    }

    private void setOccupied(int row)
    {
        this.emptyRows.set(row, false);
    }

    private void checkForEmptyRow(int row)
    {
        String entryName = this.list.getEntryName(row);
        String entry = this.list.getEntry(row).getListEntry();
        double weight = this.list.getProbability(0);
        boolean hasTraits = (this.list.getTraits(row).length > 0);

        if(entryName.equals("") && entry.equals("") && weight == 0
            && !hasTraits)
        {
            setEmpty(row);
        }
        else
        {
            setOccupied(row);
        }
    }

    private void checkForEmptyRows()
    {
        for(int i = 0; i < this.entryCount; i++)
        {
            checkForEmptyRow(i);
        }
    }





}
