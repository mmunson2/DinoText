package DinoText_GUI.TABLE_MODULE.Table_View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Table_NoList {
    private JPanel panel1;
    private JButton createList;

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
}
