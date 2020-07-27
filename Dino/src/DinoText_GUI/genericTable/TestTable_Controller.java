package DinoText_GUI.genericTable;

public class TestTable_Controller
{
    TestTable_Model model;
    TestTable_View view;

    TestTable_Controller(TestTable_Model model, TestTable_View view)
    {
        this.model = model;
        this.view = view;

        this.view.setTableModel(model);
    }

}
