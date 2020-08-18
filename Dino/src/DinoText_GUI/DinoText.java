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

import javax.swing.UIManager;
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

        setLookAndFeel();

//        if (LOOKANDFEEL_VISIBLE) {
//            try {
//                ((MetalLookAndFeel) UIManager.getLookAndFeel()).setCurrentTheme(new TestTheme());
//                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //Windows Look and feel
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//                e.printStackTrace();
//            }
//        }

        JFrame jFrame_dinoText = new JFrame();

        jFrame_dinoText.getContentPane().setForeground(Color.RED);

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
        dialogue_controller.setDinoConfig(config);


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

    private static void setLookAndFeel() {
        UIManager.put("control", new Color(57, 59, 61));
        UIManager.put("info", new Color(60, 63, 65));
        UIManager.put("nimbusBase", new Color(3, 3, 3));
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(60, 63, 65));
        UIManager.put("nimbusFocus", new Color(60, 63, 65));
        UIManager.put("nimbusGreen", new Color(176, 179, 50));
        UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new Color(43, 43, 43));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", new Color(231, 231, 231));
        UIManager.put("nimbusSelectionBackground", new Color(60, 63, 65));
        UIManager.put("text", new Color(201, 201, 201));
        UIManager.put("nimbusBlueGrey", new Color(43, 43, 43));
        UIManager.put("textForeground", new Color(201, 201, 201));

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        main(null);
    }
}
