package DinoText_GUI.CONTROLLER.Table;

import DinoText_GUI.MODEL.Table.TraitCreator.TraitCreator_Model;
import DinoText_GUI.VIEW.Table.Trait.TraitCreator_View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TraitCreator_Controller
{
    TraitCreator_Model model;
    TraitCreator_View view;

    traitName_listener traitListener;
    lowerBoundSlider_listener lowerBoundListener;
    upperBoundSlider_listener upperBoundListener;
    traitWeight_listener traitWeightListener;

    TraitCreator_Controller(TraitCreator_Model model, TraitCreator_View view)
    {
        this.model = model;
        this.view = view;

        this.traitListener = new traitName_listener();
        this.lowerBoundListener = new lowerBoundSlider_listener();
        this.upperBoundListener = new upperBoundSlider_listener();
        this.traitWeightListener = new traitWeight_listener();

        addListeners();

        this.view.setTraitName(this.model.getName());
        this.view.setLowerBoundSlider(this.model.getLowerBound());
        this.view.setUpperBoundSlider(this.model.getUpperBound());
        this.view.setTraitWeight(this.model.getWeight());
        this.view.setDisplayProbability(this.model.getProbability());
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
        }
    }


    private void addListeners()
    {
        this.view.addTraitNameListener(traitListener);
        this.view.addLowerBoundSliderListener(lowerBoundListener);
        this.view.addUpperBoundSliderListener(upperBoundListener);
        this.view.addWeightListener(traitWeightListener);
    }

    private void removeListeners()
    {
        this.view.removeTraitNameListener(traitListener);
        this.view.removeLowerBoundSliderListener(lowerBoundListener);
        this.view.removeUpperBoundSliderListener(upperBoundListener);
        this.view.removeWeightListener(traitWeightListener);
    }








}
