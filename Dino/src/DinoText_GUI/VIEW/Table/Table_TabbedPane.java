package DinoText_GUI.VIEW.Table;

import DinoText_GUI.MODEL.Table.Columns;
import DinoText_GUI.MODEL.Table.Table_TraitDisplay;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*******************************************************************************
 * Table TabbedPane
 *
 ******************************************************************************/
public class Table_TabbedPane extends JFrame{
    private JTabbedPane listPane;
    private JPanel panel1;

    private ArrayList<Table_View> tables = new ArrayList<>();
    private Table_View activeTable;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_TabbedPane()
    {
        activeTable = new Table_View();
        listPane.add(activeTable.getPanel());
        listPane.setTitleAt(this.getSelectedIndex(), "Untitled List");
        this.activeTable.setListName("Untitled List");

        tables.add(activeTable);
    }

    /***************************************************************************
     * addList
     *
     **************************************************************************/
    public void addList(String name)
    {
        this.activeTable = new Table_View();
        this.activeTable.setListName(name);

        this.tables.add(this.tables.size(), activeTable);
        this.listPane.addTab(name, this.activeTable.getPanel());
        this.listPane.setSelectedIndex(this.tables.size() - 1);
    }

    /***************************************************************************
     * getSelectedIndex
     *
     **************************************************************************/
    public int getSelectedIndex()
    {
        return this.listPane.getSelectedIndex();
    }

    /***************************************************************************
     * switchList
     *
     **************************************************************************/
    public void switchList(int index)
    {
        this.activeTable = this.tables.get(index);
        this.listPane.setSelectedIndex(index);
    }

    /***************************************************************************
     * setTableModel
     *
     **************************************************************************/
    public void setTableModel(TableModel tableModel) {
        activeTable.setTableModel(tableModel);
    }

    /***************************************************************************
     * setListName
     *
     **************************************************************************/
    public void setListName(String text) {
        this.activeTable.setListName(text);
        listPane.setTitleAt(this.getSelectedIndex(), text);
    }

    /***************************************************************************
     * setEntryCount
     *
     **************************************************************************/
    public void setEntryCount(int count) {
        this.activeTable.setEntryCount(count);
    }

    /***************************************************************************
     * getListName
     *
     **************************************************************************/
    public String getListName() {
        return this.activeTable.getListName();
    }

    public int getSelectedRow() {return this.activeTable.getSelectedRow();}


    /***************************************************************************
     * addIncrementListener
     *
     **************************************************************************/
    public void addIncrementListener(ActionListener l) {
        this.activeTable.addIncrementListener(l);
    }

    /***************************************************************************
     * removeIncrementListener
     *
     **************************************************************************/
    public void removeIncrementListener(ActionListener l)
    {
        this.activeTable.removeIncrementListener(l);
    }

    /***************************************************************************
     * addListNameListener
     *
     **************************************************************************/
    public void addListNameListener(ActionListener l) {
        this.activeTable.addListNameListener(l);
    }

    /***************************************************************************
     * removeListNameListener
     *
     **************************************************************************/
    public void removeListNameListener(ActionListener l)
    {
        this.activeTable.removeListNameListener(l);
    }

    /***************************************************************************
     * addTabSwitchListener
     *
     **************************************************************************/
    public void addTabSwitchListener(ChangeListener l)
    {
        this.listPane.addChangeListener(l);
    }

    /***************************************************************************
     * removeTabSwitchListener
     *
     **************************************************************************/
    public void removeTabSwitchListener(ChangeListener l)
    {
        this.listPane.removeChangeListener(l);
    }

    public void addDebugListener(ActionListener l)
    {
        this.activeTable.addDebugListener(l);
    }

    public void removeDebugListener(ActionListener l)
    {
        this.activeTable.removeDebugListener(l);
    }

    public void addTraitButtonListener(ActionListener l)
    {
        this.activeTable.addTraitButtonListener(l);
    }

    public void removeTraitButtonListener(ActionListener l)
    {
        this.activeTable.removeTraitButtonListener(l);
    }

    public void initializeAddTraitButtonColumn()
    {
        this.activeTable.initializeAddTraitButtonColumn();
    }


    public void updateTable() {
        this.activeTable.updateTable();
    }



    public Component getPanel1() {
        return this.panel1;
    }


    /***************************************************************************
     * set Button Column
     **************************************************************************/
    public void setButtonColumn(int columnIndex)
    {
        this.activeTable.getJTable().getColumn(Columns.TRAIT.header).setCellRenderer(new Table_TraitDisplay());
    }
}
