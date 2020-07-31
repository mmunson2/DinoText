package DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.Table_Buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Table Button Model
 *
 ******************************************************************************/
public class TableButton_Model extends DefaultCellEditor
{
    protected JButton button;
    private String label;
    private boolean isPushed;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public TableButton_Model(JCheckBox checkBox)
    {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
    }

    /***************************************************************************
     * Get Table Cell Editor Component
     *
     **************************************************************************/
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (isSelected) {
            this.button.setForeground(table.getSelectionForeground());
            this.button.setBackground(table.getSelectionBackground());
        } else {
            this.button.setForeground(table.getForeground());
            this.button.setBackground(table.getBackground());
        }
        this.label = (value == null) ? "" : value.toString();
        this.button.setText(label);
        this.isPushed = true;
        return button;
    }

    /***************************************************************************
     * Get Cell Editor Value
     *
     **************************************************************************/
    @Override
    public Object getCellEditorValue()
    {
        if (isPushed) {
            //Not doing anything here currently, handled by ActionListener
        }
        isPushed = false;
        return label;
    }

    /***************************************************************************
     * Stop Cell Editing
     *
     **************************************************************************/
    @Override
    public boolean stopCellEditing()
    {
        isPushed = false;
        return super.stopCellEditing();
    }

    /***************************************************************************
     * Add Button Listener
     *
     **************************************************************************/
    public void addButtonListener(ActionListener l)
    {
        this.button.addActionListener(l);
    }

    /***************************************************************************
     * Remove Button Listener
     *
     **************************************************************************/
    public void removeButtonListener(ActionListener l)
    {
        this.button.removeActionListener(l);
    }

    /***************************************************************************
     * Set Label
     *
     **************************************************************************/
    public void setLabel(String label)
    {
        this.label = label;
    }


}
