package DinoText_GUI.TABLE_MODULE.Table_Model;

import java.util.ArrayList;

/*******************************************************************************
 * Table Manager
 *
 ******************************************************************************/
public class Table_Manager
{
    private ArrayList<Table_Model> lists = new ArrayList<>();
    private Table_Model currentModel;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Manager()
    {
        this.currentModel = new Table_Model();
        lists.add(currentModel);
    }

    /***************************************************************************
     * getCurrentModel
     *
     **************************************************************************/
    public Table_Model getCurrentModel()
    {
        return this.currentModel;
    }

    /***************************************************************************
     * addModel
     *
     **************************************************************************/
    public void addModel(String name)
    {
        this.currentModel = new Table_Model();
        this.currentModel.setName(name);
        lists.add(currentModel);
    }

    /***************************************************************************
     * switchModel
     *
     **************************************************************************/
    public void switchModel(int modelIndex)
    {
        assert(modelIndex >= 0 && modelIndex < lists.size());

        this.currentModel = lists.get(modelIndex);
        this.currentModel.fireTableDataChanged();
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void writeToFile()
    {
        for(Table_Model list : this.lists)
        {
            list.writeToFile();
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
            this.lists.get(index).writeToFile();
    }

    /***************************************************************************
     * Get Current List Index
     *
     **************************************************************************/
    public int getCurrentListIndex()
    {
        return this.lists.indexOf(currentModel);
    }

    /***************************************************************************
     * Get List Names
     *
     **************************************************************************/
    public String[] getListNames()
    {
        String[] retVal = new String[this.lists.size()];

        for(int i = 0; i < this.lists.size(); i++)
        {
            retVal[i] = this.lists.get(i).getName();
        }

        return retVal;
    }

    /***************************************************************************
     * Get List Index From Name
     *
     **************************************************************************/
    public int getListIndexFromName(String name)
    {
        for(int i = 0; i < lists.size(); i++)
        {
            if(name.equals(lists.get(i).getName()))
            {
                return i;
            }
        }

        return -1;
    }

    /***************************************************************************
     * has List
     *
     **************************************************************************/
    public boolean hasList(String name)
    {
        for(Table_Model model : lists)
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
        return this.lists.size();
    }



}
