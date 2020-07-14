package DinoText_GUI.VIEW.Table;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table_TabbedPane extends JFrame{
    private JTabbedPane listPane;
    private JPanel panel1;

    ArrayList<Table_View> tables = new ArrayList<>();
    Table_View activeTable;

    public Table_TabbedPane()
    {
        activeTable = new Table_View();
        listPane.add(activeTable.getPanel());
        listPane.setTitleAt(0, "Untitled List");

        tables.add(activeTable);

    }

    public void setTableModel(TableModel tableModel) {
        activeTable.setTableModel(tableModel);
    }


    public void setListPaneModel(SingleSelectionModel paneModel)
    {
        this.listPane.setModel(paneModel);
    }

    public void setListName(String text) {
        this.activeTable.setListName(text);
    }

    public void setEntryCount(int count) {
        this.activeTable.setEntryCount(count);
    }

    public String getListName() {
        return this.activeTable.getListName();
    }

    public void addIncrementListener(ActionListener l) {
        this.activeTable.addIncrementListener(l);
    }

    public void addListNameListener(ActionListener l) {
        this.activeTable.addIncrementListener(l);
    }

    public void updateTable() {
        this.activeTable.updateTable();
    }



    public Component getPanel1() {
        return this.panel1;
    }


}
