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

    public Trait_Setting_View(Dino dino)
    {
        this.setContentPane(content);
        this.setSize(300, 300);
        this.setTitle("Trait Settings");

        this.dino = dino;
        this.sliders = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setPreferredSize(new Dimension(300, 300));

        // build the sliders array from the dino object
        for (int i = 0; i < this.dino.getTraitCount(); i++)
        {
            int value = (int) this.dino.getTraitValue(i);
            Trait_Setting_Slider tss = new Trait_Setting_Slider(this.dino.getTraitName(i), 0, 100, value);
            tss.sliderListener(new sliderListener(i));
            tss.spinnerListener(new spinnerListener(i));
            this.sliders.add(tss);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        for (int i = 0; i < this.sliders.size(); i++)
        {
            panel.add(this.sliders.get(i).getPanel());
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
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
            double value = sliders.get(index).getSliderValue();
            sliders.get(index).setSpinner((int) value);
            dino.setTraitValue(index, value);
        }
    }

    class spinnerListener implements ChangeListener
    {
        private int index;
        public spinnerListener(int index)
        {
            this.index = index;
        }
        @Override
        public void stateChanged(ChangeEvent e)
        {
            double value = sliders.get(index).getSpinnerValue();
            if (value < 0)
            {
                value = 0;
                sliders.get(index).setSpinner(0);
            }
            if (value > 100)
            {
                value = 100;
                sliders.get(index).setSpinner(100);
            }
            sliders.get(index).setSlider((int) value);
            dino.setTraitValue(index, value);
        }
    }
}
