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

import DinoText_GUI.CONTROLLER.Display.Text_Display_Controller;
import DinoText_GUI.MODEL.Display.Text_Display_Model;
import DinoText_GUI.VIEW.Dialogue.Dialogue_View;
import DinoText_GUI.VIEW.Display.Text_Display_View;
import DinoText_GUI.CONTROLLER.Dialogue.Dialogue_Controller;
import DinoText_GUI.CONTROLLER.Table.Table_Controller;
import DinoText_GUI.MODEL.Dialogue.Dialogue_Model;
import DinoText_GUI.MODEL.Table.Table_Manager;
import DinoText_GUI.VIEW.Table.Table_TabbedPane;

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


    public static void main(String[] args) {
        JFrame jFrame_dinoText = new JFrame();

        table_manager = new Table_Manager();
        table_view = new Table_TabbedPane();
        table_controller = new Table_Controller(table_manager, table_view);

        textDisplayModel = new Text_Display_Model();
        textDisplayView = new Text_Display_View();
        textDisplayController = new Text_Display_Controller(textDisplayModel, textDisplayView);
        textDisplayView.setPanelVisible(false);

        dinoGUIModel = new Dialogue_Model();
        dinoGUIView = new Dialogue_View();
        dinoGUIController = new Dialogue_Controller(dinoGUIModel, dinoGUIView, textDisplayController, table_controller);

        table_controller.setDialogue_controller(dinoGUIController);

        //Frame
        jFrame_dinoText.setLayout(new BorderLayout());
        jFrame_dinoText.getContentPane().add(dinoGUIView.getjPanel_dialogueEditor(), BorderLayout.CENTER);
        jFrame_dinoText.getContentPane().add(textDisplayView.getJpanel(),BorderLayout.EAST);
        jFrame_dinoText.getContentPane().add(table_view.getPanel1(),BorderLayout.SOUTH);
        jFrame_dinoText.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_dinoText.setSize(600, 580);
        jFrame_dinoText.pack();
        jFrame_dinoText.setVisible(true);

    }

    @Override
    public void run() {
        main(null);
    }
}
