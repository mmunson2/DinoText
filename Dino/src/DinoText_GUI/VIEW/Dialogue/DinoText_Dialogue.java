/*******************************************************************************************
 *   ████████▄   ▄█     ▄████████  ▄█        ▄██████▄     ▄██████▄  ███    █▄     ▄████████
 * ███   ▀███ ███    ███    ███ ███       ███    ███   ███    ███ ███    ███   ███    ███
 * ███    ███ ███▌   ███    ███ ███       ███    ███   ███    █▀  ███    ███   ███    █▀
 * ███    ███ ███▌   ███    ███ ███       ███    ███  ▄███        ███    ███  ▄███▄▄▄
 * ███    ███ ███▌ ▀███████████ ███       ███    ███ ▀▀███ ████▄  ███    ███ ▀▀███▀▀▀
 * ███    ███ ███    ███    ███ ███       ███    ███   ███    ███ ███    ███   ███    █▄
 * ███   ▄███ ███    ███    ███ ███▌    ▄ ███    ███   ███    ███ ███    ███   ███    ███
 * ████████▀  █▀     ███    █▀  █████▄▄██  ▀██████▀    ████████▀  ████████▀    ██████████
 *****************************************************************************************/
package DinoText_GUI.VIEW.Dialogue;

import Camden.CamdenController;
import Camden.CamdenModel;
import Camden.CamdenView;
import DinoText_GUI.CONTROLLER.DinoText_Dialogue_Controller;
import DinoText_GUI.CONTROLLER.Table_Controller;
import DinoText_GUI.CONTROLLER.TestPanelController;
import DinoText_GUI.MODEL.Dialogue.DinoText_Dialogue_Model;
import DinoText_GUI.MODEL.Table.Table_Manager;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.MODEL.TestPanelModel;
import DinoText_GUI.VIEW.Table.Table_TabbedPane;
import DinoText_GUI.VIEW.Table.Table_View;
import DinoText_GUI.VIEW.TestPanelView;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class DinoText_Dialogue {
    private static DinoText_Dialogue_Model dinoGUIModel;
    private static DinoText_Dialogue_View dinoGUIView;
    private static DinoText_Dialogue_Controller dinoGUIController;

    private static Table_Manager table_manager;
    private static Table_TabbedPane table_view;
    private static Table_Controller table_controller;

    private static CamdenModel camdenModel;
    private static CamdenView camdenView;
    private static CamdenController camdenController;

    public static void main(String[] args) {
        JFrame jFrame_dinoText = new JFrame();

        table_manager = new Table_Manager();
        table_view = new Table_TabbedPane();
        table_controller = new Table_Controller(table_manager, table_view);

        camdenModel = new CamdenModel();
        camdenView = new CamdenView();
        camdenController = new CamdenController(camdenModel,camdenView);
        camdenView.setPanelVisible(false);

        dinoGUIModel = new DinoText_Dialogue_Model();
        dinoGUIView = new DinoText_Dialogue_View();
        dinoGUIController = new DinoText_Dialogue_Controller(dinoGUIModel, dinoGUIView, camdenController, table_controller);

        //Frame
        jFrame_dinoText.setLayout(new BorderLayout());
        jFrame_dinoText.getContentPane().add(dinoGUIView.getjPanel_dialogueEditor(), BorderLayout.CENTER);
        jFrame_dinoText.getContentPane().add(camdenView.getJpanel(),BorderLayout.EAST);
        jFrame_dinoText.getContentPane().add(table_view.getPanel1(),BorderLayout.SOUTH);
        jFrame_dinoText.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_dinoText.setSize(600, 580);
        jFrame_dinoText.pack();
        jFrame_dinoText.setVisible(true);

    }

    public String getDialogueText() {
        return dinoGUIView.getText_jTextPane_dialogueInput();
    }

    public void highlightWord(String word, Color color) throws BadLocationException {
        dinoGUIController.highlightWord(word, color);
    }
}
