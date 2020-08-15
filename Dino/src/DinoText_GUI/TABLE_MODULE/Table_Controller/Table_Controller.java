package DinoText_GUI.TABLE_MODULE.Table_Controller;

import Dino.FileTypes;
import Dino.List.ListEntry;
import Dino.List.Trait;
import Dino.ListParser;
import DinoText_GUI.DIALOGUE_MODULE.DialogueController.Dialogue_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignTab_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Probabilities;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Model;
import DinoText_GUI.TABLE_MODULE.Table_View.Table_TabbedPane;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_View;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;
import DinoText_GUI.TABLE_MODULE.TraitEditor.Editor_Controller;
import DinoText_GUI.TABLE_MODULE.TraitEditor.Editor_Model;
import DinoText_GUI.TABLE_MODULE.TraitEditor.Editor_View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private listener_editTraitButton editTraitButtonListener;
    private listener_firstListButton firstListButtonListener;

    private listener_debug debugListener;


    private Dialogue_Controller dialogue_controller;


    /***************************************************************************
     * Constructor
     *
     * Test
     **************************************************************************/
    public Table_Controller(Table_Manager manager, Table_TabbedPane view) {
        this.manager = manager;
        this.listNumber = 0;
        this.view = view;

        this.incrementListener = new listener_increment();
        this.listNameListener = new listener_listName();
        this.tableModelListener = new listener_tableModel();
        this.tabSwitchListener = new listener_tabSwitch();
        this.traitButtonListener = new listener_addTraitButton();
        this.editTraitButtonListener = new listener_editTraitButton();
        this.firstListButtonListener = new listener_firstListButton();

        this.debugListener = new listener_debug();

        this.view.addTabSwitchListener(tabSwitchListener);
        this.view.addNoListButtonListener(this.firstListButtonListener);

        //this.view.initializeAddTraitButtonColumn();
        //this.view.addTraitButtonListener(traitButtonListener);

       //this.view.setTableModel(model);
        //this.view.setButtonColumn(0);
    }

    public void setDialogue_controller(Dialogue_Controller controller) {
        this.dialogue_controller = controller;
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
     **************************************************************************/
    public void addList(String name, String[] entries) {

        //Check if the list already exists
        if (this.manager.hasActiveModel() && this.manager.hasList(name)) {
            //Not doing anything currently!
        }
        else //Otherwise make a whole new list
        {
            removeListeners();

            if(view.hasNoListTab())
            {
                view.removeNoListTab();
            }

            this.manager.addModel(name);
            this.view.addList(name);
            this.view.setTableModel(this.manager.getActiveModel());
            view.setEntryCount(manager.getActiveModel().getEntryCount());
            this.view.initializeAddTraitButtonColumn();
            this.view.initializeEditTraitButtonColumn();

            addListeners();

            this.view.addTraitButtonListener(traitButtonListener);
            this.view.addEditTraitButtonListener(editTraitButtonListener);
        }

        //If there are entries, add them
        if (entries != null) {
            for (int i = 0; i < entries.length; i++) {
                this.addEntry(entries[i]);
            }
        }
    }

    public String[] getListNames() {
        return this.manager.getListNames();
    }

    public void addEntry(String entry) {
        if(this.manager.hasActiveModel())
            this.manager.getActiveModel().addEntry(entry, 1.0);
    }

    public void addEntry(String entry, double weight) {
        if(this.manager.hasActiveModel())
            this.manager.getActiveModel().addEntry(entry, weight);
    }

    public void addEntry(ListEntry listEntry) {
        if(this.manager.hasActiveModel())
            this.manager.getActiveModel().addEntry(listEntry);
    }

    /***************************************************************************
     * rename List
     *
     **************************************************************************/
    public void renameList(String newName) {
        if(this.manager.hasActiveModel())
            this.manager.getActiveModel().setName(newName);
        if(this.view.hasActiveTable())
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

        if(dialogue_controller != null)
        {
            dialogue_controller.renameList(newName, oldName);
        }
    }


    /***************************************************************************
     * switchToIndex
     *
     **************************************************************************/
    public void switchToIndex(int index) {
        removeListeners();

        if(this.manager.hasActiveModel())
            this.manager.switchModel(index);

        if(this.view.hasActiveTable())
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

    public void writeAllToFile(File directory)
    {
        this.manager.writeToFile(directory);
    }


    /***************************************************************************
     * writeCurrentToFile
     *
     **************************************************************************/
    public void writeCurrentToFile() {
        if(this.manager.hasActiveModel())
            this.manager.getActiveModel().writeToFile();
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile(String name) {
        this.manager.writeToFile(name);
    }

    /***************************************************************************
     * openFile
     *
     **************************************************************************/
    public void openFile(String fileName) {
        openFile(fileName, null);
    }

    public void openFile(File file) {
        openFile(file.getName(), file);
    }

    public void openFile(String fileName, File file) {
        if (!FileTypes.hasListExtension(fileName)) {
            JOptionPane.showMessageDialog(null, "Could not open " + fileName);

            return;
        }
        ListParser parser = null;

        if (file == null) {
            parser = new ListParser(new File(fileName));
        } else {
            parser = new ListParser(file);
        }

        String name = parser.getName();

        if (manager.hasList(name)) {
            //Todo: Make this overwrite the existing list
            return;
        }

        this.addList(parser.getName());

        ListEntry[] entries = parser.getList();

        for (ListEntry entry : entries) {
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

            assert(view.hasActiveTable());
            assert(manager.hasActiveModel());

            manager.getActiveModel().addRow();
            view.setEntryCount(manager.getActiveModel().getEntryCount());
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
            assert(view.hasActiveTable());
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
            assert(view.hasActiveTable());

            renameList(view.getListName(), manager.getActiveModel().getName());
            renameList(view.getListName());
        }
    }

    class listener_debug implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            addList("New List");
        }
    }

    class listener_addTraitButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert(view.hasActiveTable());
            assert(manager.hasActiveModel());

            int row = view.getSelectedRow();
            Table_Probabilities probabilities =
                    manager.getActiveModel().getProbabilities();

            Creator_View traitView = new Creator_View();

            Creator_Model traitModel = new Creator_Model();
            traitModel.setProbabilities(probabilities);
            traitModel.setRowIndex(row);

            Creator_Controller traitController =
                    new Creator_Controller(traitModel, traitView);

            int result = JOptionPane.showConfirmDialog(null, traitView.getPanel(), "Create Trait", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                traitController.finalizeTrait();
                Trait newTrait = traitModel.getTrait();
                manager.getActiveModel().addTrait(row, newTrait);
                SwingUtilities.getWindowAncestor((JButton) e.getSource()).repaint();
            } else {
                //Cancelled Trait
            }
        }
    }

    class listener_editTraitButton implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            int row = view.getSelectedRow();
            Table_Probabilities probabilities =
                    manager.getActiveModel().getProbabilities();

            Trait[] traits = manager.getActiveModel().getTraitArray(row);

            Editor_View editorView = new Editor_View();

            Editor_Model editorModel = new Editor_Model(probabilities, row);

            Editor_Controller editorController = new Editor_Controller(
                    editorModel, editorView);

            if(traits != null && traits.length != 0)
            {
                editorController.setTraits(traits, probabilities, row);
            }

            int result = JOptionPane.showConfirmDialog(null,
                    editorView.getPanel(),
                    "Edit Trait",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null);

            if(result == JOptionPane.OK_OPTION)
            {
                Trait[] newTraits = editorController.getTraits();

                manager.getActiveModel().setTraits(row, newTraits);
                SwingUtilities.getWindowAncestor((JButton) e.getSource()).repaint();
            }
            else
            {
                //cancelled edits
            }
        }
    }

    class listener_firstListButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String listName = JOptionPane.showInputDialog(Dialogue_Controller.DYNAMICLISTNAME + " Name: ");

            dialogue_controller.insertionHelper(listName);
        }
    }


    /***************************************************************************
     * addListeners
     **************************************************************************/
    private void addListeners() {
        if(!view.hasActiveTable() || !manager.hasActiveModel())
            return;

        this.view.addIncrementListener(incrementListener);
        this.view.addListNameListener(listNameListener);

        this.view.addDebugListener(debugListener);

        Table_Model model = manager.getActiveModel();
        model.addTableModelListener(tableModelListener);
    }

    /***************************************************************************
     * removeListeners
     *
     **************************************************************************/
    private void removeListeners() {
        if(!view.hasActiveTable() || !manager.hasActiveModel())
            return;

        //this.view.removeTraitButtonListener(traitButtonListener);
        this.view.removeIncrementListener(incrementListener);
        this.view.removeListNameListener(listNameListener);

        this.view.removeDebugListener(debugListener);
        Table_Model model = manager.getActiveModel();
        model.removeTableModelListener(tableModelListener);
    }


}
