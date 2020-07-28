package DinoText_GUI.MatthewTestTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class TestTable_View
{
    private JTable testTable;
    private JPanel panel1;
    private JLabel description;

    //Table constructor just formats the table the way I like it
    public TestTable_View()
    {
        this.testTable.getTableHeader().setOpaque(false);
        this.testTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        this.testTable.setShowHorizontalLines(true);
        this.testTable.setGridColor(Color.DARK_GRAY);
    }

    //This is where we link the table to its model
    public void setTableModel(TableModel tableModel) {
        this.testTable.setModel(tableModel);
    }

    public JPanel getPanel()
    {
        return this.panel1;
    }

    public void setButtonColumn(int columnIndex)
    {
        this.testTable.getColumn("Add Trait").setCellRenderer(new TableButton_View());
        this.testTable.getColumn("Add Trait").setCellEditor(new TableButton_Model(new JCheckBox()));
    }

    public void setButtonListener(ActionListener l)
    {
        TableButton_Model model = (TableButton_Model) this.testTable.getColumn("Add Trait").getCellEditor();

        model.addButtonListener(l);
    }

    public int getSelectedRow()
    {
        return this.testTable.getSelectedRow();
    }



}
