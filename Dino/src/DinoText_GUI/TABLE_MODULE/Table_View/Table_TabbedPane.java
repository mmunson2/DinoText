package DinoText_GUI.TABLE_MODULE.Table_View;

import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.TraitDisplay.Table_TraitDisplay;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*******************************************************************************
 * Table TabbedPane
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Table_TabbedPane extends JFrame{
    private JTabbedPane listPane;
    private JPanel panel1;

    private ArrayList<Table_View> editorTabs = new ArrayList<>();
    private ArrayList<Table_View> designTabs = new ArrayList<>();
    int activeIndex = 0;

    private Table_View editorTab;
    private Table_View designTab;

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
        return this.editorTab != null;
    }

    /***************************************************************************
     * addList
     *
     **************************************************************************/
    public void addList(String name)
    {
        if(this.listPane.getTabCount() > 0)
            this.removeCurrentTabs();

        this.editorTab = new Table_View();
        this.editorTab.setListName(name);

        this.designTab = new Table_View();
        this.designTab.setListName(name);

        this.activeIndex = this.editorTabs.size();

        this.editorTabs.add(this.activeIndex, editorTab);
        this.designTabs.add(this.activeIndex, designTab);

        this.addCurrentTabs();

        this.listPane.setSelectedIndex(0);
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
    public int getActiveIndex()
    {
        return this.listPane.getSelectedIndex();
    }

    /***************************************************************************
     * switchList
     *
     **************************************************************************/
    public void switchList(int index)
    {
        if(index < 0 || index > this.editorTabs.size())
        {
            System.err.println("Error in Table_View -> TabbedPane -> switchList(): \n" +
                               "invalid index: " + index + "\n" +
                               "Active Lists: " + this.editorTabs.size());

            return;
        }

        if(this.listPane.getTabCount() > 0)
            this.removeCurrentTabs();

        this.activeIndex = index;

        this.editorTab = this.editorTabs.get(index);
        this.designTab = this.designTabs.get(index);

        this.addCurrentTabs();
    }

    /***************************************************************************
     * switchIndex
     *
     **************************************************************************/
    public void switchTab(int index)
    {
        if(index < 0 || index > 2)
        {
            System.err.println("Error in Table_View -> TabbedPane -> switchTab(): \n" +
                    "invalid index: " + index + "\n" +
                    "Should be 0 or 1.");

            return;
        }
        if(this.listPane.getTabCount() > 1)
            this.listPane.setSelectedIndex(index);
    }



    /***************************************************************************
     * setListName
     *
     **************************************************************************/
    public void setListName(String text) {
        this.removeCurrentTabs();

        this.editorTab.setListName(text);
        this.designTab.setListName(text);

        this.addCurrentTabs();
    }

    public int getSelectedRow()
    {
        if(this.listPane.getSelectedIndex() == 0)
        {
            return this.editorTab.getSelectedRow();
        }
        else
        {
            return this.designTab.getSelectedRow();
        }
    }

    public Component getPanel()
    {
        return this.panel1;
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

    private void removeCurrentTabs()
    {
        this.listPane.removeTabAt(0);
        this.listPane.removeTabAt(0);
    }

    private void addCurrentTabs()
    {
        String editorTabName = this.editorTab.getListName() + " Editor";
        String designTabName = this.designTab.getListName() + " Designer";

        this.listPane.addTab(editorTabName, this.editorTab.getPanel());
        this.listPane.addTab(designTabName, this.designTab.getPanel());
    }



    /***************************************************************************
     * PASS THROUGH METHODS
     **************************************************************************/

    public void setTableModel(Table_Model tableModel) {

        editorTab.setTableModel(tableModel.getEntryTab_model());
        designTab.setTableModel(tableModel.getDesignTab_model());

        editorTab.initializeEditorTabColumns();
        designTab.initializeDesignTabColumns();
    }

    public void setEntryCount(int count) {
        this.editorTab.setEntryCount(count);
        this.designTab.setEntryCount(count);
    }

    public String getListName() {
        return this.designTab.getListName();
    }

    public void addIncrementListener(ActionListener l) {
        this.editorTab.addIncrementListener(l);
        this.designTab.addIncrementListener(l);
    }

    public void removeIncrementListener(ActionListener l)
    {
        this.editorTab.removeIncrementListener(l);
        this.designTab.removeIncrementListener(l);
    }

    public void addListNameListener(ActionListener l) {
        this.editorTab.addListNameListener(l);
        this.designTab.addListNameListener(l);
    }

    public void removeListNameListener(ActionListener l)
    {
        this.editorTab.removeListNameListener(l);
        this.designTab.removeListNameListener(l);
    }

    public void addDebugListener(ActionListener l)
    {
        this.editorTab.addDebugListener(l);
        this.designTab.addDebugListener(l);
    }

    public void removeDebugListener(ActionListener l)
    {
        this.editorTab.removeDebugListener(l);
        this.designTab.removeDebugListener(l);
    }

    public void addTraitButtonListener(ActionListener l)
    {
        //this.editorTab.addTraitButtonListener(l);
        this.designTab.addTraitButtonListener(l);
    }

    public void removeTraitButtonListener(ActionListener l)
    {
        this.designTab.removeTraitButtonListener(l);
    }

    public void addEditTraitButtonListener(ActionListener l)
    {
        this.designTab.addEditTraitButtonListener(l);
    }

    public void removeEditTraitButtonListener(ActionListener l)
    {
        this.designTab.removeEditTraitButtonListener(l);
    }

    public void initializeAddTraitButtonColumn()
    {
        this.designTab.initializeAddTraitButtonColumn();
    }

    public void initializeEditTraitButtonColumn()
    {
        this.designTab.initializeEditTraitButtonColumn();
    }

    public void updateTable() {
        this.editorTab.updateTable();
        this.designTab.updateTable();
    }

}
