package DinoText_GUI.IhsanTestTable;

public class TestTable_Controller
{
    TestTable_Model model;
    TestTable_View view;

    TestTable_Controller(TestTable_Model model, TestTable_View view)
    {
        this.model = model;
        this.view = view;

        this.view.setTableModel(model);

        //Todo: Info: This method binds the view to your custom trait display to the trait column
        this.view.setButtonColumn(0);
    }

}
