package DinoText_GUI.MatthewTestTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

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



}
