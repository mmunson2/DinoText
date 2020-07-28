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

        switch(column)
        {
            case LIST_ENTRY:
                return "List Entry:";
            case PROBABILITY_WEIGHT:
                return "Probability Weight:";
            case PROBABILITY:
                return "Probability:";
            case TRAIT:
                return "Trait";
        }

        return null;
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
                return true;
            case PROBABILITY:
                return false;
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

                double currentWeight = (Double) this.getValueAt(rowIndex,
                        Columns.PROBABILITY_WEIGHT.ordinal());

                if(currentWeight == 0)
                {
                    this.addEntry(entry, 1.0);
                }
                else
                {
                    this.addEntry(entry, currentWeight);
                }

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

                this.list.setProbability(rowIndex, weight);
                this.probabilities.updateWeight(rowIndex, weight);

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
