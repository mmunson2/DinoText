// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import Dino.Dino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Variable_Setting_View extends JFrame
{
    private JPanel content;

    private ArrayList<Variable_Setting_Box> boxes;
    private Dino dino;

    public Variable_Setting_View(Dino dino)
    {
        this.setContentPane(content);
        this.setAlwaysOnTop(true);
        this.setSize(300, 300);
        this.setTitle("Variable Settings");

        this.dino = dino;
        this.boxes = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setPreferredSize(new Dimension(300, 300));

        // build the boxes array from the dino object
        for (int i = 0; i < this.dino.getStaticVariableCount(); i++)
        {
            Variable_Setting_Box box = new Variable_Setting_Box(this.dino.getStaticVariableName(i));
            box.boxListener(new boxListener(i));
            this.boxes.add(box);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        for (int i = 0; i < this.boxes.size(); i++)
        {
            panel.add(this.boxes.get(i).getPanel());
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        this.content.add(scroller, BorderLayout.CENTER);
    }

    class boxListener implements KeyListener
    {
        private int index;
        public boxListener(int index)
        {
            this.index = index;
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e)
        {
            String value = boxes.get(index).getValue();
            dino.setStaticVariable(index, value);
        }
    }
}
