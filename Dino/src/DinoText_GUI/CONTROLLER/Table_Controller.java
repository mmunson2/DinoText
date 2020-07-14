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

        addListeners();
    }

    public void addListeners()
    {
        this.view.addIncrementListener(incrementListener);
        this.view.addListNameListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.addTableModelListener(tableModelListener);
    }

    public void removeListeners()
    {
        this.view.removeIncrementListener(incrementListener);
        this.view.removeIncrementListener(listNameListener);

        Table_Model model = manager.getCurrentModel();
        model.removeTableModelListener(tableModelListener);
    }

    public void addList()
    {
        addList("untitledList", null);
    }

    public void addList(String name)
    {
        addList(name, null);
    }

    public void addList(String name, String[] entries)
    {
        if(entries == null)
        {
            this.manager.addModel(name);
            this.view.addList(name);
        }
        else
        {
            System.err.println("Matthew didn't implement this yet :(");
            System.exit(-1);
        }
    }

    class listener_tabSwitch implements ActionListener
    {


        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    class listener_increment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            manager.getCurrentModel().addRow();
            view.setEntryCount(manager.getCurrentModel().getRowCount());
        }
    }

    class listener_tableModel implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e)
        {
            view.updateTable();
        }
    }

    class listener_listName implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            manager.getCurrentModel().setName(view.getListName());
        }
    }



}
