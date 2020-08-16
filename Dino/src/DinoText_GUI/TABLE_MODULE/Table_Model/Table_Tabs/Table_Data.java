package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs;

import Dino.List.ListEntry;
import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.Util.DinoList;
import DinoText_GUI.Util.DinoWriter;

import java.io.File;
import java.util.ArrayList;

/*******************************************************************************
 * Table Data
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Table_Data
{
    private int entryCount;
    private DinoList list;

    private File directory;
    private Table_Probabilities probabilities;

    private ArrayList<Boolean> emptyRows;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Data()
    {
        this.list = new DinoList("Untitled List");
        this.emptyRows = new ArrayList<>();

        for(int i = 0; i < Table_Manager.DEFAULT_ROWS; i++)
        {
            this.emptyRows.add(true);
            this.list.add("");
            this.list.setEntryName(i, "");
            this.list.setProbability(i, 0);
        }

        this.entryCount = Table_Manager.DEFAULT_ROWS;

        probabilities = new Table_Probabilities();
    }

    /***************************************************************************
     * Add Row
     *
     **************************************************************************/
    public void addRow()
    {
        this.entryCount++;

        this.list.add("");
        this.list.setEntryName(this.entryCount - 1, "");
        this.emptyRows.add(true);
        this.list.setProbability(this.list.size() - 1, 0);

        this.probabilities.addWeight(0);
    }

    /***************************************************************************
     * Add Trait
     *
     **************************************************************************/
    public void addTrait(int rowIndex, Trait newTrait)
    {
        this.list.addTrait(rowIndex, newTrait);
        checkForEmptyRow(rowIndex);
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile()
    {
        DinoWriter writer = new DinoWriter();
        writer.writeListToFile(this.list);
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile(File directory)
    {
        DinoWriter writer = new DinoWriter();
        this.list.setDirectory(directory);
        writer.writeListToFile(this.list);
    }

    /***************************************************************************
     * Add Entry
     *
     **************************************************************************/
    public void addEntry(int nextEmpty, String entry, double weight)
    {
        this.addEntry(nextEmpty, "", entry, weight, null);
    }

    /***************************************************************************
     * Add Entry
     *
     **************************************************************************/
    public void addEntry(int nextEmpty, ListEntry listEntry)
    {
        this.addEntry(nextEmpty, listEntry.getEntryName(), listEntry.getListEntry(), listEntry.getBaseProbability(), listEntry.getTraits());
    }

    /***************************************************************************
     * Add Entry
     *
     **************************************************************************/
    public void addEntry(int nextEmpty, String entryName, String entry, double weight, Trait[] traits)
    {
        int rowIndex = nextEmpty;

        if(rowIndex == -1) //Need to add a row
        {
            this.addRow();
            rowIndex = this.entryCount - 1;
        }

        //Add to existing entry
        this.list.setEntryName(rowIndex, entryName);
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
     * Set Entry Name
     *
     **************************************************************************/
    public void setEntryName(int nextEmpty, int rowIndex, String newName)
    {
        if(!this.isEmpty(rowIndex)) //If the row wasn't blank, don't add a newline
        {
            this.list.setEntryName(rowIndex, newName);
        }
        else //If the row is blank and currentWeight 0, set the weight to 1
        {
            this.addEntry(nextEmpty, "", 1.0);
            this.list.setEntryName(nextEmpty, newName);
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
    public void setEntry(int nextEmpty, int rowIndex, String newEntry)
    {

        if(!isEmpty(rowIndex)) //If the row wasn't blank, don't add a newline
        {
            this.list.setEntry(rowIndex, newEntry);
        }
        else //If the row is blank and currentWeight 0, set the weight to 1
        {
            this.addEntry(nextEmpty, newEntry, 1.0);
            //Todo: Only do this if there aren't any traits
        }
    }

    /***************************************************************************
     * Is Empty
     *
     **************************************************************************/
    public boolean isEmpty(int row)
    {
        return this.emptyRows.get(row);
    }

    /***************************************************************************
     * Set Empty
     *
     **************************************************************************/
    private void setEmpty(int row)
    {
        this.emptyRows.set(row, true);
    }

    /***************************************************************************
     * Set Occupied
     *
     **************************************************************************/
    private void setOccupied(int row)
    {
        this.emptyRows.set(row, false);
    }

    /***************************************************************************
     * Check For Empty Row
     *
     **************************************************************************/
    private void checkForEmptyRow(int row)
    {
        String entryName = this.list.getEntryName(row);
        String entry = this.list.getEntry(row).getListEntry();
        double weight = this.list.getProbability(0);

        Trait[] traits = this.list.getTraits(row);

        boolean hasTraits = !(traits == null || traits.length == 0);

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

    /***************************************************************************
     *--------------------------------------------------------------------------
     *  ██████  ███████ ████████         ██     ███████ ███████ ████████ 
     * ██       ██         ██           ██      ██      ██         ██    
     * ██   ███ █████      ██          ██       ███████ █████      ██ 
     * ██    ██ ██         ██         ██             ██ ██         ██ 
     *  ██████  ███████    ██        ██         ███████ ███████    ██ 
     *--------------------------------------------------------------------------
     **************************************************************************/

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

}
