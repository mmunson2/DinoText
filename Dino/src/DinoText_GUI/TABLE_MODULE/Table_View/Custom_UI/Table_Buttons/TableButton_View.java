package DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.Table_Buttons;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


/*******************************************************************************
 * Table Button Model
 *
 ******************************************************************************/
public class TableButton_View extends JButton implements TableCellRenderer
{
    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public TableButton_View()
    {
        this.setOpaque(true);
    }

    /***************************************************************************
     * Get Table Cell Renderer Component
     *
     **************************************************************************/
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
