package DinoText_GUI.TABLE_MODULE.TraitEditor;

import DinoText_GUI.TABLE_MODULE.TraitCreator.Creator_View;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*******************************************************************************
 * Editor View
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Editor_View {
    private JTabbedPane traitTabs;
    private JPanel panel1;
    private JButton deleteTraitButton;
    private JButton NewTraitButton;

    private ArrayList<Creator_View> traits = new ArrayList<>();
    private Creator_View activeTrait;

    private Editor_NoTraits noTraits;

    /***************************************************************************
     * Constructor
     **************************************************************************/
    public Editor_View()
    {
        this.noTraits = new Editor_NoTraits();

        addNoTraitsTab();
    }

    /***************************************************************************
     * Add Trait
     **************************************************************************/
    public void addTrait(String name)
    {
        this.activeTrait = new Creator_View();
        this.activeTrait.setTraitName(name);

        this.traits.add(this.traits.size(), this.activeTrait);
        this.traitTabs.addTab(name, this.activeTrait.getPanel());
        this.traitTabs.setSelectedIndex(this.traits.size() - 1);
    }

    /***************************************************************************
     * Remove Active Trait
     **************************************************************************/
    public void removeActiveTrait()
    {
        int currentIndex = this.getSelectedIndex();
        traits.remove(currentIndex);
        traitTabs.removeTabAt(currentIndex);

        if(this.traits.size() == 0)
        {
            this.activeTrait = null;
        }
        else if(currentIndex > 0)
        {
            //Default is to go one tab left if possible
            currentIndex -= 1;
            this.switchList(currentIndex);
        }
        else
        {
            //Otherwise we go one tab to the right
            this.switchList(currentIndex);
        }
    }

    /***************************************************************************
     * Add No Traits Tab
     **************************************************************************/
    public void addNoTraitsTab()
    {
        this.traitTabs.addTab(Editor_NoTraits.NAME, this.noTraits.getPanel());
    }

    /***************************************************************************
     * Has No List Tab
     **************************************************************************/
    public boolean hasNoListTab()
    {
        int index = this.traitTabs.indexOfTab(Editor_NoTraits.NAME);

        return index != -1;
    }

    /***************************************************************************
     * Remove No List Tab
     **************************************************************************/
    public void removeNoListTab()
    {
        int index = this.traitTabs.indexOfTab(Editor_NoTraits.NAME);

        if(index != -1)
        {
            this.traitTabs.removeTabAt(index);
        }
    }

    /***************************************************************************
     * switchList
     *
     **************************************************************************/
    public void switchList(int index)
    {
        this.activeTrait= this.traits.get(index);
        this.traitTabs.setSelectedIndex(index);
    }

    /***************************************************************************
     *--------------------------------------------------------------------------
     *  ██████  ███████ ████████         ██     ███████ ███████ ████████ 
     * ██       ██         ██           ██      ██      ██         ██    
     * ██   ███ █████      ██          ██       ███████ █████      ██ 
     * ██    ██ ██         ██         ██             ██ ██         ██ 
     *  ██████  ███████    ██        ██         ███████ ███████    ██ 
     *--------------------------------------------------------------------------
     **************************************************************************/

    public void setListName(String text) {
        this.activeTrait.setTraitName(text);
        traitTabs.setTitleAt(this.getSelectedIndex(), text);
    }

    public int getSelectedIndex()
    {
        return this.traitTabs.getSelectedIndex();
    }

    public Component getPanel()
    {
        return this.panel1;
    }

    public Creator_View getActiveTrait()
    {
        return this.activeTrait;
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

    public void addTabSwitchListener(ChangeListener l)
    {
        this.traitTabs.addChangeListener(l);
    }

    public void removeTabSwitchListener(ChangeListener l)
    {
        this.traitTabs.removeChangeListener(l);
    }

    public void addNoTraitButtonListener(ActionListener l)
    {
        this.noTraits.addCreateTraitListener(l);
    }

    public void removeNoTraitButtonListener(ActionListener l)
    {
        this.noTraits.removeCreateTraitListener(l);
    }

    public void addNewTraitButtonListener(ActionListener l)
    {
        this.NewTraitButton.addActionListener(l);
    }

    public void removeNewTraitButtonListener(ActionListener l)
    {
        this.NewTraitButton.removeActionListener(l);
    }

    public void addDeleteTraitButtonListener(ActionListener l)
    {
        this.deleteTraitButton.addActionListener(l);
    }

    public void removeDeleteTraitButtonListener(ActionListener l)
    {
        this.deleteTraitButton.removeActionListener(l);
    }
}
