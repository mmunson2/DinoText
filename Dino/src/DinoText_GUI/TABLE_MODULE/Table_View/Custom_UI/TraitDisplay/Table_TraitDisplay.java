package DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.TraitDisplay;

import Dino.List.Trait;
import DinoText_GUI.Util.DinoList;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Model;
import DinoText_GUI.Util.TraitModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/*******************************************************************************
 * Table Trait Display
 *
 ******************************************************************************/
public class Table_TraitDisplay extends TraitLabel implements TableCellRenderer {

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Table_TraitDisplay() {
        this.setOpaque(true);
    }


    /***************************************************************************
     * Get Table Cell Renderer Component
     *
     **************************************************************************/
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Trait[] traits = (Trait[]) value;

        if (traits != null) {
            for (Trait t : traits) {
                TraitModel trait = new TraitModel(t);
                panel.add(new TraitLabel(trait.getName(), trait.getLowerBound(), trait.getUpperBound()));
            }
        }

        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }

        return panel;
    }
}
