package DinoText_GUI.TABLE_MODULE.TraitEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Editor View - No Traits Display
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Editor_NoTraits {
    private JButton createTrait;
    private JPanel panel1;

    public static final String NAME = "No Traits";

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Editor_NoTraits() {}

    /***************************************************************************
     * Get Panel
     **************************************************************************/
    public Component getPanel()
    {
        return this.panel1;
    }

    /***************************************************************************
     * Add Create Trait Listener
     **************************************************************************/
    public void addCreateTraitListener(ActionListener l)
    {
        this.createTrait.addActionListener(l);
    }

    /***************************************************************************
     * Remove Create Trait Listener
     **************************************************************************/
    public void removeCreateTraitListener(ActionListener l)
    {
        this.createTrait.removeActionListener(l);
    }
}
