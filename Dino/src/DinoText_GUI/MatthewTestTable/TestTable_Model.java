package DinoText_GUI.MatthewTestTable;

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

            case 2: return "Edit Button";

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
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

}
