package DinoText_GUI.TABLE_MODULE.TraitCreator;

import javax.swing.*;

public class TraitCreator_Test
{
    public static void main(String[] args)
    {
        Creator_View view = new Creator_View();
        Creator_Model model = new Creator_Model();

        Creator_Controller controller = new Creator_Controller(model, view);



        int result = JOptionPane.showConfirmDialog(null, view.getPanel(), "Create Trait", JOptionPane.OK_CANCEL_OPTION);

    }

}
