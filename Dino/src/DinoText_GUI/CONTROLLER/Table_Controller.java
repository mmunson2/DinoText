package DinoText_GUI.CONTROLLER;

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
public class Table_Controller
{
    private Table_Manager manager;
    private int listNumber;
    private Table_TabbedPane view;

    //Listeners
    private listener_increment incrementListener;
    private listener_listName listNameListener;
    private listener_tableModel tableModelListener;
    private listener_tabSwitch tabSwitchListener;


    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Controller(Table_Manager manager, Table_TabbedPane view)
    {
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

    /***************************************************************************
     * addListeners
     *
     **************************************************************************/
    public void addListeners()
    {
        this.view.addIncrementListener(incrementListener);
        this.view.addListNameListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.addTableModelListener(tableModelListener);
    }

    /***************************************************************************
     * removeListeners
     *
     **************************************************************************/
    public void removeListeners()
    {
        this.view.removeIncrementListener(incrementListener);
        this.view.removeListNameListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.removeTableModelListener(tableModelListener);
    }

    /***************************************************************************
     * addList - noArg
     *
     **************************************************************************/
    public void addList()
    {
        addList("untitledList", null);
    }

    /***************************************************************************
     * addList - String overload
     *
     **************************************************************************/
    public void addList(String name)
    {
        addList(name, null);
    }

    /***************************************************************************
     * addList - String and entries overload
     *
     **************************************************************************/
    public void addList(String name, String[] entries)
    {
        if(entries == null)
        {
            System.out.println("Adding a list");
            removeListeners();
            this.manager.addModel(name);
            this.view.addList(name);
            this.view.setTableModel(this.manager.getCurrentModel());
            view.setEntryCount(manager.getCurrentModel().getRowCount());
            addListeners();
        }
        else
        {
            System.err.println("Matthew didn't implement this yet :(");
            System.exit(-1);
        }
    }

    /***************************************************************************
     * switchToIndex
     *
     **************************************************************************/
    public void switchToIndex(int index)
    {
        removeListeners();
        this.manager.switchModel(index);
        this.view.switchList(index);
        addListeners();
    }

    public void writeToFile()
    {
        this.manager.writeToFile();
    }


    /***************************************************************************
     * Inner Class: Tab Switch Listener
     *
     **************************************************************************/
    class listener_tabSwitch implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            switchToIndex(view.getSelectedIndex());
        }
    }


    /***************************************************************************
     * Inner Class: Increment Row Listener
     *
     **************************************************************************/
    class listener_increment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e)
        {
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
        public void tableChanged(TableModelEvent e)
        {
            view.updateTable();
        }
    }

    /***************************************************************************
     * Inner Class: List Name Listener
     *
     **************************************************************************/
    class listener_listName implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            manager.getCurrentModel().setName(view.getListName());
        }
    }



}
