package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.Table.Table_Model;
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
    private Table_Model model;
    private Table_View view;

    public Table_Controller(Table_Model model, Table_View view)
    {
        this.model = model;
        this.view = view;

        this.view.setTableModel(model);
        this.view.setEntryCount(Table_Model.DEFAULT_ROWS);


        this.view.addIncrementListener(new listener_increment());
        this.view.addListNameListener(new listener_listName());
        this.model.addTableModelListener(new listener_tableModel());
    }

    class listener_increment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            model.addRow();
            view.setEntryCount(model.getRowCount());
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
            model.setName(view.getListName());
        }
    }



}
