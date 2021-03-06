package DinoText_GUI.TABLE_MODULE.Table_Controller;

import Dino.FileTypes;
import Dino.List.ListEntry;
import Dino.List.Trait;
import Dino.ListParser;
import DinoText_GUI.DIALOGUE_MODULE.DialogueController.Dialogue_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Probabilities;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Model;
import DinoText_GUI.TABLE_MODULE.Table_View.Table_TabbedPane;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_View;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

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
    private listener_addTraitButton traitButtonListener;

    private listener_debug debugListener;


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
        this.traitButtonListener = new listener_addTraitButton();

        this.debugListener = new listener_debug();

        this.view.addTabSwitchListener(tabSwitchListener);

        this.view.initializeAddTraitButtonColumn();
        this.view.addTraitButtonListener(traitButtonListener);

        this.view.setTableModel(model);
        this.view.setButtonColumn(0);
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
     *
     **************************************************************************/
    public void addList(String name, String[] entries) {

        //Check if the list already exists
        if(this.manager.hasList(name))
        {
            //Not doing anything currently!
        }
        //If this is the first list, just rename Untitled List
        else if (this.manager.getCurrentModel().getName().equals("Untitled List")
                && this.manager.getSize() == 1) {
            this.renameList(name);
            this.view.initializeAddTraitButtonColumn();

            this.view.addTraitButtonListener(traitButtonListener);
        }
        else //Otherwise make a whole new list
        {
            removeListeners();
            this.manager.addModel(name);
            this.view.addList(name);
            this.view.setTableModel(this.manager.getCurrentModel());
            view.setEntryCount(manager.getCurrentModel().getRowCount());
            this.view.initializeAddTraitButtonColumn();

            addListeners();

            this.view.addTraitButtonListener(traitButtonListener);
        }

        //If there are entries, add them
        if(entries != null)
        {
            for(int i = 0; i < entries.length; i++)
            {
                this.addEntry(entries[i]);
            }
        }
    }

    public String[] getListNames()
    {
        return this.manager.getListNames();
    }

    public void addEntry(String entry)
    {
        this.manager.getCurrentModel().addEntry(entry, 1.0);
    }

    public void addEntry(String entry, double weight)
    {
        this.manager.getCurrentModel().addEntry(entry, weight);
    }

    public void addEntry(ListEntry listEntry)
    {
        this.manager.getCurrentModel().addEntry(listEntry);
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


    /***************************************************************************
     * writeAllToFile
     *
     **************************************************************************/
    public void writeAllToFile() {
        this.manager.writeToFile();
    }

    /***************************************************************************
     * writeCurrentToFile
     *
     **************************************************************************/
    public void writeCurrentToFile()
    {
        this.manager.getCurrentModel().writeToFile();
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile(String name)
    {
        this.manager.writeToFile(name);
    }

    //Todo: Support Traits
    //Todo: If a list is already open, just switch the tab
    //Todo: Implement file extensions
    /***************************************************************************
     * openFile
     *
     **************************************************************************/
    public void openFile(String fileName)
    {
        if(!FileTypes.hasListExtension(fileName))
        {
            System.err.println("Could not open " + fileName);

            JOptionPane.showMessageDialog(null, "Could not open " + fileName);

            return;
        }

        ListParser parser = new ListParser(new File(fileName));

        String name = parser.getName();

        if(manager.hasList(name))
        {
            //Todo: Make this overwrite the existing list
            return;
        }

        this.addList(parser.getName());

        ListEntry[] entries = parser.getList();

        for(ListEntry entry : entries)
        {
            this.addEntry(entry);
        }
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
            renameList(view.getListName(), manager.getCurrentModel().getName());
            renameList(view.getListName());
        }
    }

    class listener_debug implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            File root = new File("/Users/matthewmunson/Documents/GitHub/DinoText/Resources/Lists");
            System.out.println(root.getAbsolutePath());

            String[] files = root.list();


            Random random = new Random();

            String fileName = files[random.nextInt(files.length)];

            System.out.println(fileName);
            openFile(fileName);


        }
    }

    class listener_addTraitButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int row = view.getSelectedRow();
            Table_Probabilities probabilities =
                    manager.getCurrentModel().getProbabilities();

            Creator_View traitView = new Creator_View();

            Creator_Model traitModel = new Creator_Model();
            traitModel.setProbabilities(probabilities);
            traitModel.setRowIndex(row);

            Creator_Controller traitController =
                    new Creator_Controller(traitModel, traitView);

            int result = JOptionPane.showConfirmDialog(null, traitView.getPanel(), "Create Trait",JOptionPane.OK_CANCEL_OPTION);

            if(result == JOptionPane.OK_OPTION)
            {
                traitController.finalizeTrait();
                Trait newTrait = traitModel.getTrait();
                manager.getCurrentModel().addTrait(row, newTrait);
                System.out.println("Trait Added");
                SwingUtilities.getWindowAncestor((JButton) e.getSource()).repaint();
            }
            else
            {
                System.out.println("Cancelled");
            }
        }
    }

    /***************************************************************************
     * addListeners
     **************************************************************************/
    private void addListeners() {
        this.view.addIncrementListener(incrementListener);
        this.view.addListNameListener(listNameListener);

        this.view.addDebugListener(debugListener);

        Table_Model model = manager.getCurrentModel();
        model.addTableModelListener(tableModelListener);
    }

    /***************************************************************************
     * removeListeners
     *
     **************************************************************************/
    private void removeListeners() {
        //this.view.removeTraitButtonListener(traitButtonListener);
        this.view.removeIncrementListener(incrementListener);
        this.view.removeListNameListener(listNameListener);

        this.view.removeDebugListener(debugListener);
        Table_Model model = manager.getCurrentModel();
        model.removeTableModelListener(tableModelListener);
    }


}
