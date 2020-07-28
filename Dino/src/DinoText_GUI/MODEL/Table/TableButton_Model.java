package DinoText_GUI.MODEL.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationInstantiator;

public class TableButton_Model extends DefaultCellEditor
{
    protected JButton button;
    private String label;
    private boolean isPushed;

    public TableButton_Model(JCheckBox checkBox)
    {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
    }

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

    @Override
    public Object getCellEditorValue()
    {
        if (isPushed) {
            //Not doing anything here currently, handled by ActionListener
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing()
    {
        isPushed = false;
        return super.stopCellEditing();
    }

    public void addButtonListener(ActionListener l)
    {
        this.button.addActionListener(l);
    }

    public void removeButtonListener(ActionListener l)
    {
        this.button.removeActionListener(l);
    }

    public void setLabel(String label)
    {
        this.label = label;
    }


}
