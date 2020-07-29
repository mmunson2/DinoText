package DinoText_GUI.VIEW.Table;

import DinoText_GUI.MODEL.Table.Columns;
import DinoText_GUI.MODEL.Table.TableButton_Model;

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
    }

    /***************************************************************************
     * setTableModel
     *
     **************************************************************************/
    public void setTableModel(TableModel tableModel) {
        this.listTable.setModel(tableModel);
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
        try {
            this.listTable.getColumn(Columns.ADD_TRAIT.header).setCellRenderer(new TableButton_View());
            this.listTable.getColumn(Columns.ADD_TRAIT.header).setCellEditor(new TableButton_Model(new JCheckBox()));
        }
        catch(Exception e)
        {

        }

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
            model = (TableButton_Model) this.listTable.getColumn(Columns.ADD_TRAIT.header).getCellEditor();
        }
        catch(Exception e)
        {
            
        }

        if(model == null)
        {
            System.err.println("Model is null :/");
        }
        else
        {
            model.addButtonListener(l);
        }
    }

    public void removeTraitButtonListener(ActionListener l)
    {
        System.out.println("Column count: " + this.listTable.getColumnCount());

        TableColumn column = null;

        try {
            column = this.listTable.getColumn(Columns.ADD_TRAIT.header);
        }
        catch(IllegalArgumentException e)
        {
        }

        if(column == null)
        {
            System.err.println("Column is null :/");
            return;
        }

        TableButton_Model model = (TableButton_Model) column.getCellEditor();

        model.removeButtonListener(l);
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
