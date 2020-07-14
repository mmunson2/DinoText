package DinoText_GUI.VIEW.Table;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table_View {

    private JTable listTable;
    private JTextField listName;
    private JLabel entryCount;
    private JButton increment;
    private JPanel panel1;

    public Table_View()
    {
        this.listTable.getTableHeader().setOpaque(false);
        this.listTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        this.listTable.setShowHorizontalLines(true);
        this.listTable.setGridColor(Color.DARK_GRAY);

    }

    public void setTableModel(TableModel tableModel) {
        this.listTable.setModel(tableModel);
    }

    public void setListName(String text) {
        this.listName.setText(text);
    }

    public int getEntryCount() {
        return Integer.parseInt(entryCount.getText());
    }

    public void setEntryCount(int count) {
        this.entryCount.setText("" + count);
    }

    public String getListName() {
        return this.listName.getText();
    }

    public void addIncrementListener(ActionListener l) {
        this.increment.addActionListener(l);
    }

    public void addListNameListener(ActionListener l) {
        this.listName.addActionListener(l);
    }

    public void updateTable() {
        this.listTable.updateUI();
    }


    public Component getPanel()
    {
        return this.panel1;
    }

}
