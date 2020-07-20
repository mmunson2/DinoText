package DinoText_GUI.CONTROLLER.Table;

import DinoText_GUI.CONTROLLER.Dialogue.Dialogue_Controller;
import DinoText_GUI.MODEL.Table.Table_Manager;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.VIEW.Table.Table_TabbedPane;
import DinoText_GUI.VIEW.Table.Table_View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Table Controller
 *
 ******************************************************************************/
public class Table_Controller {
    private Table_Manager manager;
    private int listNumber;
    private Table_TabbedPane view;

    //Listeners
    private listener_increment incrementListener;
    private listener_listName listNameListener;
    private listener_tableModel tableModelListener;
    private listener_tabSwitch tabSwitchListener;

    private Dialogue_Controller dialogue_controller;


    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Controller(Table_Manager manager, Table_TabbedPane view) {
        this.manager = manager;
        this.listNumber = 0;
        this.view = view;

        Table_Model model = manager.getCurrentModel();

        this.view.setTableModel(model);
        this.view.setEntryCount(Table_Model.DEFAULT_ROWS);

        this.incrementListener = new listener_increment();
        this.listNameListener = new listener_listName();
        this.tableModelListener = new listener_tableModel();
        this.tabSwitchListener = new listener_tabSwitch();

        this.view.addTabSwitchListener(tabSwitchListener);

        addListeners();
    }

    public void setDialogue_controller(Dialogue_Controller controller) {
        this.dialogue_controller = controller;
    }

    /***************************************************************************
     * addList - noArg
     *
     **************************************************************************/
    public void addList() {
        addList("Untitled List", null);
    }

    /***************************************************************************
     * addList - String overload
     *
     **************************************************************************/
    public void addList(String name) {
        addList(name, null);
    }

    /***************************************************************************
     * addList - String and entries overload
     *
     **************************************************************************/
    public void addList(String name, String[] entries) {
        if (entries == null) //Creating a list from scratch
        {
            if (this.manager.getCurrentModel().getName().equals("Untitled List")
                    && this.manager.getSize() == 1) {
                this.renameList(name);
            } else {
                removeListeners();
                this.manager.addModel(name);
                this.view.addList(name);
                this.view.setTableModel(this.manager.getCurrentModel());
                view.setEntryCount(manager.getCurrentModel().getRowCount());
                addListeners();
            }
        } else //Creating a list from an array of Strings
        {





            System.err.println("Matthew didn't implement this yet :(");
            System.exit(-1);
        }
    }

    public void addEntry(String entry)
    {


    }

    public void addEntry(String entry, double probability)
    {


    }

    


    /***************************************************************************
     * rename List
     *
     **************************************************************************/
    public void renameList(String newName) {
        this.manager.getCurrentModel().setName(newName);
        this.view.setListName(newName);
    }

    /***************************************************************************
     * rename List - listIndex overload
     *
     **************************************************************************/
    public void renameList(String newName, int listIndex) {
        if (listIndex != this.manager.getCurrentListIndex())
            this.switchToIndex(listIndex);

        renameList(newName);
    }

    /***************************************************************************
     * rename List - String, String overload
     *
     **************************************************************************/
    public void renameList(String newName, String oldName) {
        renameList(newName, manager.getListIndexFromName(oldName));
        dialogue_controller.renameList(newName, oldName);
    }


    /***************************************************************************
     * switchToIndex
     *
     **************************************************************************/
    public void switchToIndex(int index) {
        removeListeners();
        this.manager.switchModel(index);
        this.view.switchList(index);
        addListeners();
    }

    /***************************************************************************
     * switchToName
     *
     **************************************************************************/
    public void switchToName(String listName) {
        int index = manager.getListIndexFromName(listName);

        if (index != -1)
            switchToIndex(index);
    }


    public void writeToFile() {
        this.manager.writeToFile();
    }


    /***************************************************************************
     * Inner Class: Tab Switch Listener
     *
     **************************************************************************/
    class listener_tabSwitch implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            switchToIndex(view.getSelectedIndex());
        }
    }

    /***************************************************************************
     * Inner Class: Increment Row Listener
     *
     **************************************************************************/
    class listener_increment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            manager.getCurrentModel().addRow();
            view.setEntryCount(manager.getCurrentModel().getRowCount());
        }
    }

    /***************************************************************************
     * Inner Class: Table Model Listener
     *
     **************************************************************************/
    class listener_tableModel implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            view.updateTable();
        }
    }

    /***************************************************************************
     * Inner Class: List Name Listener
     *
     **************************************************************************/
    class listener_listName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            renameList(view.getListName());
            renameList(view.getListName(), manager.getCurrentModel().getName());
        }
    }

    /***************************************************************************
     * addListeners
     **************************************************************************/
    private void addListeners() {
        this.view.addIncrementListener(incrementListener);
        this.view.addListNameListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.addTableModelListener(tableModelListener);
    }

    /***************************************************************************
     * removeListeners
     *
     **************************************************************************/
    private void removeListeners() {
        this.view.removeIncrementListener(incrementListener);
        this.view.removeListNameListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.removeTableModelListener(tableModelListener);
    }


}
