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

import DinoText_GUI.CONTROLLER.DinoText_Dialogue_Controller;
import DinoText_GUI.CONTROLLER.Table_Controller;
import DinoText_GUI.CONTROLLER.TestPanelController;
import DinoText_GUI.MODEL.Dialogue.DinoText_Dialogue_Model;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.MODEL.TestPanelModel;
import DinoText_GUI.VIEW.Table.Table_View;
import DinoText_GUI.VIEW.TestPanelView;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class DinoText_Dialogue {
    private static DinoText_Dialogue_Model dinoGUIModel;
    private static DinoText_Dialogue_View dinoGUIView;
    private static DinoText_Dialogue_Controller dinoGUIController;

    private static Table_Model table_model;
    private static Table_View table_view;
    private static Table_Controller table_controller;

    public static void main(String[] args) {
        JFrame jFrame_dinoText = new JFrame();

        dinoGUIModel = new DinoText_Dialogue_Model();
        dinoGUIView = new DinoText_Dialogue_View();
        dinoGUIController = new DinoText_Dialogue_Controller(dinoGUIModel, dinoGUIView);

        table_model = new Table_Model();
        table_view = new Table_View();
        table_controller = new Table_Controller(table_model, table_view);

        //Frame
        jFrame_dinoText.setLayout(new BorderLayout());
        jFrame_dinoText.getContentPane().add(dinoGUIView.getjPanel_dialogueEditor(), BorderLayout.CENTER);
        // JFrame.add(Table_View.getJPanel?)
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
