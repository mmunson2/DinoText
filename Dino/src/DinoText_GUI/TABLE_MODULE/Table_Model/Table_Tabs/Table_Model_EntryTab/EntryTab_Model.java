package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab;

import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Data;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.TABLE_MODULE.TraitEditor.Editor_Controller;
import DinoText_GUI.Util.DinoList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/*******************************************************************************
 * Entry Tab Model
 *
 ******************************************************************************/
public class EntryTab_Model extends AbstractTableModel
{
    private Table_Data model;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public EntryTab_Model(Table_Data model)
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
        return EntryColumns.values().length;
    }

    /***************************************************************************
     * Get Column Name
     *
     **************************************************************************/
    @Override
    public String getColumnName(int columnIndex) {

        EntryColumns column = EntryColumns.values()[columnIndex];

        return column.header;
    }

    /***************************************************************************
     * Get Column Class
     *
     **************************************************************************/
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }

    /***************************************************************************
     * Is Cell Editable
     *
     **************************************************************************/
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        EntryColumns column = EntryColumns.values()[columnIndex];

        switch(column)
        {
            case ENTRY_NAME:
            case ENTRY:
                return true;
        }

        return false;
    }

    /***************************************************************************
     * Get Value At
     *
     **************************************************************************/
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        EntryColumns column = EntryColumns.values()[columnIndex];

        DinoList list = this.model.getList();

        switch(column)
        {
            case ENTRY_NAME:
                return list.getEntryName(rowIndex);
            case ENTRY:
                return list.getEntry(rowIndex).getListEntry();
        }

        return null;
    }


    /***************************************************************************
     * Set Value At
     *
     **************************************************************************/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        EntryColumns column = EntryColumns.values()[columnIndex];

        int nextEmpty = this.nextEmptyRow();

        switch(column)
        {
            case ENTRY_NAME:
                String entryName = (String) aValue;
                this.model.setEntryName(nextEmpty, rowIndex, entryName);
                break;

            case ENTRY:
                String entry = (String) aValue;
                this.model.setEntry(nextEmpty, rowIndex, entry);
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
        for(int i = 0; i < this.model.getEntryCount(); i++)
        {
            String entryName = (String) this.getValueAt(i, EntryColumns.ENTRY_NAME.ordinal());

            String entry = (String) this.getValueAt(i,
                    EntryColumns.ENTRY.ordinal());

            if(entry.equals("") && entryName.equals(""))
                return i;
        }

        return -1;
    }

}
