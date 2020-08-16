package DinoText_GUI.TABLE_MODULE.TraitCreator;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

/*******************************************************************************
 * Creator View
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Creator_View
{
    private JTextField traitName;
    private JSlider lowerBoundSlider;
    private JSlider upperBoundSlider;
    private JTextField traitWeight;
    private JPanel panel;
    private JTextField probabilityDisplay;
    private JSpinner upperBoundSpinner;
    private JSpinner lowerBoundSpinner;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Creator_View()
    {
        this.probabilityDisplay.setEditable(false);
    }

    /***************************************************************************
     *--------------------------------------------------------------------------
     * ███████ ███████ ████████ ████████ ███████ ██████  ███████ 
     * ██      ██         ██       ██    ██      ██   ██ ██      
     * ███████ █████      ██       ██    █████   ██████  ███████ 
     *      ██ ██         ██       ██    ██      ██   ██      ██ 
     * ███████ ███████    ██       ██    ███████ ██   ██ ███████ 
     *--------------------------------------------------------------------------
     **************************************************************************/

    public void setTraitName(String name)
    {
        this.traitName.setText(name);
    }

    public void setLowerBoundSlider(int value)
    {
        this.lowerBoundSlider.setValue(value);
    }

    public void setLowerBoundSpinner(int value)
    {
        this.lowerBoundSpinner.setValue(value);
    }

    public void setUpperBoundSlider(int value)
    {
        this.upperBoundSlider.setValue(value);
    }

    public void setUpperBoundSpinner(int value)
    {
        this.upperBoundSpinner.setValue(value);
    }

    public void setTraitWeight(double weight)
    {
        this.traitWeight.setText("" + weight);
    }

    public void setDisplayProbability(double probability)
    {
        String probabilityString = String.format("%2.2f%%", probability);

        this.probabilityDisplay.setText(probabilityString);
    }

    /***************************************************************************
     *--------------------------------------------------------------------------
     *  ██████  ███████ ████████ ████████ ███████ ██████  ███████ 
     * ██       ██         ██       ██    ██      ██   ██ ██      
     * ██   ███ █████      ██       ██    █████   ██████  ███████ 
     * ██    ██ ██         ██       ██    ██      ██   ██      ██ 
     *  ██████  ███████    ██       ██    ███████ ██   ██ ███████ 
     *--------------------------------------------------------------------------
     **************************************************************************/
    public JPanel getPanel()
    {
        return this.panel;
    }

    public String getTraitName()
    {
        return this.traitName.getText();
    }

    public int getLowerBoundSliderValue()
    {
        return this.lowerBoundSlider.getValue();
    }

    public Object getLowerBoundSpinner()
    {
        return this.lowerBoundSpinner.getValue();
    }

    public int getUpperBoundSliderValue()
    {
        return this.upperBoundSlider.getValue();
    }

    public Object getUpperBoundSpinner()
    {
        return this.upperBoundSpinner.getValue();
    }

    public String getTraitWeight()
    {
        return this.traitWeight.getText();
    }

    /***************************************************************************
     *--------------------------------------------------------------------------
     * ██      ██ ███████ ████████ ███████ ███    ██ ███████ ██████  ███████ 
     * ██      ██ ██         ██    ██      ████   ██ ██      ██   ██ ██      
     * ██      ██ ███████    ██    █████   ██ ██  ██ █████   ██████  ███████ 
     * ██      ██      ██    ██    ██      ██  ██ ██ ██      ██   ██      ██ 
     * ███████ ██ ███████    ██    ███████ ██   ████ ███████ ██   ██ ███████ 
     *--------------------------------------------------------------------------
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

    public void addLowerBoundSpinnerListener(ChangeListener l)
    {
        this.lowerBoundSpinner.addChangeListener(l);
    }

    public void removeLowerBoundSpinnerListener(ChangeListener l)
    {
        this.lowerBoundSpinner.removeChangeListener(l);
    }

    public void addUpperBoundSliderListener(ChangeListener l)
    {
        this.upperBoundSlider.addChangeListener(l);
    }

    public void removeUpperBoundSliderListener(ChangeListener l)
    {
        this.upperBoundSlider.removeChangeListener(l);
    }

    public void addUpperBoundSpinnerListener(ChangeListener l)
    {
        this.upperBoundSpinner.addChangeListener(l);
    }

    public void removeUpperBoundSpinnerListener(ChangeListener l)
    {
        this.upperBoundSpinner.removeChangeListener(l);
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
