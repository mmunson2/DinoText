package DinoText_GUI.MODEL.Table;


import DinoText_GUI.MODEL.DinoList;

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
        this.list = new DinoList("unnamedList");

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

    public String getName()
    {
        return this.list.getName();
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
        }

        return null;
    }

    /***************************************************************************
     * getColumnClass
     *
     **************************************************************************/
    @Override
    public Class<?> getColumnClass(int columnIndex) {
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
                this.list.setEntry(rowIndex, (String) aValue);
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
}
