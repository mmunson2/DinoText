package DinoText_GUI.TABLE_MODULE.Table_View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Table_NoList {
    private JPanel panel1;
    private JButton createList;

    public static final String NAME = "No Lists";

    Table_NoList()
    {

    }

    public void addCreateListActionListener(ActionListener listener)
    {
        this.createList.addActionListener(listener);
    }

    public void removeCreateListActionListener(ActionListener listener)
    {
        this.createList.removeActionListener(listener);
    }

    public Component getPanel()
    {
        return this.panel1;
    }
}
