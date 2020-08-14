package DinoText_GUI.TABLE_MODULE.Table_View;

import DinoText_GUI.TABLE_MODULE.Table_Model.Columns;
import DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.TraitDisplay.Table_TraitDisplay;

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

    private Table_NoList noList;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_TabbedPane()
    {
        this.noList = new Table_NoList();

        addNoListTab();
    }

    public boolean hasActiveTable()
    {
        return this.activeTable != null;
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

    public void addNoListTab()
    {
        this.listPane.addTab(Table_NoList.NAME, this.noList.getPanel());
    }

    public boolean hasNoListTab()
    {
        int index = this.listPane.indexOfTab(Table_NoList.NAME);

        return index != -1;
    }

    public void removeNoListTab()
    {
        int index = this.listPane.indexOfTab(Table_NoList.NAME);

        if(index != -1)
        {
            this.listPane.removeTabAt(index);
        }
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
     * setListName
     *
     **************************************************************************/
    public void setListName(String text) {
        this.activeTable.setListName(text);
        listPane.setTitleAt(this.getSelectedIndex(), text);
    }

    public int getSelectedRow() {return this.activeTable.getSelectedRow();}


    public Component getPanel()
    {
        return this.panel1;
    }

    /***************************************************************************
     * set Button Column
     **************************************************************************/
    public void setButtonColumn(int columnIndex)
    {
        this.activeTable.getJTable().getColumn(Columns.TRAIT.header).setCellRenderer(new Table_TraitDisplay());
    }

    public void addTabSwitchListener(ChangeListener l)
    {
        this.listPane.addChangeListener(l);
    }

    public void removeTabSwitchListener(ChangeListener l)
    {
        this.listPane.removeChangeListener(l);
    }

    public void addNoListButtonListener(ActionListener l)
    {
        this.noList.addCreateListActionListener(l);
    }

    public void removeNoListButtonListener(ActionListener l)
    {
        this.noList.removeCreateListActionListener(l);
    }

    /***************************************************************************
     * PASS THROUGH METHODS
     **************************************************************************/

    public void setTableModel(TableModel tableModel) {
        activeTable.setTableModel(tableModel);
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
        this.activeTable.addListNameListener(l);
    }

    public void removeListNameListener(ActionListener l)
    {
        this.activeTable.removeListNameListener(l);
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

    public void addEditTraitButtonListener(ActionListener l)
    {
        this.activeTable.addEditTraitButtonListener(l);
    }

    public void removeEditTraitButtonListener(ActionListener l)
    {
        this.activeTable.removeEditTraitButtonListener(l);
    }

    public void initializeAddTraitButtonColumn()
    {
        this.activeTable.initializeAddTraitButtonColumn();
    }

    public void initializeEditTraitButtonColumn()
    {
        this.activeTable.initializeEditTraitButtonColumn();
    }

    public void updateTable() {
        this.activeTable.updateTable();
    }

}
