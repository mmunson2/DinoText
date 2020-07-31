// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import Dino.Dino;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class Trait_Setting_View extends JFrame
{
    private  JPanel content;

    private ArrayList<Trait_Setting_Slider> sliders;
    private Dino dino;

    public Dino getDino()
    {
        return this.dino;
    }

    public Trait_Setting_View(Dino dino)
    {
        this.dino = dino;
        this.sliders = new ArrayList<>();

        this.setContentPane(content);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setTitle("Trait Settings");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setPreferredSize(new Dimension(300, 300));

        for (int i = 0; i < this.dino.getTraitCount(); i++)
        {
            Trait_Setting_Slider tss = new Trait_Setting_Slider(this.dino.getTraitName(i), 0, 100);
            tss.sliderListener(new sliderListener(i));
            this.sliders.add(tss);
        }
        /*
        for (int i = 0; i < 20; i++)
        {
            Trait_Setting_Slider tss = new Trait_Setting_Slider("trait" + i, 0, 100);
            tss.sliderListener(new sliderListener(i));
            this.sliders.add(tss);
        }
        */

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        for (int i = 0; i < this.sliders.size(); i++)
        {
            panel.add(this.sliders.get(i).getPanel());
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(Box.createVerticalGlue());

        this.content.add(scroller, BorderLayout.CENTER);
    }

    class sliderListener implements ChangeListener
    {
        private int index;
        public sliderListener(int index)
        {
            this.index = index;
        }
        @Override
        public void stateChanged(ChangeEvent e)
        {
            //double value = (double) sliders.get(index).getValue() / sliders.get(index).getMax();
            double value = (double) sliders.get(index).getValue();
            dino.setTraitValue(index, value);
            //System.out.println("Changing trait " + index + " to " + value);
        }
    }
}
