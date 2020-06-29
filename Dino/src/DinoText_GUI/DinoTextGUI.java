package DinoText_GUI;

import DinoText_GUI.CONTROLLER.DinoGUIController;
import DinoText_GUI.MODEL.DinoGUIModel;
import DinoText_GUI.VIEW.DinoGUIView;

public class DinoTextGUI {

    public static void main(String args[]) {
        DinoGUIModel model = new DinoGUIModel();
        DinoGUIView view = new DinoGUIView();
        DinoGUIController controller = new DinoGUIController(model, view);

        view.setVisible(true);
    }
}
