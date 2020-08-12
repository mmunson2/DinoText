package DinoText_GUI.TABLE_MODULE.TraitEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Editor_NoTraits {
    private JButton createTrait;
    private JPanel panel1;

    public static final String NAME = "No Traits";

    public Editor_NoTraits()
    {

    }

    public Component getPanel()
    {
        return this.panel1;
    }

    public void addCreateTraitListener(ActionListener l)
    {
        this.createTrait.addActionListener(l);
    }

    public void removeCreateTraitListener(ActionListener l)
    {
        this.createTrait.removeActionListener(l);
    }



}
