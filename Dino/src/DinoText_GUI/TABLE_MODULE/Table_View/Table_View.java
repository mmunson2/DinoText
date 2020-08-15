package DinoText_GUI.TABLE_MODULE.Table_View;

import DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.Table_Buttons.TableButton_View;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.Table_Buttons.TableButton_Model;
import DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.TraitDisplay.Table_TraitDisplay;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Table View
 *
 ******************************************************************************/
public class Table_View {

    private JTable listTable;
    private JTextField listName;
    private JLabel entryCount;
    private JButton increment;
    private JPanel panel1;
    private JButton debugButton;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_View()
    {
        this.listTable.getTableHeader().setOpaque(false);
        this.listTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        this.listTable.setShowHorizontalLines(true);
        this.listTable.setGridColor(Color.DARK_GRAY);
        this.listTable.setRowHeight(30);

        this.listTable.setColumnSelectionAllowed(false);
        this.listTable.setRowSelectionAllowed(false);
        this.listTable.setDragEnabled(false);
        this.listTable.getTableHeader().setReorderingAllowed(false);
    }

    /***************************************************************************
     * setTableModel
     *
     **************************************************************************/
    public void setTableModel(TableModel tableModel) {
        this.listTable.setModel(tableModel);

        // Set individual column widths
        int listEntry = DesignColumns.ENTRY_NAME.ordinal();
        int weight = DesignColumns.PROBABILITY_WEIGHT.ordinal();
        int probability = DesignColumns.PROBABILITY.ordinal();
        int addTrait = DesignColumns.ADD_TRAIT.ordinal();
        int editTrait = DesignColumns.EDIT_TRAIT.ordinal();
        int traitDisplay = DesignColumns.TRAIT.ordinal();

        this.listTable.getColumnModel().getColumn(listEntry).setPreferredWidth(350);
        this.listTable.getColumnModel().getColumn(weight).setPreferredWidth(120);
        this.listTable.getColumnModel().getColumn(probability).setPreferredWidth(78);
        this.listTable.getColumnModel().getColumn(addTrait).setPreferredWidth(100);
        this.listTable.getColumnModel().getColumn(editTrait).setPreferredWidth(100);
        this.listTable.getColumnModel().getColumn(traitDisplay).setPreferredWidth(200);
    }

    /***************************************************************************
     * get JTable
     **************************************************************************/
    public JTable getJTable()
    {
        return listTable;
    }

    /***************************************************************************
     * setListName
     *
     **************************************************************************/
    public void setListName(String text) {
        this.listName.setText(text);
    }


    public void initializeAddTraitButtonColumn()
    {
        this.listTable.getColumn(DesignColumns.ADD_TRAIT.header).setCellRenderer(new TableButton_View());
        this.listTable.getColumn(DesignColumns.TRAIT.header).setCellRenderer(new Table_TraitDisplay());
        this.listTable.getColumn(DesignColumns.ADD_TRAIT.header).setCellEditor(new TableButton_Model(new JCheckBox()));
    }

    public void initializeEditTraitButtonColumn()
    {
        this.listTable.getColumn(DesignColumns.EDIT_TRAIT.header).setCellRenderer(new TableButton_View());
        this.listTable.getColumn(DesignColumns.EDIT_TRAIT.header).setCellEditor(new TableButton_Model(new JCheckBox()));
    }

    /***************************************************************************
     * getEntryCount
     *
     **************************************************************************/
    public int getEntryCount() {
        return Integer.parseInt(entryCount.getText());
    }

    /***************************************************************************
     * setEntryCount
     *
     **************************************************************************/
    public void setEntryCount(int count) {
        this.entryCount.setText("" + count);
    }

    /***************************************************************************
     * getListName
     *
     **************************************************************************/
    public String getListName() {
        return this.listName.getText();
    }

    public int getSelectedRow()
    {
        return this.listTable.getSelectedRow();
    }


    /***************************************************************************
     * addIncrementListener
     *
     **************************************************************************/
    public void addIncrementListener(ActionListener l) {
        this.increment.addActionListener(l);
    }

    /***************************************************************************
     * removeIncrementListener
     *
     **************************************************************************/
    public void removeIncrementListener(ActionListener l)
    {
        this.increment.removeActionListener(l);
    }

    /***************************************************************************
     * addListNameListener
     *
     **************************************************************************/
    public void addListNameListener(ActionListener l) {
        this.listName.addActionListener(l);
    }

    /***************************************************************************
     * removeListNameListener
     *
     **************************************************************************/
    public void removeListNameListener(ActionListener l)
    {
        this.listName.removeActionListener(l);
    }

    public void addDebugListener(ActionListener l)
    {
        this.debugButton.addActionListener(l);
    }

    public void removeDebugListener(ActionListener l)
    {
        this.debugButton.removeActionListener(l);
    }

    public void addTraitButtonListener(ActionListener l)
    {
        TableButton_Model model = null;

        try {
            model = (TableButton_Model) this.listTable.getColumn(DesignColumns.ADD_TRAIT.header).getCellEditor();
        }
        catch(Exception e)
        {
            System.err.println("Error in addTraitButtonListener :(");
            //Todo: Remove if we're no longer getting errors
        }

        if(model != null)
            model.addButtonListener(l);

    }

    public void removeTraitButtonListener(ActionListener l)
    {
        TableColumn column = null;

        try {
            column = this.listTable.getColumn(DesignColumns.ADD_TRAIT.header);
        }
        catch(IllegalArgumentException e)
        {
            System.err.println("Error in removeTraitButtonListener :(");
            //Todo: Remove if we're no longer getting errors
        }
        if(column != null)
        {
            TableButton_Model model = (TableButton_Model) column.getCellEditor();
            model.removeButtonListener(l);
        }
    }

    public void addEditTraitButtonListener(ActionListener l)
    {
        TableButton_Model model = null;

        try {
            model = (TableButton_Model) this.listTable.getColumn(DesignColumns.EDIT_TRAIT.header).getCellEditor();
        }
        catch(Exception e)
        {
            System.err.println("Error in addTraitButtonListener :(");
            //Todo: Remove if we're no longer getting errors
        }

        if(model != null)
            model.addButtonListener(l);

    }

    public void removeEditTraitButtonListener(ActionListener l)
    {
        TableColumn column = null;

        try {
            column = this.listTable.getColumn(DesignColumns.EDIT_TRAIT.header);
        }
        catch(IllegalArgumentException e)
        {
            System.err.println("Error in removeTraitButtonListener :(");
            //Todo: Remove if we're no longer getting errors
        }
        if(column != null)
        {
            TableButton_Model model = (TableButton_Model) column.getCellEditor();
            model.removeButtonListener(l);
        }
    }






    /***************************************************************************
     * Update Table
     **************************************************************************/
    public void updateTable() {
        this.listTable.updateUI();
    }

    /***************************************************************************
     * get Panel
     **************************************************************************/
    public Component getPanel()
    {
        return this.panel1;
    }



}
