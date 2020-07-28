package DinoText_GUI.MODEL.Table;

import DinoParser.List.Trait;
import DinoText_GUI.MODEL.Traits.TraitModel;
import DinoText_GUI.VIEW.TraitLabel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table_TraitDisplay extends TraitLabel implements TableCellRenderer {
    public Table_TraitDisplay() {
        this.setOpaque(true);
    }

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
