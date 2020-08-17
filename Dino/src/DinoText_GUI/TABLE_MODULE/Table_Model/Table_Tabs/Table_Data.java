package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs;

import Dino.List.ListEntry;
import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
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
 *
 * This class represents all of the data stored in the Entry and Design tab
 * tables. The Table_Model, DesignTab_Model, and EntryTab_Model all share
 * a reference to this class.
 ******************************************************************************/
public class Table_Data
{
    private int entryCount;
    private DinoList list;

    private File directory;
    private Table_Probabilities probabilities;

    private ArrayList<Boolean> emptyRows;

    //Just in case you want to modify what an "empty" cell is
    private static final String EMPTY_ENTRY_DEFAULT = "";
    private static final String EMPTY_ENTRY_NAME_DEFAULT = "";
    private static final double EMPTY_WEIGHT_DEFAULT = 0.0;

    private static final String DEFAULT_LIST_NAME = "Untitled List";

    /***************************************************************************
     * Constructor
     *
     * Creates an empty table with the default number of rows and the default
     * list name.
     **************************************************************************/
    public Table_Data()
    {
        this.list = new DinoList(DEFAULT_LIST_NAME);
        this.emptyRows = new ArrayList<>();
        this.probabilities = new Table_Probabilities();

        for(int i = 0; i < Table_Manager.DEFAULT_ROWS; i++)
        {
            this.addRow();
        }

    }

    /***************************************************************************
     * Add Row
     *
     * Adds an empty row to the end of the table.
     **************************************************************************/
    public void addRow()
    {
        this.entryCount++;

        int newIndex = this.entryCount - 1; //The index of the new entry

        this.list.add(EMPTY_ENTRY_DEFAULT);
        this.list.setEntryName(newIndex, EMPTY_ENTRY_NAME_DEFAULT);
        this.list.setProbability(newIndex, EMPTY_WEIGHT_DEFAULT);
        this.emptyRows.add(true);

        this.probabilities.addWeight(EMPTY_WEIGHT_DEFAULT);
    }

    /***************************************************************************
     * Add Trait
     *
     * Applies a trait to a given index.
     **************************************************************************/
    public void addTrait(int rowIndex, Trait newTrait)
    {
        this.list.addTrait(rowIndex, newTrait);
        updateEmptyStatus(rowIndex);
    }

    /***************************************************************************
     * Write To File
     *
     * Writes the current list to the working directory
     **************************************************************************/
    public void writeToFile()
    {
        DinoWriter writer = new DinoWriter();
        writer.writeListToFile(this.list);
    }

    /***************************************************************************
     * Write To File - File overload
     *
     * Writes the current list to the given directory.
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
     * Appends an entry and weight to the next empty row in the table.
     **************************************************************************/
    public void addEntry(int nextEmpty, String entry, double weight)
    {
        addEntry(nextEmpty, "", entry, weight, null);
    }

    /***************************************************************************
     * Add Entry - ListEntry overload
     *
     * Appends a ListEntry, which could include an entry name, entry, weight,
     * and trait array, to the next empty row in the table.
     **************************************************************************/
    public void addEntry(int nextEmpty, ListEntry listEntry)
    {
        addEntry(nextEmpty,
                 listEntry.getEntryName(),
                 listEntry.getListEntry(),
                 listEntry.getBaseProbability(),
                 listEntry.getTraits());
    }

    /***************************************************************************
     * Add Entry - Main Overload
     *
     * Appends all provided information to the next empty row in the table.
     *
     * - traits can be null
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
                addTrait(rowIndex, trait);
            }
        }

        updateEmptyStatus(rowIndex);
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
    public void setWeight(int rowIndex, double weight)
    {
        this.list.setProbability(rowIndex, weight);
        this.probabilities.updateWeight(rowIndex, weight);

        updateEmptyStatus(rowIndex);
    }

    /***************************************************************************
     * Set Entry Name
     *
     **************************************************************************/
    public void setEntryName(int rowIndex, String newName)
    {
        this.list.setEntryName(rowIndex, newName);

        updateEmptyStatus(rowIndex);
    }

    /***************************************************************************
     * setListEntry
     *
     **************************************************************************/
    public void setEntry(int rowIndex, String newEntry)
    {
        this.list.setEntry(rowIndex, newEntry);

        updateEmptyStatus(rowIndex);
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
     * Update Empty Status
     *
     **************************************************************************/
    private void updateEmptyStatus(int row)
    {
        String entryName = this.list.getEntryName(row);
        String entry = this.list.getEntry(row).getListEntry();
        double weight = this.list.getProbability(0);

        Trait[] traits = this.list.getTraits(row);
        boolean hasTraits = !(traits == null || traits.length == 0);

        if(entryName.equals(EMPTY_ENTRY_NAME_DEFAULT)
                && entry.equals(EMPTY_ENTRY_DEFAULT)
                && weight == EMPTY_WEIGHT_DEFAULT
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

        this.updateEmptyStatus(row);
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
