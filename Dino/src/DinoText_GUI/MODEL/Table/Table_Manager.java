package DinoText_GUI.MODEL.Table;

import DinoText_GUI.VIEW.Table.Table_TabbedPane;

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

}
