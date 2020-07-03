package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.DinoText_Dialogue_Model;
import DinoText_GUI.VIEW.DinoText_Dialogue_View;

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
        dinoGUIView.setVisiblejButton_submit(true);
        dinoGUIView.setVisiblejLabel_instructions(true);
        dinoGUIView.setVisiblejLabel_instructions(true);
        dinoGUIView.setText_jButton_submit("Submit");
        dinoGUIView.setText_jTextPane_output("Enter dialogue in as many lines as necessary. \nUse \"\\L[NAME]\" to " +
                "specify a list, where NAME is the list name.   \nYou will be asked to populate each list once the " +
                "dialogue is complete. \nPress \"Submit Dialogue\" to complete dialogue entry. \nPress the X in the " +
                "upper right corner to quit without saving.");
        dinoGUIView.setFocusTSDialogueInput();
        dinoGUIView.addjButton_submitListener(new listener_jButton_submit_newDialogue());
    }


    class listener_jButton_submit_newDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String currentListName = dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());

            dinoGUIView.setText_jTextPane_output("Populate List: \nList name: " + currentListName + "\nEnter list " +
                    "contents one line at a time. \nPress the enter key to complete a list entry." +
                    "\nPress \"Submit\" when all entries are made. \nPress the X in the upper right corner " +
                    "to quit without saving.");

            dinoGUIView.setText_jTextPane_dialogueInput("");

            dinoGUIView.removejButton_submitListener(new listener_jButton_submit_newDialogue());

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
        dinoGUIView.addjTextField_inputListener(new listener_jTextField_input_populateList());
        dinoGUIView.addjButton_submitListener(new listener_jButton_submit_populateList());
    }

    private void updateListContents() {
        dinoGUIView.setText_jTextPane_listContents(dinoGUIModel.getCurrentList());
    }

    class listener_jButton_submit_populateList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejTextPane_output(false);
            dinoGUIView.setText_jTextField_input("");

           if (dinoGUIModel.hasNextList()) {
                dinoGUIModel.incrementList();
                dinoGUIView.setText_jTextPane_output("Populate List: \nList name: " + dinoGUIModel.getCurrentListName() + "\nEnter list " +
                        "contents one line at a time. \nPress the enter key to complete a list entry." +
                        "\nPress \"Submit\" when all entries are made. \nPress the X in the upper right corner " +
                        "to quit without saving.");

                dinoGUIView.setText_jTextPane_listContents("");
               dinoGUIView.setVisiblejTextPane_output(true);
           }
            else {
                dinoGUIView.removejTextField_inputListener(new listener_jTextField_input_populateList());
                dinoGUIView.removejButton_submitListener(new listener_jButton_submit_populateList());
                dinoGUIView.setVisibleTSLListContents(false);
               dinoGUIView.setVisiblejTextPane_output(true);
               nameDialogue();
            }
        }
    }


    class listener_jTextField_input_populateList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!dinoGUIView.getText_jTextField_input().isEmpty()) {
                String listItem = dinoGUIView.getText_jTextField_input();
                dinoGUIView.setVisibleTSLListContents(true);
                dinoGUIModel.populateList(listItem);
                updateListContents();
                dinoGUIView.setText_jTextField_input("");
            }
        }
    }

    /*************************************
     * NAME DIALOGUE
     ************************************/
    private void nameDialogue() {
        dinoGUIView.setFocusjTextField_input();
        dinoGUIView.addjTextField_inputListener(new listener_jTextField_input_nameDialogue());
        dinoGUIView.addjButton_submitListener(new listener_jButton_submit_nameDialogue());
        dinoGUIView.setText_jTextPane_output("Enter a name for the dialogue file.");
    }

    class listener_jButton_submit_nameDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIModel.nameDialogue(dinoGUIView.getText_jTextField_input());
            dinoGUIView.removejTextField_inputListener(new listener_jTextField_input_nameDialogue());
            dinoGUIView.removejButton_submitListener(new listener_jButton_submit_nameDialogue());
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIView.setText_jTextField_input("");
            writeToFile();
        }
    }

    class listener_jTextField_input_nameDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIModel.nameDialogue(dinoGUIView.getText_jTextField_input());
            dinoGUIView.removejTextField_inputListener(new listener_jTextField_input_nameDialogue());
            dinoGUIView.removejButton_submitListener(new listener_jButton_submit_nameDialogue());
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
        dinoGUIView.addjButton_submitListener(new listener_jButton_submit_writeToFile());
        dinoGUIView.setText_jButton_submit("Write to File");
    }

    class listener_jButton_submit_writeToFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIModel.writeToFile();
            dinoGUIView.setVisiblejTextField_input(false);
            newDialogue();
        }
    }

    class listener_jTextField_input_writeToFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIModel.writeToFile();
            newDialogue();
        }
    }



}
