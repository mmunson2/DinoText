package DinoText_GUI.VIEW.Table;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    }

    /***************************************************************************
     * setTableModel
     *
     **************************************************************************/
    public void setTableModel(TableModel tableModel) {
        this.listTable.setModel(tableModel);
    }

    /***************************************************************************
     * setListName
     *
     **************************************************************************/
    public void setListName(String text) {
        this.listName.setText(text);
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
