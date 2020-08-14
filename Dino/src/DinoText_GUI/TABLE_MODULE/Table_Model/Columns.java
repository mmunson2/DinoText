package DinoText_GUI.TABLE_MODULE.Table_Model;

/*******************************************************************************
 * Columns Enum
 *
 ******************************************************************************/
public enum Columns
{
    LIST_ENTRY("List Entry"),
    PROBABILITY_WEIGHT("Probability Weight"),
    PROBABILITY("Probability"),
    ADD_TRAIT("Add Traits"),
    EDIT_TRAIT("Edit Traits"),
    TRAIT("Traits");

    public String header;
    Columns(String name)
    {
        this.header = name;
    }



}
