package DinoText_GUI.VIEW.Display;

import DinoText_GUI.CONTROLLER.Display.Text_Display_Controller;
import DinoText_GUI.CONTROLLER.Dialouge.DinoText_Dialogue_Controller;
import DinoText_GUI.MODEL.Display.Text_Display_Model;

public class CamdenTest
{
    private static Text_Display_Model model;
    private static Text_Display_View view;
    private static Text_Display_Controller controller;
    private static DinoText_Dialogue_Controller dinoText_dialogue_controller;

    public static void main(String[] args)
    {
        model = new Text_Display_Model();
        view = new Text_Display_View();
        controller = new Text_Display_Controller(model, view);

        view.setVisible(true);

        String str = "The quick brown fox ran extremely quickly and jumped over the lazy dog in order to escape from the crazed beast that was chasing it.";

        controller.setDialogue(str);
    }
}
