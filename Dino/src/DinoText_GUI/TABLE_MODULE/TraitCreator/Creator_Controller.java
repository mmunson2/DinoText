package DinoText_GUI.TABLE_MODULE.TraitCreator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Creator_Controller
{
    Creator_Model model;
    Creator_View view;

    traitName_listener traitListener;
    lowerBoundSlider_listener lowerBoundListener;
    upperBoundSlider_listener upperBoundListener;
    traitWeight_listener traitWeightListener;
    traitName_focusListener traitNameFocusListener;


    public Creator_Controller(Creator_Model model, Creator_View view)
    {
        this.model = model;
        this.view = view;

        this.traitListener = new traitName_listener();
        this.lowerBoundListener = new lowerBoundSlider_listener();
        this.upperBoundListener = new upperBoundSlider_listener();
        this.traitWeightListener = new traitWeight_listener();
        this.traitNameFocusListener = new traitName_focusListener();

        addListeners();

        this.view.setTraitName(this.model.getName());
        this.view.setLowerBoundSlider(this.model.getLowerBound());
        this.view.setUpperBoundSlider(this.model.getUpperBound());
        this.view.setTraitWeight(this.model.getWeight());
        this.view.setDisplayProbability(this.model.getProbability());
    }

    public void finalizeTrait() {
        String traitName = view.getTraitName();
        model.setName(traitName);
        view.setTraitName(traitName);
    }

    class traitName_listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String traitName = view.getTraitName();

            model.setName(traitName);
            view.setTraitName(traitName);
        }
    }

    class lowerBoundSlider_listener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int newValue = view.getLowerBoundSliderValue();

            if(newValue > model.getUpperBound())
            {
                newValue = model.getUpperBound();
            }

            model.setLowerBound(newValue);
            view.setLowerBoundSlider(newValue);
        }
    }

    class upperBoundSlider_listener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int newValue = view.getUpperBoundSliderValue();

            if(newValue < model.getLowerBound())
            {
                newValue = model.getLowerBound();
            }

            model.setUpperBound(newValue);
            view.setUpperBoundSlider(newValue);
        }
    }

    class traitWeight_listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String valueString = view.getTraitWeight();
            double value;

            try
            {
                value = Double.parseDouble(valueString);
            }
            catch(NumberFormatException exception)
            {
                value = 0.0;
            }

            model.setWeight(value);
            view.setTraitWeight(value);
            view.setDisplayProbability(model.getProbability());
        }
    }


    private class traitName_focusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField textField = (JTextField) e.getSource();
            textField.select(0, textField.getText().length());
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }


    private void addListeners()
    {
        this.view.addTraitNameListener(traitListener);
        this.view.addLowerBoundSliderListener(lowerBoundListener);
        this.view.addUpperBoundSliderListener(upperBoundListener);
        this.view.addWeightListener(traitWeightListener);
        this.view.addTraitNameFocusListener(traitNameFocusListener);
    }

    private void removeListeners()
    {
        this.view.removeTraitNameListener(traitListener);
        this.view.removeLowerBoundSliderListener(lowerBoundListener);
        this.view.removeUpperBoundSliderListener(upperBoundListener);
        this.view.removeWeightListener(traitWeightListener);
    }

}
