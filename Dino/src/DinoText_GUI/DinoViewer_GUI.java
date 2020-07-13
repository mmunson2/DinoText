package DinoText_GUI;

import DinoText_GUI.CONTROLLER.DinoViewer_Controller;
import DinoText_GUI.MODEL.DinoViewer_Model;
import DinoText_GUI.VIEW.DinoViewer_View;

public class DinoViewer_GUI {
    private static DinoViewer_Model dinoViewer_model;
    private static DinoViewer_View dinoViewer_view;
    private static DinoViewer_Controller dinoViewer_controller;

    public static void main(String args[]) {
        dinoViewer_model = new DinoViewer_Model();
        dinoViewer_view = new DinoViewer_View();
        dinoViewer_controller = new DinoViewer_Controller(dinoViewer_model, dinoViewer_view);

        dinoViewer_view.setVisible(true);
    }

}
