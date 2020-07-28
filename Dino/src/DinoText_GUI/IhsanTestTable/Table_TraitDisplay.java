package DinoText_GUI.IhsanTestTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table_TraitDisplay extends JButton implements TableCellRenderer
{
    public Table_TraitDisplay()
    {
        this.setOpaque(true);
    }

    //Todo: This is the method where you handle a trait array or something similar
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        this.setText((value == null) ? "" : value.toString());

        return this;
    }
}
