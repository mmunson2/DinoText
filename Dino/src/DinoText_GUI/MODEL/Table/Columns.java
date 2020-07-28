package DinoText_GUI.MODEL.Table;

/*******************************************************************************
 * Columns Enum
 *
 ******************************************************************************/
public enum Columns
{
    LIST_ENTRY("List Entry"),
    PROBABILITY_WEIGHT("Probability Weight"),
    PROBABILITY("Probability"),
    ADD_TRAIT("Add Traits");

    public String header;
    Columns(String name)
    {
        this.header = name;
    }



}
