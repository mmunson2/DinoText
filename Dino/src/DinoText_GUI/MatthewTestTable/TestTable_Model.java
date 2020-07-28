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
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override public String getColumnName(int columnIndex)
    {
        return "";
    }

    @Override public Class<?> getColumnClass(int columnIndex)
    {
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
