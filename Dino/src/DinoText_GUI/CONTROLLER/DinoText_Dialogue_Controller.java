package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.DinoText_Dialogue_Model;
import DinoText_GUI.VIEW.DinoText_Dialogue_View;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DinoText_Dialogue_Controller {

    private DinoText_Dialogue_Model dinoGUIModel;
    private DinoText_Dialogue_View dinoGUIView;

    public DinoText_Dialogue_Controller(DinoText_Dialogue_Model model, DinoText_Dialogue_View view) {
        dinoGUIModel = model;
        dinoGUIView = view;

        initialize();
    }

    private void initialize() {
        initializejPopupMenu();
        initializejToolBar();
        newDialogue();
    }


    /*************************************
     * JTOOLBAR
     ************************************/
    private void initializejToolBar() {
        JButton temp = new JButton("Insert Dynamic List");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_InsertDynamicList());
        dinoGUIView.addButtonjToolBar_topBar(temp);
        temp = new JButton("Delete Dynamic List");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_DeleteDynamicList());
        dinoGUIView.addButtonjToolBar_topBar(temp);
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
        dinoGUIView.editListenerjTextField_input(new listener_jTextField_input_nameDialogue(), true);
    }

    class listener_jTextField_input_nameDialogue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIModel.nameDialogue(dinoGUIView.getText_jTextField_input());
            dinoGUIView.editListenerjTextField_input(new listener_jTextField_input_nameDialogue(), false);
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIView.setText_jTextField_input("");
            writeToFile();
        }
    }


    /*************************************
     * WRITE TO FILE
     ************************************/
    private void writeToFile() {
        dinoGUIView.editListenerjTextField_input(new listener_jTextField_input_writeToFile(), true);
    }

    class listener_jTextField_input_writeToFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejTextField_input(false);
            dinoGUIModel.writeToFile();
            newDialogue();
        }
    }

    /*************************************
     * POPUP MENU
     ************************************/
    private void initializejPopupMenu() {
        dinoGUIView.setInvokerjPopupMenu_listInsertion(dinoGUIView.getjTextPane_dialogueInput());
        dinoGUIView.addItemjPopupMenu_listInsertion("Insert Dynamic List", new listener_jPopupMenu_listInsertion_InsertDynamicList());
        dinoGUIView.addListenerjTextPane_dialogueInput(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    dinoGUIView.showjPopupMenu_listInsertion(me);
                }
            }

        });
    }

    class listener_jPopupMenu_listInsertion_InsertDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();
            dinoGUIView.insertButtonjTextPane(listName, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
            }, Color.yellow);
            dinoGUIView.addItemjPopupMenu_listInsertion(listName, new listener_jPopupMenu_listInsertion_SelectExistingList(listName));
        }
    }

    class listener_jPopupMenu_listInsertion_SelectExistingList implements ActionListener {
        private String listName;

        listener_jPopupMenu_listInsertion_SelectExistingList(String name){
            super();
            listName = name;
        }
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.insertButtonjTextPane(listName, dinoGUIView.getButton(listName).getAction(), Color.yellow); //TODO this button may not link to the right name
        }
    }

    class listener_jPopupMenu_listInsertion_DeleteDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();
            //TODO dinoGUIView.removeButtonjPopupMenu(dinoGUIView.getButton(listName)); CONCURRENT MODIFICATION EXCEPTION
        }
    }

}
