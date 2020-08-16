package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab;

/*******************************************************************************
 * Entry Columns Enum
 *
 * @author matthewmunson
 * Date: 8/15/2020
 * @version 0.7-beta
 ******************************************************************************/
public enum EntryColumns {

    ENTRY_NAME("Entry Name"),
    ENTRY("List Entry");

    public String header;
    EntryColumns(String name)
    {
        this.header = name;
    }


}
