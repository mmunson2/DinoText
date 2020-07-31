// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import DinoText_GUI.Util.DinoConfig;
import DinoText_GUI.DISPLAY_MODULE.DisplayController.Text_Display_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayModel.Text_Display_Model;

/*******************************************************************************
 * Text Display Test Window
 *
 ******************************************************************************/
public class Text_Display_View_Test
{
    private static Text_Display_Model model;
    private static Text_Display_View view;
    private static Text_Display_Controller controller;
    private static DinoConfig config;

    public static void main(String[] args)
    {
        model = new Text_Display_Model();
        view = new Text_Display_View();
        config = DinoConfig.loadConfig();
        controller = new Text_Display_Controller(model, view, config);

        view.setVisible(true);

        String str = "The quick brown fox ran extremely quickly and jumped over the lazy dog in order to escape from the crazed beast that was chasing it. ";
        str += "This has been a very exciting test indeed!";

        controller.setDialogue(str);
    }
}
