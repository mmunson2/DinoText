// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Variable_Setting_Box
{
    private JPanel panel;
    private JTextField box;
    private JLabel variableName;

    public Variable_Setting_Box(String name)
    {
        this.variableName.setText(name);
        this.panel.setMinimumSize(new Dimension(250, 50));
        this.panel.setMaximumSize(new Dimension(300, 50));
        this.panel.setPreferredSize(new Dimension(-1, 50));
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public String getValue() { return box.getText(); }

    public void boxListener(KeyListener keyListener) { box.addKeyListener(keyListener); }
}
