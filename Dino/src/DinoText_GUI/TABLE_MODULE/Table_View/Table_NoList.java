package DinoText_GUI.TABLE_MODULE.Table_View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Table with No List
 *
 ******************************************************************************/
public class Table_NoList {
    private JPanel panel1;
    private JButton createList;

    public static final String NAME = "No Lists";

    /***************************************************************************
     * Constructor
     **************************************************************************/
    Table_NoList()
    {

    }

    /***************************************************************************
     * Add Create List ActionListener
     *
     **************************************************************************/
    public void addCreateListActionListener(ActionListener listener)
    {
        this.createList.addActionListener(listener);
    }

    /***************************************************************************
     * Remove Create List ActionListener
     *
     **************************************************************************/
    public void removeCreateListActionListener(ActionListener listener)
    {
        this.createList.removeActionListener(listener);
    }

    /***************************************************************************
     * getPanel
     *
     **************************************************************************/
    public Component getPanel()
    {
        return this.panel1;
    }
}
