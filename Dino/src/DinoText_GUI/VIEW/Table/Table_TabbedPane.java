package DinoText_GUI.VIEW.Table;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table_TabbedPane extends JFrame{
    private JTabbedPane listPane;
    private JPanel panel1;

    private ArrayList<Table_View> tables = new ArrayList<>();
    private Table_View activeTable;
    private int activeIndex;

    public Table_TabbedPane()
    {
        activeTable = new Table_View();
        listPane.add(activeTable.getPanel());
        this.activeIndex = 0;
        listPane.setTitleAt(activeIndex, "Untitled List");

        tables.add(activeTable);

    }

    public void addList(String name)
    {
        this.activeTable = new Table_View();
        this.activeTable.setListName(name);
        this.activeIndex = this.tables.size();

        this.tables.add(activeIndex, activeTable);
    }

    public void switchList(int index)
    {
        this.activeIndex = index;
        this.activeTable = this.tables.get(index);
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

    public void removeIncrementListener(ActionListener l)
    {
        this.activeTable.removeIncrementListener(l);
    }

    public void addListNameListener(ActionListener l) {
        this.activeTable.addIncrementListener(l);
    }

    public void removeListNameListener(ActionListener l)
    {
        this.activeTable.removeListNameListener(l);
    }

    public void updateTable() {
        this.activeTable.updateTable();
    }



    public Component getPanel1() {
        return this.panel1;
    }


}
