package DinoText_GUI.VIEW.Table.Trait;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class TraitCreator_View {
    private JTextField traitName;
    private JSlider lowerBoundSlider;
    private JSlider upperBoundSlider;
    private JTextField traitWeight;
    private JLabel DisplayProbability;
    private JPanel panel;

    public TraitCreator_View()
    {

    }
    public JPanel getPanel()
    {
        return this.panel;
    }


    /***************************************************************************
     * GETTERS / SETTERS
     **************************************************************************/

    public void setTraitName(String name)
    {
        this.traitName.setText(name);
    }

    public String getTraitName()
    {
        return this.traitName.getText();
    }

    public void setLowerBoundSlider(int value)
    {
        this.lowerBoundSlider.setValue(value);
    }

    public int getLowerBoundSliderValue()
    {
        return this.lowerBoundSlider.getValue();
    }

    public void setUpperBoundSlider(int value)
    {
        this.upperBoundSlider.setValue(value);
    }

    public int getUpperBoundSliderValue()
    {
        return this.upperBoundSlider.getValue();
    }

    public void setTraitWeight(double weight)
    {
        this.traitWeight.setText("" + weight);
    }

    public String getTraitWeight()
    {
        return this.traitWeight.getText();
    }

    public void setDisplayProbability(double probability)
    {
        this.DisplayProbability.setText(probability + "%");
    }


    /***************************************************************************
     * LISTENERS
     **************************************************************************/
    public void addTraitNameListener(ActionListener l)
    {
        this.traitName.addActionListener(l);
    }

    public void removeTraitNameListener(ActionListener l)
    {
        this.traitName.removeActionListener(l);
    }

    public void addLowerBoundSliderListener(ChangeListener l)
    {
        this.lowerBoundSlider.addChangeListener(l);
    }

    public void removeLowerBoundSliderListener(ChangeListener l)
    {
        this.lowerBoundSlider.removeChangeListener(l);
    }

    public void addUpperBoundSliderListener(ChangeListener l)
    {
        this.upperBoundSlider.addChangeListener(l);
    }

    public void removeUpperBoundSliderListener(ChangeListener l)
    {
        this.upperBoundSlider.removeChangeListener(l);
    }

    public void addWeightListener(ActionListener l)
    {
        this.traitWeight.addActionListener(l);
    }

    public void removeWeightListener(ActionListener l)
    {
        this.traitWeight.removeActionListener(l);
    }








}
