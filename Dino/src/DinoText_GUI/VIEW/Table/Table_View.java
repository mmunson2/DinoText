package DinoText_GUI.VIEW.Table;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class Table_View extends JFrame {
    private JPanel panel1;
    private JTable listTable;
    private JTextField listName;
    private JLabel entryCount;
    private JButton increment;

    public Table_View() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 580);

        this.listTable.getTableHeader().setOpaque(false);
        this.listTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        this.listTable.setShowHorizontalLines(true);
        this.listTable.setGridColor(Color.DARK_GRAY);

    }

    public void setTableModel(TableModel tableModel)
    {
        this.listTable.setModel(tableModel);
    }

    public void setListName(String text)
    {
        this.listName.setText(text);
    }

    public int getEntryCount()
    {
        return Integer.parseInt(entryCount.getText());
    }

    public void setEntryCount(int count)
    {
        this.entryCount.setText("" + count);
    }

    public String getListName()
    {
        return this.listName.getText();
    }

    public void addIncrementListener(ActionListener l) {
        this.increment.addActionListener(l);
    }

    public void addListNameListener(ActionListener l)
    {
        this.listName.addActionListener(l);
    }

    public void updateTable()
    {
        this.listTable.updateUI();
    }


}
