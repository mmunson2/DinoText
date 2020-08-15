package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab;


import Dino.List.ListEntry;
import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Data;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab.EntryColumns;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab.EntryTab_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Probabilities;
import DinoText_GUI.Util.DinoList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.File;

/*******************************************************************************
 * Design Tab Model
 *
 ******************************************************************************/
public class DesignTab_Model extends AbstractTableModel
{
    private Table_Data model;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public DesignTab_Model(Table_Data model)
    {
        this.model = model;
    }

    /***************************************************************************
     * Get Row Count
     *
     **************************************************************************/
    @Override
    public int getRowCount() {
        return this.model.getEntryCount();
    }

    /***************************************************************************
     * Get Column Count
     *
     **************************************************************************/
    @Override
    public int getColumnCount() {
        return DesignColumns.values().length;
    }

    /***************************************************************************
     * Get Column Name
     *
     **************************************************************************/
    @Override
    public String getColumnName(int columnIndex) {

        DesignColumns column = DesignColumns.values()[columnIndex];

        return column.header;
    }

    /***************************************************************************
     * Get Column Class
     *
     **************************************************************************/
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if(columnIndex == DesignColumns.TRAIT.ordinal())
        {
            return Trait[].class;
        }
        else
        {
            return String.class;
        }
    }

    /***************************************************************************
     * Is Cell Editable
     *
     **************************************************************************/
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        DesignColumns column = DesignColumns.values()[columnIndex];

        switch(column)
        {
            case ENTRY_NAME:
            case PROBABILITY_WEIGHT:
            case ADD_TRAIT:
            case EDIT_TRAIT:
                return true;
            case PROBABILITY:
            case TRAIT:
                return false;
        }

        return false;
    }

    /***************************************************************************
     * Get Value At
     *
     **************************************************************************/
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        DesignColumns column = DesignColumns.values()[columnIndex];

        DinoList list = this.model.getList();
        Table_Probabilities probabilities = this.model.getProbabilities();

        switch(column)
        {
            case ENTRY_NAME:
                return list.getEntryName(rowIndex);
            case PROBABILITY_WEIGHT:
                return list.getProbability(rowIndex);
            case PROBABILITY:
                return String.format("%.2f%%",
                        probabilities.getProbability(rowIndex));
            case ADD_TRAIT:
                return "Add Trait";
            case EDIT_TRAIT:
                return "Edit Trait";
            case TRAIT:
                return list.getTraits(rowIndex);
        }

        return null;
    }

    /***************************************************************************
     * Set Value At
     *
     **************************************************************************/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        DesignColumns column = DesignColumns.values()[columnIndex];

        int nextEmpty = this.nextEmptyRow();

        switch(column)
        {
            case ENTRY_NAME:
                String entryName = (String) aValue;
                this.model.setEntryName(nextEmpty, rowIndex, entryName);
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

                this.model.setWeight(nextEmpty, rowIndex, weight);

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
     * Next Empty Row
     *
     *
     * Returns -1 if there's no empty rows
     **************************************************************************/
    private int nextEmptyRow()
    {
        for(int i = 0; i < this.model.getEntryCount(); i++)
        {
            String entry = (String) this.getValueAt(i,
                    DesignColumns.ENTRY_NAME.ordinal());

            double probability = (Double)
                    this.getValueAt(i, DesignColumns.PROBABILITY_WEIGHT.ordinal());

            if(entry.equals("") && probability == 0.0)
                return i;
        }

        return -1;
    }


}
