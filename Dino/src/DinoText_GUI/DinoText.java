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
import DinoText_GUI.SYSTEM_CONTROLLER.System_Controller;
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
    private static Dialogue_Model dialogue_model;
    private static Dialogue_View dialogue_view;
    private static Dialogue_Controller dialogue_controller;

    private static Table_Manager table_manager;
    private static Table_TabbedPane table_view;
    private static Table_Controller table_controller;

    private static Text_Display_Model textDisplayModel;
    private static Text_Display_View textDisplayView;
    private static Text_Display_Controller textDisplayController;

    private static System_Controller system_controller;

    private static DinoConfig config;

    private static final String LOOKANDFEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final boolean LOOKANDFEEL_VISIBLE = true;

    public static void main(String[] args) {

        system_controller = new System_Controller(dialogue_controller, textDisplayController, table_controller);

        if (LOOKANDFEEL_VISIBLE) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //Windows Look and feel
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }

        JFrame jFrame_dinoText = new JFrame();

        config = DinoConfig.loadConfig();

        table_manager = new Table_Manager();
        table_view = new Table_TabbedPane();
        table_controller = new Table_Controller(table_manager, table_view);

        textDisplayModel = new Text_Display_Model();
        textDisplayView = new Text_Display_View();
        textDisplayController = new Text_Display_Controller(textDisplayModel, textDisplayView, config);
        textDisplayView.setPanelVisible(false);

        dialogue_model = new Dialogue_Model();
        dialogue_view = new Dialogue_View();
        dialogue_controller = new Dialogue_Controller(dialogue_model, dialogue_view, textDisplayController, table_controller);

        table_controller.setDialogue_controller(dialogue_controller);
        textDisplayController.setDialogueController(dialogue_controller);

        //Frame
        jFrame_dinoText.setLayout(new BorderLayout());
        jFrame_dinoText.getContentPane().add(dialogue_view.getjPanel_dialogueEditor(), BorderLayout.CENTER);
        jFrame_dinoText.getContentPane().add(textDisplayView.getJpanel(), BorderLayout.EAST);
        jFrame_dinoText.getContentPane().add(table_view.getPanel(), BorderLayout.SOUTH);
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
