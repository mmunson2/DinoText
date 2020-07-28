package DinoText_GUI.IhsanTestTable;

import DinoParser.List.Trait;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TestTable_Model extends AbstractTableModel
{
    @Override
    public int getRowCount()
    {
        return 3;
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return rowIndex;

            case 1: return rowIndex * 2;

            //Todo: This should return a trait array eventually
            case 2: return "Trait Display";

        }

        return null;
    }

    @Override public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Test Value 1";

            case 1: return "Test Value 2";

            case 2: return "Add Trait";

        }

        return "";
    }

    @Override public Class<?> getColumnClass(int columnIndex)
    {
        if(columnIndex == 2)
        {
            return Trait[].class;
        }

        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

}
