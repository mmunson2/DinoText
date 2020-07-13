package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.DinoViewer_Model;
import DinoText_GUI.VIEW.DinoViewer_View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoViewer_Controller {
    private DinoViewer_Model dinoViewer_model;
    private DinoViewer_View dinoViewer_view;

    public DinoViewer_Controller(DinoViewer_Model model, DinoViewer_View viewer) {
        dinoViewer_model = model;
        dinoViewer_view = viewer;
    }

    class listener_JTextField_Input implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }
}
