
package DinoText_GUI.VIEW.Table;

import DinoText_GUI.CONTROLLER.Table_Controller;
import DinoText_GUI.MODEL.Table.Table_Manager;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.VIEW.Table.Table_View;

public class Table_GUI
{
    private static Table_Manager modelManager;
    private static Table_Controller controller;
    private static Table_TabbedPane view;

    public static void main(String[] args)
    {
        modelManager = new Table_Manager();
        view = new Table_TabbedPane();
        controller = new Table_Controller(modelManager, view);

        view.setVisible(true);
    }



}
