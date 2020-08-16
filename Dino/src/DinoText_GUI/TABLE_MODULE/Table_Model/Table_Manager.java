package DinoText_GUI.TABLE_MODULE.Table_Model;

import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Data;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab.DesignTab_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab.EntryTab_Model;

import java.io.File;
import java.util.ArrayList;

/*******************************************************************************
 * Table Manager
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public class Table_Manager
{
    public static final int DEFAULT_ROWS = 4;
    private ArrayList<Table_Model> models = new ArrayList<>();
    private Table_Model activeModel;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Manager()
    {
        this.activeModel = null;
    }

    /***************************************************************************
     * Has Active Model
     *
     **************************************************************************/
    public boolean hasActiveModel()
    {
        return this.activeModel != null;
    }

    /***************************************************************************
     * Get Active Model
     *
     **************************************************************************/
    public Table_Model getActiveModel()
    {
        return this.activeModel;
    }

    /***************************************************************************
     * Add Model
     *
     **************************************************************************/
    public void addModel(String name)
    {
        Table_Data model = new Table_Data();
        EntryTab_Model entryTab_model = new EntryTab_Model(model);

        this.activeModel = new Table_Model();
        this.activeModel.setName(name);
        models.add(activeModel);
    }

    /***************************************************************************
     * Switch Model
     *
     **************************************************************************/
    public void switchModel(int modelIndex)
    {
        assert(modelIndex >= 0 && modelIndex < models.size());

        this.activeModel = models.get(modelIndex);
        this.activeModel.fireTableDataChanged();
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile()
    {
        for(Table_Model model : this.models)
        {
            model.writeToFile();
        }
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile(File directory)
    {
        for(Table_Model model : this.models)
        {
            model.writeToFile(directory);
        }
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile(String name)
    {
        int index = this.getListIndexFromName(name);

        if(index != -1)
        {
            Table_Model model = this.models.get(index);
            model.writeToFile();
        }

    }

    /***************************************************************************
     * Get Current List Index
     *
     **************************************************************************/
    public int getCurrentListIndex()
    {
        return this.models.indexOf(activeModel);
    }

    /***************************************************************************
     * Get List Names
     *
     **************************************************************************/
    public String[] getListNames()
    {
        String[] retVal = new String[this.models.size()];

        for(int i = 0; i < this.models.size(); i++)
        {
            retVal[i] = this.models.get(i).getName();
        }

        return retVal;
    }

    /***************************************************************************
     * Get List Index From Name
     *
     **************************************************************************/
    public int getListIndexFromName(String name)
    {
        for(int i = 0; i < models.size(); i++)
        {
            if(name.equals(models.get(i).getName()))
            {
                return i;
            }
        }

        return -1;
    }

    /***************************************************************************
     * Has List
     *
     **************************************************************************/
    public boolean hasList(String name)
    {
        for(Table_Model model : models)
        {
            if(model.getName().equals(name))
                return true;
        }

        return false;
    }


    /***************************************************************************
     * Get Size
     *
     **************************************************************************/
    public int getSize()
    {
        return this.models.size();
    }

}
