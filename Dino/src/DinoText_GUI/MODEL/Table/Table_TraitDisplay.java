package DinoText_GUI.MODEL.Table;

import DinoParser.List.Trait;
import DinoText_GUI.MODEL.DinoList;
import DinoText_GUI.MODEL.Table.Traits.TraitModel;
import DinoText_GUI.VIEW.Table.TraitLabel;

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

    /***************************************************************************
     * Update Trait Display
     *
     **************************************************************************/
    public static void updateTraitDisplay(Table_Model model) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        DinoList list = model.list;

        for (int i = 0; i < list.size(); i++) {

            if (list.getTraits(i) != null) {
                for (Trait t :list.getTraits(i)) {
                    TraitModel trait = new TraitModel(t);
                    panel.add(new TraitLabel(trait.getName(), trait.getLowerBound(), trait.getUpperBound()));
                }
            }
        }
    }
}
