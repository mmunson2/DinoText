package DinoText_GUI.IhsanTestTable;

import DinoParser.List.Trait;
import DinoText_GUI.VIEW.TraitLabel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Table_TraitDisplay extends TraitLabel implements TableCellRenderer {
    public Table_TraitDisplay() {
        this.setOpaque(true);
    }

    TraitLabel[] labelTable;


    //Todo: This is the method where you handle a trait array or something similar
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        for (Trait trait : (Trait[]) value) {
            panel.add(new TraitLabel(trait.getName(), trait.getLowerBound(), trait.getUpperBound()));
        }

        TraitLabel temp = new TraitLabel("Trait", 10, 50);

        panel.add(temp);

        panel.add(new TraitLabel("Trait 2", 30, 94));


        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }

        return panel;
    }
}
