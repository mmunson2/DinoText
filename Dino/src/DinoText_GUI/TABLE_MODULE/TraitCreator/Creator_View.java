package DinoText_GUI.TABLE_MODULE.TraitCreator;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

public class Creator_View {
    private JTextField traitName;
    private JSlider lowerBoundSlider;
    private JSlider upperBoundSlider;
    private JTextField traitWeight;
    private JPanel panel;
    private JTextField probabilityDisplay;

    public Creator_View()
    {
        this.probabilityDisplay.setEditable(false);
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
        String probabilityString = String.format("%2.2f%%", probability);

        this.probabilityDisplay.setText(probabilityString);
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

    public void addTraitNameFocusListener(FocusListener l) { this.traitName.addFocusListener(l); }








}
