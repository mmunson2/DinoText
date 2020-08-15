package DinoText_GUI.TABLE_MODULE.Table_Model.Table_Tabs.Table_Model_DesignTab;

/*******************************************************************************
 * Columns Enum
 *
 ******************************************************************************/
public enum DesignColumns
{
    ENTRY_NAME("Entry Name"),
    PROBABILITY_WEIGHT("Probability Weight"),
    PROBABILITY("Probability"),
    ADD_TRAIT("Add Traits"),
    EDIT_TRAIT("Edit Traits"),
    TRAIT("Traits");

    public String header;
    DesignColumns(String name)
    {
        this.header = name;
    }



}
