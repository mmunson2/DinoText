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
package DinoText_GUI;

import DinoText_GUI.DISPLAY_MODULE.DisplayController.Text_Display_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayModel.Text_Display_Model;
import DinoText_GUI.DIALOGUE_MODULE.DialogueView.Dialogue_View;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Text_Display_View;
import DinoText_GUI.DIALOGUE_MODULE.DialogueController.Dialogue_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Controller.Table_Controller;
import DinoText_GUI.DIALOGUE_MODULE.DialogueModel.Dialogue_Model;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_View.Table_TabbedPane;
import DinoText_GUI.Util.DinoConfig;

import javax.swing.*;
import java.awt.*;

/*******************************************************************************
 * Main Class
 *
 ******************************************************************************/
public class DinoText implements Runnable {
    private static Dialogue_Model dinoGUIModel;
    private static Dialogue_View dinoGUIView;
    private static Dialogue_Controller dinoGUIController;

    private static Table_Manager table_manager;
    private static Table_TabbedPane table_view;
    private static Table_Controller table_controller;

    private static Text_Display_Model textDisplayModel;
    private static Text_Display_View textDisplayView;
    private static Text_Display_Controller textDisplayController;

    private static DinoConfig config;

    public static void main(String[] args) {
        JFrame jFrame_dinoText = new JFrame();

        config = DinoConfig.loadConfig();

        table_manager = new Table_Manager();
        table_view = new Table_TabbedPane();
        table_controller = new Table_Controller(table_manager, table_view);

        textDisplayModel = new Text_Display_Model();
        textDisplayView = new Text_Display_View();
        textDisplayController = new Text_Display_Controller(textDisplayModel, textDisplayView, config);
        textDisplayView.setPanelVisible(false);

        dinoGUIModel = new Dialogue_Model();
        dinoGUIView = new Dialogue_View();
        dinoGUIController = new Dialogue_Controller(dinoGUIModel, dinoGUIView, textDisplayController, table_controller);

        table_controller.setDialogue_controller(dinoGUIController);
        textDisplayController.setDialogueController(dinoGUIController);

        //Frame
        jFrame_dinoText.setLayout(new BorderLayout());
        jFrame_dinoText.getContentPane().add(dinoGUIView.getjPanel_dialogueEditor(), BorderLayout.CENTER);
        jFrame_dinoText.getContentPane().add(textDisplayView.getJpanel(),BorderLayout.EAST);
        jFrame_dinoText.getContentPane().add(table_view.getPanel1(),BorderLayout.SOUTH);
        jFrame_dinoText.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_dinoText.setSize(600, 580);
        jFrame_dinoText.setTitle("Dino Text");
        jFrame_dinoText.pack();
        jFrame_dinoText.setVisible(true);

    }

    @Override
    public void run() {
        main(null);
    }
}
