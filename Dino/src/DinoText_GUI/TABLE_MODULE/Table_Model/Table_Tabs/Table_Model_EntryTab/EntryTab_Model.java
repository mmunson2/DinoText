package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab;

import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Data;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.TABLE_MODULE.TraitEditor.Editor_Controller;
import DinoText_GUI.Util.DinoList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class EntryTab_Model extends AbstractTableModel
{
    private Table_Data model;

    public EntryTab_Model(Table_Data model)
    {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return this.model.getEntryCount();
    }

    @Override
    public int getColumnCount() {
        return EntryColumns.values().length;
    }


    @Override
    public String getColumnName(int columnIndex) {

        EntryColumns column = EntryColumns.values()[columnIndex];

        return column.header;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }

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
     * setValueAt
     *
     **************************************************************************/
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        EntryColumns column = EntryColumns.values()[columnIndex];

        switch(column)
        {
            case ENTRY_NAME:
                String entryName = (String) aValue;
                this.model.getList().setEntryName(rowIndex, entryName);
                break;

            case ENTRY:
                String entry = (String) aValue;
                this.model.getList().setEntry(rowIndex, entry);
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
