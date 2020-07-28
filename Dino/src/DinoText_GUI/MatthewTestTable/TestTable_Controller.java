package DinoText_GUI.MatthewTestTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class TestTable_Controller
{
    TestTable_Model model;
    TestTable_View view;

    TestTable_Controller(TestTable_Model model, TestTable_View view)
    {
        this.model = model;
        this.view = view;

        this.view.setTableModel(model);

        this.view.setButtonColumn(0);

        this.view.setButtonListener(new listener_button());

    }

    class listener_button implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Selected Row: " + view.getSelectedRow());
        }
    }

}
