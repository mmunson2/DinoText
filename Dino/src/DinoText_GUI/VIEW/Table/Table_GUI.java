
package DinoText_GUI.VIEW.Table;

import DinoText_GUI.CONTROLLER.Table_Controller;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.VIEW.Table.Table_View;

public class Table_GUI
{
    private static Table_Model model;
    private static Table_Controller controller;
    private static Table_View view;

    public static void main(String[] args)
    {
        model = new Table_Model();
        view = new Table_View();
        controller = new Table_Controller(model, view);

        view.setVisible(true);
    }



}
