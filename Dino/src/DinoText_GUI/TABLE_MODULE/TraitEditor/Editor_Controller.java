package DinoText_GUI.TABLE_MODULE.TraitEditor;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Editor_Controller
{
    private Creator_Controller traitController;

    private Editor_Model model;
    private Editor_View view;

    private newTrait_listener newTraitListener;
    private tabSwitch_listener tabSwitchListener;

    public Editor_Controller(Editor_Model model, Editor_View view)
    {
        this.model = model;
        this.view = view;

        this.newTraitListener = new newTrait_listener();
        tabSwitchListener = new tabSwitch_listener();

        addListeners();
    }

    public void setTraits(Trait[] traits)
    {
        if(traits.length == 0)
        {
            return;
        }

        this.model = new Editor_Model(traits);

        if(this.view.hasNoListTab())
        {
            this.view.removeNoListTab();
        }

        for(int i = 0; i < this.model.getTraitCount(); i++)
        {
            this.view.addTrait(this.model.getTraitNameAt(i));
        }

        updateCreatorController();
    }

    public void addTrait()
    {
        this.model.addTrait();
        this.view.addTrait(this.model.getTraitNameAt(this.model.getTraitCount() - 1));

        updateCreatorController();

        if(this.view.hasNoListTab())
        {
            this.view.removeNoListTab();
        }
    }

    public void deleteTrait()
    {
        int traitCount = this.model.getTraitCount();

        assert(traitCount != 0);

        if(traitCount == 1)
        {


        }
        else
        {


        }




    }

    public void switchTab(int tabIndex)
    {
        assert(tabIndex > 0 && tabIndex < this.model.getTraitCount());

        this.model.switchModel(tabIndex);
        this.view.switchList(tabIndex);

        updateCreatorController();
    }

    public Trait[] getTraits()
    {
        return this.model.getTraits();
    }

    private class tabSwitch_listener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            switchTab(view.getSelectedIndex());
        }
    }

    private class newTrait_listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            addTrait();
        }
    }

    private class deleteTrait_listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private void updateCreatorController()
    {
        if(this.traitController == null)
        {
            this.traitController = new Creator_Controller(this.model.getActiveModel(), this.view.getActiveTrait());
        }
        else
        {
            this.traitController.set(this.model.getActiveModel(), this.view.getActiveTrait());
        }
    }

    private void addListeners()
    {
        this.view.addNoTraitButtonListener(this.newTraitListener);
        this.view.addTabSwitchListener(this.tabSwitchListener);
        this.view.addNewTraitButtonListener(this.newTraitListener);
    }

    private void removeListeners()
    {
        this.view.removeNoTraitButtonListener(this.newTraitListener);
        this.view.removeTabSwitchListener(this.tabSwitchListener);
        this.view.removeNewTraitButtonListener(this.newTraitListener);
    }


}
