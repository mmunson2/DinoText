package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.Dialogue.DinoText_Dialogue_Model;
import DinoText_GUI.VIEW.Dialogue.DinoText_Dialogue_View;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoText_Dialogue_Controller {

    private DinoText_Dialogue_Model dinoGUIModel;
    private DinoText_Dialogue_View dinoGUIView;

    public DinoText_Dialogue_Controller(DinoText_Dialogue_Model model, DinoText_Dialogue_View view) {
        dinoGUIModel = model;
        dinoGUIView = view;

        initialize();
    }

    private void initialize() {
        newDialogue();
    }

    /*************************************
     * NEW DIALOGUE
     ************************************/
    private void newDialogue() {
        dinoGUIView.setVisibleTSDialogueInput(true);
        dinoGUIView.setFocusTSDialogueInput();
    }

    public void highlightWord(String word, Color color) throws BadLocationException {
        dinoGUIView.highlightWord(word, color);

    }

    class listener_jButton_submit_newDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String currentListName = dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
            dinoGUIView.setText_jTextPane_dialogueInput("");
            dinoGUIView.setVisibleTSDialogueInput(false);
            populateList();
        }
    }

    /*************************************
     * POPULATE LIST
     ************************************/
    private void populateList() {
        dinoGUIView.setVisiblejTextField_input(true);
        dinoGUIView.setFocusjTextField_input();
    }

    /*************************************
     * NAME DIALOGUE
     ************************************/
    private void nameDialogue() {
        dinoGUIView.setFocusjTextField_input();
        dinoGUIView.addjTextField_inputListener(new listener_jTextField_input_nameDialogue());
    }

    class listener_jTextField_input_nameDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIModel.nameDialogue(dinoGUIView.getText_jTextField_input());
            dinoGUIView.removejTextField_inputListener(new listener_jTextField_input_nameDialogue());
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIView.setText_jTextField_input("");
            writeToFile();
        }
    }


    /*************************************
     * WRITE TO FILE
     ************************************/
    private void writeToFile() {
        dinoGUIView.addjTextField_inputListener(new listener_jTextField_input_writeToFile());
    }

    class listener_jTextField_input_writeToFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIModel.writeToFile();
            newDialogue();
        }
    }


}
