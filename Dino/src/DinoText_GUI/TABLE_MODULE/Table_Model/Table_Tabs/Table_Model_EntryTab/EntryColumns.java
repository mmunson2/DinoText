package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_EntryTab;

/*******************************************************************************
 * Entry Columns Enum
 *
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
