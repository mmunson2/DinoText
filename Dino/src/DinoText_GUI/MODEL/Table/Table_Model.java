package DinoText_GUI.MODEL.Table;


import DinoParser.List.Trait;
import DinoText_GUI.MODEL.DinoList;
import DinoText_GUI.MODEL.DinoWriter;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/*******************************************************************************
 * Table_Model
 *
 ******************************************************************************/
public class Table_Model extends AbstractTableModel
{
    public static final int DEFAULT_ROWS = 4;
    private int entryCount;

    DinoList list;
    Table_Probabilities probabilities;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Model()
    {
        this.list = new DinoList("Untitled List");

        for(int i = 0; i < DEFAULT_ROWS; i++)
        {
            this.list.add("");
            this.list.setProbability(i, 0);
        }

        this.entryCount = DEFAULT_ROWS;

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
        this.list.setProbability(this.list.size() - 1, 0);

        this.probabilities.addWeight(0);
        this.fireTableDataChanged();
    }

    /***************************************************************************
     * setName
     *
     **************************************************************************/
    public void setName(String listName)
    {
        this.list.setName(listName);
    }

    /***************************************************************************
     * getName
     *
     **************************************************************************/
    public String getName()
    {
        return this.list.getName();
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
    }


    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile()
    {
        DinoWriter writer = new DinoWriter();
        writer.writeListToFile(this.list);
    }

    /***************************************************************************
     * addEntry
     *
     **************************************************************************/
    public void addEntry(String entry, double weight)
    {
        int rowIndex = nextEmptyRow();
        int entryColumn = Columns.LIST_ENTRY.ordinal();
        int weightColumn = Columns.PROBABILITY_WEIGHT.ordinal();

        if(rowIndex == -1) //Need to add a row
        {
            this.addRow();
            rowIndex = this.entryCount - 1;


            this.list.setEntry(rowIndex, entry);
            this.list.setProbability(rowIndex, weight);
        }
        else //Add to existing entry
        {
            this.list.setEntry(rowIndex, entry);
            this.list.setProbability(rowIndex, weight);
        }

        this.probabilities.updateWeight(rowIndex, weight);
        this.fireTableDataChanged();
    }


    /***************************************************************************
     * getRowCount
     *
     **************************************************************************/
    @Override
    public int getRowCount() {
        return this.entryCount;
    }

    /***************************************************************************
     * getColumnCount
     *
     **************************************************************************/
    @Override
    public int getColumnCount() {
        return Columns.values().length;
    }

    /***************************************************************************
     * getColumnName
     *
     **************************************************************************/
    @Override
    public String getColumnName(int columnIndex) {

        Columns column = Columns.values()[columnIndex];

        return column.header;
    }

    /***************************************************************************
     * getColumnClass
     *
     **************************************************************************/
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if(columnIndex == 2)
        {
            return Trait[].class;
        }

        return String.class;
    }

    /***************************************************************************
     * isCellEdiable
     *
     **************************************************************************/
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Columns column = Columns.values()[columnIndex];

        switch(column)
        {
            case LIST_ENTRY:
            case PROBABILITY_WEIGHT:
            case ADD_TRAIT:
                return true;
            case PROBABILITY:
            case TRAIT:
                return false;
        }

        return false;
    }

    /***************************************************************************
     * getValueAt
     *
     **************************************************************************/
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Columns column = Columns.values()[columnIndex];

        switch(column)
        {
            case LIST_ENTRY:
                return this.list.getEntry(rowIndex).getListEntry();
            case PROBABILITY_WEIGHT:
                return this.list.getProbability(rowIndex);
            case PROBABILITY:
                return String.format("%.2f%%",
                        this.probabilities.getProbability(rowIndex));
            case ADD_TRAIT:
                return "Add Trait";
            case TRAIT:
                return list.getTraits(rowIndex);
        }

        return null;
    }

    /***************************************************************************
     * setValueAt
     *
     **************************************************************************/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Columns column = Columns.values()[columnIndex];

        switch(column)
        {
            case LIST_ENTRY:
                String entry = (String) aValue;

                setListEntry(rowIndex, entry);
                break;

            case PROBABILITY_WEIGHT:
                double weight;
                try
                {
                    weight = Double.parseDouble((String) aValue);
                    if(weight < 0)
                        weight = 0;
                }
                catch(NumberFormatException e)
                {
                    weight = 0;
                }

                setProbability(rowIndex, weight);

                break;

            case PROBABILITY:
                System.out.println("How did we get here?");
                break;
        }
    }

    /***************************************************************************
     * addTableModelListener
     *
     **************************************************************************/
    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }

    /***************************************************************************
     * removeTableModelListener
     *
     **************************************************************************/
    @Override
    public void removeTableModelListener(TableModelListener l) {
        super.removeTableModelListener(l);
    }

    public void setProbability(int rowIndex, double weight)
    {
        double currentWeight = (Double) this.getValueAt(rowIndex,
                Columns.PROBABILITY_WEIGHT.ordinal());

        String currentEntry = (String) this.getValueAt(rowIndex, Columns.LIST_ENTRY.ordinal());

        if(!currentEntry.equals(""))
        {
            this.list.setProbability(rowIndex, weight);
            this.probabilities.updateWeight(rowIndex, weight);
        }
        else if(currentWeight != 0)
        {
            this.list.setProbability(rowIndex, weight);
            this.probabilities.updateWeight(rowIndex, weight);
        }
        else
        {
            addEntry(currentEntry, weight);
        }

        //this.list.setProbability(rowIndex, weight);
        //this.probabilities.updateWeight(rowIndex, weight);
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
    public void setListEntry(int rowIndex, String newEntry)
    {
        double currentWeight = (Double) this.getValueAt(rowIndex,
                Columns.PROBABILITY_WEIGHT.ordinal());

        String currentEntry = (String) this.getValueAt(rowIndex, Columns.LIST_ENTRY.ordinal());

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
            this.addEntry(newEntry, 1.0);
            //Todo: Only do this if there aren't any traits
        }
        else //If the probability was already set, don't mess with it
        {
            this.list.setEntry(rowIndex, newEntry);
        }
    }


    /***************************************************************************
     * nextEmptyRow
     *
     *
     * Returns -1 if there's no empty rows
     **************************************************************************/
    private int nextEmptyRow()
    {
        for(int i = 0; i < this.entryCount; i++)
        {
            String entry = (String) this.getValueAt(i,
                    Columns.LIST_ENTRY.ordinal());


            double probability = (Double)
                    this.getValueAt(i, Columns.PROBABILITY_WEIGHT.ordinal());

            if(entry.equals("") && probability == 0.0)
                return i;
        }

        return -1;
    }
}
