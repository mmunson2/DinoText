package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.DinoGUIModel;
import DinoText_GUI.VIEW.DinoGUIView;

public class DinoGUIController {

    private DinoGUIModel dinoGUIModel;
    private DinoGUIView dinoGUIView;

    public DinoGUIController(DinoGUIModel model, DinoGUIView view) {
        dinoGUIModel = model;
        dinoGUIView = view;
    }
}
