package DinoText_GUI.TABLE_MODULE.TraitEditor;

import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Probabilities;
import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_Controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Editor Controller
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Editor_Controller
{
    private Creator_Controller traitController;

    private Editor_Model model;
    private Editor_View view;

    private newTrait_listener newTraitListener;
    private tabSwitch_listener tabSwitchListener;
    private traitNameChange_listener traitNameChangeListener;
    private deleteTrait_listener deleteTraitListener;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Editor_Controller(Editor_Model model, Editor_View view)
    {
        this.model = model;
        this.view = view;

        this.newTraitListener = new newTrait_listener();
        this.tabSwitchListener = new tabSwitch_listener();
        this.traitNameChangeListener = new traitNameChange_listener();
        this.deleteTraitListener = new deleteTrait_listener();

        addListeners();
    }

    /***************************************************************************
     * Set Traits
     **************************************************************************/
    public void setTraits(Trait[] traits, Table_Probabilities probabilities, int rowIndex)
    {
        if(traits.length == 0)
        {
            return;
        }

        this.model = new Editor_Model(traits, probabilities, rowIndex);

        for(int i = 0; i < this.model.getTraitCount(); i++)
        {
            this.view.addTrait(this.model.getTraitNameAt(i));
        }

        updateCreatorController();

        if(this.view.hasNoListTab())
        {
            this.view.removeNoListTab();
        }
    }

    /***************************************************************************
     * Add Traits
     **************************************************************************/
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

    /***************************************************************************
     * Delete Trait
     **************************************************************************/
    public void deleteTrait()
    {
        int traitCount = this.model.getTraitCount();

        if(traitCount < 1)
            return;

        if(traitCount == 1)
        {
            this.view.addNoTraitsTab();
            this.traitController = null;
        }

        this.model.removeActiveTrait();
        this.view.removeActiveTrait();

        if(traitCount > 1)
        {
            this.updateCreatorController();
        }
    }

    /***************************************************************************
     * Switch Tab
     **************************************************************************/
    public void switchTab(int tabIndex)
    {
        if(this.view.hasNoListTab())
            return;

        this.model.switchModel(tabIndex);
        this.view.switchList(tabIndex);

        updateCreatorController();
    }

    /***************************************************************************
     * Get Traits
     **************************************************************************/
    public Trait[] getTraits()
    {
        return this.model.getTraits();
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

    /***************************************************************************
     * Inner Class - Tab Switch Listener
     **************************************************************************/
    private class tabSwitch_listener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            switchTab(view.getSelectedIndex());
        }
    }

    /***************************************************************************
     * Inner Class - New Trait Listener
     **************************************************************************/
    private class newTrait_listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            addTrait();
        }
    }

    /***************************************************************************
     * Inner Class - Delete Trait Listener
     **************************************************************************/
    private class deleteTrait_listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            deleteTrait();
        }
    }

    /***************************************************************************
     * Inner Class - Name Change Listener
     **************************************************************************/
    private class traitNameChange_listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String newName = view.getActiveTrait().getTraitName();
            view.setListName(newName);
        }
    }

    /***************************************************************************
     * Update Creator Controller
     **************************************************************************/
    private void updateCreatorController()
    {
        if(this.traitController == null)
        {
            this.traitController = new Creator_Controller(this.model.getActiveModel(), this.view.getActiveTrait());
            this.traitController.addNameChangeListener(this.traitNameChangeListener);
        }
        else
        {
            this.traitController.removeNameChangeListener(this.traitNameChangeListener);
            this.traitController.set(this.model.getActiveModel(), this.view.getActiveTrait());
            this.traitController.addNameChangeListener(this.traitNameChangeListener);
        }
    }

    /***************************************************************************
     * Add Listeners
     **************************************************************************/
    private void addListeners()
    {
        this.view.addNoTraitButtonListener(this.newTraitListener);
        this.view.addTabSwitchListener(this.tabSwitchListener);
        this.view.addNewTraitButtonListener(this.newTraitListener);
        this.view.addDeleteTraitButtonListener(this.deleteTraitListener);
    }

    /***************************************************************************
     * Remove Listeners
     **************************************************************************/
    private void removeListeners()
    {
        this.view.removeNoTraitButtonListener(this.newTraitListener);
        this.view.removeTabSwitchListener(this.tabSwitchListener);
        this.view.removeNewTraitButtonListener(this.newTraitListener);
        this.view.removeDeleteTraitButtonListener(this.deleteTraitListener);
    }


}
