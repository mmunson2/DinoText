package DinoText_GUI.TABLE_MODULE.Table_Model;

import Dino.List.ListEntry;
import Dino.List.Trait;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Data;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignColumns;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignTab_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab.EntryTab_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Probabilities;
import DinoText_GUI.Util.DinoList;

import javax.swing.event.TableModelListener;
import java.io.File;

/*******************************************************************************
 * Table Model
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 *
 ******************************************************************************/
public class Table_Model
{
    private DesignTab_Model designTab_model;
    private EntryTab_Model entryTab_model;
    private Table_Data data;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Model()
    {
        this.data = new Table_Data();

        this.designTab_model = new DesignTab_Model(this.data);
        this.entryTab_model = new EntryTab_Model(this.data);
    }

    /***************************************************************************
     * Add Row
     *
     **************************************************************************/
    public void addRow()
    {
        this.data.addRow();
        this.fireTableDataChanged();
    }

    /***************************************************************************
     * addTrait
     *
     **************************************************************************/
    public void addTrait(int rowIndex, Trait newTrait)
    {
        this.data.addTrait(rowIndex, newTrait);
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile()
    {
        this.data.writeToFile();
    }

    public void writeToFile(File directory)
    {
        this.data.writeToFile(directory);
    }

    public void addEntry(String entry, double weight)
    {
        int nextEmpty = this.nextEmptyRow();
        this.data.addEntry(nextEmpty, entry, weight);
    }

    public void addEntry(ListEntry listEntry)
    {
        int nextEmpty = this.nextEmptyRow();
        this.data.addEntry(nextEmpty, listEntry);
    }

    public void addEntry(String entry, double weight, Trait[] traits)
    {
        int nextEmpty = this.nextEmptyRow();
        this.data.addEntry(nextEmpty, "", entry, weight, traits);

        this.fireTableDataChanged();
    }

    public void fireTableDataChanged()
    {
        this.designTab_model.fireTableDataChanged();
        this.entryTab_model.fireTableDataChanged();
    }

    /***************************************************************************
     * nextEmptyRow
     *
     *
     * Returns -1 if there's no empty rows
     **************************************************************************/
    private int nextEmptyRow()
    {
        for(int i = 0; i < this.data.getEntryCount(); i++)
        {
            String entry = (String) this.designTab_model.getValueAt(i,
                    DesignColumns.ENTRY_NAME.ordinal());

            double probability = (Double)
                    this.designTab_model.getValueAt(i, DesignColumns.PROBABILITY_WEIGHT.ordinal());

            if(entry.equals("") && probability == 0.0)
                return i;
        }

        return -1;
    }

    /***************************************************************************
     * addTableModelListener
     *
     **************************************************************************/
    public void addTableModelListener(TableModelListener l) {
        this.designTab_model.addTableModelListener(l);
        this.entryTab_model.addTableModelListener(l);
    }

    /***************************************************************************
     * removeTableModelListener
     *
     **************************************************************************/
    public void removeTableModelListener(TableModelListener l) {
        this.designTab_model.removeTableModelListener(l);
        this.entryTab_model.removeTableModelListener(l);
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

    public void setName(String listName)
    {
        this.data.setName(listName);
    }

    public void setDirectory(File directory)
    {
        this.data.setDirectory(directory);
    }
    public void setTraits(int row, Trait[] traits)
    {
        this.data.setTraits(row, traits);
    }

    public String getName()
    {
        return this.data.getName();
    }

    public DinoList getList()
    {
        return this.data.getList();
    }

    public File getDirectory()
    {
        return this.data.getDirectory();
    }

    public int getEntryCount()
    {
        return this.data.getEntryCount();
    }

    public DesignTab_Model getDesignTab_model()
    {
        return this.designTab_model;
    }

    public EntryTab_Model getEntryTab_model()
    {
        return this.entryTab_model;
    }

    public Trait[] getTraitArray(int rowIndex)
    {
        return this.data.getTraitArray(rowIndex);
    }

    public Table_Probabilities getProbabilities()
    {
        return this.data.getProbabilities();
    }

}
