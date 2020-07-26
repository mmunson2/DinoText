package DinoText_GUI.CONTROLLER.Dialogue;

import DinoDictionary.DinoDictionary;
import DinoDictionary.WordSuggest;
import DinoParser.Dino;
import DinoText_GUI.CONTROLLER.Table.Table_Controller;
import DinoText_GUI.CONTROLLER.Display.Text_Display_Controller;
import DinoText_GUI.DinoText;
import DinoText_GUI.MODEL.Dialogue.Dialogue_Model;
import DinoText_GUI.VIEW.Dialogue.Dialogue_View;


import javax.swing.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*******************************************************************************
 * Dialogue Controller
 *
 ******************************************************************************/
public class Dialogue_Controller {

    private Dialogue_Model dinoGUIModel;
    private Dialogue_View dinoGUIView;
    private Text_Display_Controller textDisplayController;
    private Table_Controller table_controller;

    //Todo: Find a better way to do this
    private String mostRecentSaved = null;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Dialogue_Controller(Dialogue_Model model, Dialogue_View view, Text_Display_Controller textDisplayController, Table_Controller table_controller) {
        dinoGUIModel = model;
        dinoGUIView = view;
        this.textDisplayController = textDisplayController;
        this.table_controller = table_controller;

        initialize();
    }

    /***************************************************************************
     * Initialize
     *
     **************************************************************************/
    private void initialize() {
        initializejPopupMenu();
        initializejToolBar_File();
        initializejToolBar_ListTools();
        initializejToolBar_DictionaryTools();
        newDialogue();
    }


    /***************************************************************************
     * Initialize File JToolBar
     *
     **************************************************************************/
    private void initializejToolBar_File() {
        JMenu file = new JMenu("File");
        JMenuItem fileMenu;

        fileMenu = new JMenuItem();
        fileMenu.setText("New");
        fileMenu.addActionListener(new listener_JMenuItem_File_New());
        file.add(fileMenu);

        fileMenu = new JMenuItem();
        fileMenu.setText("Open");
        fileMenu.addActionListener(new listener_JMenuItem_File_Open());
        file.add(fileMenu);

        fileMenu = new JMenuItem();
        fileMenu.setText("Save");
        fileMenu.addActionListener(new listener_JMenuItem_File_Save());
        file.add(fileMenu);

        fileMenu = new JMenuItem();
        fileMenu.setText("Preview");
        fileMenu.addActionListener(new listener_JMenuItem_File_Preview());
        file.add(fileMenu);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(file);

        dinoGUIView.addJMenujToolBar_topBar(jMenuBar);
    }

    /***************************************************************************
     * Initialize Tools JToolBar
     *
     **************************************************************************/
    private void initializejToolBar_ListTools() {
        JMenu tools = new JMenu("List Tools");
        JMenuItem menuItem;

        menuItem = new JMenuItem();
        menuItem.setText("Insert Dynamic List");
        menuItem.addActionListener(new listener_JMenuItem_Tools_InsertDynamicList());
        tools.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Convert to Dynamic List");
        menuItem.addActionListener(new listener_JMenuItem_Tools_ConvertToDynamicList());
        tools.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Insert Static Variable");
        menuItem.addActionListener(new listener_JMenuItem_Tools_InsertStaticVariable());
        tools.add(menuItem);

        JMenuBar toolsMenu = new JMenuBar();
        toolsMenu.add(tools);

        dinoGUIView.addJMenujToolBar_topBar(toolsMenu);
    }

    /***************************************************************************
     * Initialize DictionaryTools JToolBar
     *
     **************************************************************************/
    private void initializejToolBar_DictionaryTools() {
        JMenu tools = new JMenu("Dictionary Tools");
        JMenuItem menuItem;

        menuItem = new JMenuItem();
        menuItem.setText("Dynamic Word Candidate Suggestion");
        menuItem.addActionListener(new listener_JMenuItem_Tools_DynamicWordCandidateSuggestion());
        tools.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Synonyms");
        menuItem.addActionListener(new listener_JMenuItem_Tools_Synonyms());
        tools.add(menuItem);

        JMenuBar toolsMenu = new JMenuBar();
        toolsMenu.add(tools);

        dinoGUIView.addJMenujToolBar_topBar(toolsMenu);
    }

    /***************************************************************************
     * File Dropdown Menu - Save
     *
     **************************************************************************/
    class listener_JMenuItem_File_Save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDialogueFile();
        }
    }

    /***************************************************************************
     * File Dropdown Menu - Open
     *
     **************************************************************************/
    class listener_JMenuItem_File_Open implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");

            if (fileName != null) {
                dinoGUIModel.setName(fileName);
                mostRecentSaved = fileName;

                if (fileName != null) {
                    dinoGUIModel.setName(fileName);
                    mostRecentSaved = fileName;

                    dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
                    dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
                    dinoGUIModel.writeToFile();

                    table_controller.writeAllToFile();
                }
            }
        }
    }

    /***************************************************************************
     * File Dropdown Menu - New
     *
     **************************************************************************/
    class listener_JMenuItem_File_New implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDialogueFile();
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor());
            jFrame.setVisible(false);
            DinoText.main(null);
        }
    }

    /***************************************************************************
     * File Dropdown Menu - Preview
     *
     **************************************************************************/
    class listener_JMenuItem_File_Preview implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mostRecentSaved == null) {
                saveDialogueFile();
            }

            if (dinoGUIModel.getName() != null) {
                Dino dino = new Dino(mostRecentSaved);
                textDisplayController.setDino(dino);
                if (mostRecentSaved == null) {
                    String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");

                    dinoGUIModel.setName(fileName);
                    mostRecentSaved = fileName;

                    dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
                    dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
                    dinoGUIModel.writeToFile();

                    table_controller.writeAllToFile();
                }


                if (textDisplayController.panelIsVisible()) {
                    textDisplayController.setPanelVisible(false);
                } else {
                    textDisplayController.setPanelVisible(true);
                }
            }
        }
    }

    /***************************************************************************
     * File Dropdown Menu - Preferences
     *
     **************************************************************************/
    private class listener_JMenuItem_Preferences implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejDialog_Preferences(true);
        }
    }


    /***************************************************************************
     * Tools Dropdown Menu - Convert to Dynamic List
     *
     **************************************************************************/
    private class listener_JMenuItem_Tools_ConvertToDynamicList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String word = dinoGUIView.getSelectedText_jTextPane_dialogueInput();
            if (word.length() > 0 && word.trim().length() > 0) {

                String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();

                if (listName.length() > 0 && listName.trim().length() > 0 ){
                    dinoGUIView.insertButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //When a list button is pressed, change the list tab
                            table_controller.switchToName(listName.trim());
                        }
                    }, Color.yellow);

                    JMenuItem temp = new JMenuItem();
                    temp.setText(listName.trim());
                    temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(listName.trim()));
                    dinoGUIView.addItemjPopupMenu_listInsertion(temp);

                    table_controller.addList(listName.trim());

                }

                dinoGUIView.setFocusTSDialogueInput();

                dinoGUIView.deleteSelectedText_jTextPane_dialogueInput();
                table_controller.addEntry(word.trim());
            } else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please highlight a word/sentence and try again.");
            }
        }
    }

    /***************************************************************************
     * Tools Dropdown Menu - Insert Dynamic List
     *
     **************************************************************************/
    class listener_JMenuItem_Tools_InsertDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();

            if (listName.length() > 0 && listName.trim().length() > 0) {
                dinoGUIView.insertButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //When a list button is pressed, change the list tab
                        table_controller.switchToName(listName.trim());
                    }
                }, Color.yellow);


                JMenuItem temp = new JMenuItem();
                temp.setText(listName.trim());
                temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(listName.trim()));
                dinoGUIView.addItemjPopupMenu_listInsertion(temp);

                table_controller.addList(listName.trim());

            }
            else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please enter a name.");
            }

            dinoGUIView.setFocusTSDialogueInput();
        }
    }

    /***************************************************************************
     * Tools Dropdown Menu - Dynamic Word Candidate Suggestion
     *
     **************************************************************************/
    private class listener_JMenuItem_Tools_DynamicWordCandidateSuggestion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String sentence = dinoGUIView.getSelectedText_jTextPane_dialogueInput();
            if (sentence == null) {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please highlight a sentence and try again.");
            } else {
                String[] result = WordSuggest.suggestWord(sentence);
                for (String word : result) {
                    dinoGUIView.getSelectedText_jTextPane_dialogueInput();
                    try {
                        System.out.print(word);
                        dinoGUIView.highlightWordInSelection(word, Color.yellow);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /***************************************************************************
     * Tools Dropdown Menu - Synonyms
     *
     **************************************************************************/
    private class listener_JMenuItem_Tools_Synonyms implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String word = dinoGUIView.getSelectedText_jTextPane_dialogueInput();
            if (word == null || word.trim().contains(" ") || word.trim() == null) {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please highlight a single word and try again.");
            } else {
                word = word.trim();
                table_controller.addList(word + " Synonyms", DinoDictionary.getSynonyms(word).toArray(new String[0]));
            }
        }
    }

    /***************************************************************************
     * Tools Dropdown Menu - Insert Static Variable
     *
     **************************************************************************/
    class listener_JMenuItem_Tools_InsertStaticVariable implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String varName = dinoGUIView.requestListNamejOptionPane_listInsertion(); // get name of
            if (varName.length() > 0 && varName.trim().length() > 0) {
                varName = varName.trim();
                dinoGUIView.insertButtonjTextPane_StaticVar(varName, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //If we make a static variable menu, it should be
                        //updated here
                    }
                }, Color.red);

                JMenuItem temp = new JMenuItem();
                temp.setText(varName);
                temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(varName));
                dinoGUIView.addItemjPopupMenu_listInsertion(temp);

                dinoGUIModel.addStaticVar(varName);

                dinoGUIView.setFocusTSDialogueInput();
            }
            else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please enter a name.");
            }
        }
    }

    /***************************************************************************
     * set jOptionPane_Preferences
     * use this to pass in the JOptionPane from another class
     **************************************************************************/
    public void setjOptionPane_Preferences(JOptionPane pane) {
        dinoGUIView.setjOptionPane_Preferences(pane);
    }

    /***************************************************************************
     * newDialogue
     *
     **************************************************************************/
    private void newDialogue() {
        dinoGUIView.setVisibleTSDialogueInput(true);
        dinoGUIView.setFocusTSDialogueInput();
    }

    /***************************************************************************
     * Initialize JPopup Menu
     *
     **************************************************************************/
    void initializejPopupMenu() {
        dinoGUIView.setInvokerjPopupMenu_listInsertion(dinoGUIView.getjTextPane_dialogueInput());

        JMenuItem temp = new JMenuItem();
        temp.setText("Dynamic List");
        temp.addActionListener(new listener_JMenuItem_Tools_InsertDynamicList());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText("Dynamic List Conversion");
        temp.addActionListener(new listener_JMenuItem_Tools_ConvertToDynamicList());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText("Static Variable");
        temp.addActionListener(new listener_JMenuItem_Tools_InsertStaticVariable());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText("Dynamic Word Candidate Suggestion");
        temp.addActionListener(new listener_JMenuItem_Tools_DynamicWordCandidateSuggestion());
        dinoGUIView.addItemjPopupMenu_dictionary(temp);

        temp = new JMenuItem();
        temp.setText("Find Synonyms");
        temp.addActionListener(new listener_JMenuItem_Tools_Synonyms());
        dinoGUIView.addItemjPopupMenu_dictionary(temp);

        dinoGUIView.addListenerjTextPane_dialogueInput(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    dinoGUIView.showjPopupMenu_listInsertion(me);
                }
            }

        });
        
    }

    /***************************************************************************
     * Select Existing List
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_SelectExistingList implements ActionListener {
        private String varName;

        listener_jPopupMenu_listInsertion_SelectExistingList(String name) {
            super();
            varName = name;
        }

        public void actionPerformed(ActionEvent e) {
            if (dinoGUIView.getSetListNames().contains(varName))
                dinoGUIView.insertButtonjTextPane_DynamicList(varName, dinoGUIView.getListButton(varName).getAction(), Color.yellow); //TODO this button may not link to the right name
            else {
                dinoGUIView.insertButtonjTextPane_DynamicList(varName, dinoGUIView.getStaticVarButton(varName).getAction(), Color.red); //TODO this button may not link to the right name
            }

            dinoGUIView.setFocusTSDialogueInput();
        }
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public void saveDialogueFile() {
        String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");
        if (fileName != null) {
            dinoGUIModel.setName(fileName);
            mostRecentSaved = fileName;

            dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
            dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
            dinoGUIModel.writeToFile();

            table_controller.writeAllToFile();
        }
    }

    /***************************************************************************
     * SETTERS
     **************************************************************************/
    /***************************************************************************
     * set ListNames
     *
     **************************************************************************/
    public void setListNames(Set<String> newListNames) {
        dinoGUIModel.setListNames(newListNames);
    }

    /***************************************************************************
     * set staticVars
     *
     **************************************************************************/
    public void setStaticVars(Set<String> newVars) {
        dinoGUIModel.setStaticVars(newVars);
    }

    /***************************************************************************
     * set Dialogue
     *
     **************************************************************************/
    public void setDialogue(String newDialogue) {
        dinoGUIModel.setDialogue(newDialogue);
    }

    /***************************************************************************
     * set Dialogue Name
     *
     **************************************************************************/
    public void setDialogueName(String newName) {
        dinoGUIModel.setName(newName);
    }


    /***************************************************************************
     * GETTERS
     **************************************************************************/

    /***************************************************************************
     * get ListNames
     *
     **************************************************************************/
    public Set<String> getSetListNames() {
        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        return dinoGUIModel.getListNames();
    }

    public String[] getArrayListName() {
        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        return dinoGUIView.getSetListNames().toArray(new String[dinoGUIView.getSetListNames().size()]);
    }

    /***************************************************************************
     * get staticVars
     *
     **************************************************************************/
    public Set<String> getSetStaticVars() {
        return dinoGUIModel.getStaticVars();
    }

    public String[] getArrayStaticVars() {
        return dinoGUIModel.getStaticVars().toArray(new String[dinoGUIModel.getStaticVars().size()]);

    }

    /***************************************************************************
     * get Dialogue
     *
     **************************************************************************/
    public String getDialogue() {
        dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
        return dinoGUIModel.getDialogue();
    }

    /***************************************************************************
     * get Dialogue Name
     *
     **************************************************************************/
    public String getDialogueName() {
        return dinoGUIModel.getName();
    }

    /***************************************************************************
     * rename List
     *
     **************************************************************************/
    public void renameList(String newName, String oldName) {
        HashSet<String> temp = dinoGUIView.getSetListNames();
        if (temp.contains(oldName)) {
            int oldPos = Arrays.asList(temp.toArray(new String[0])).indexOf(oldName);

            //remove oldname from dialogue
            dinoGUIView.getText_jTextPane_dialogueInput().replaceAll(oldName, newName);

            //rename JButtons
            for (JButton tempButton : dinoGUIView.getAllListButtons()) {
                if (tempButton.getName().equals(oldName)) {
                    tempButton.setName(newName);
                    tempButton.setText(newName);
                }
            }

            //remove oldname from the set of list names
            temp.remove(oldName);
            temp.add(newName);
            dinoGUIView.setSetListNames(temp);
            System.out.println("Temp: " + temp);
            dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
            System.out.println(dinoGUIView.getSetListNames());

            //remove oldname from jpopupmenu
            dinoGUIView.removeItemjPopupMenu_listInsertion(oldPos + 2);

            //add newname to jpopupmenu

            JMenuItem tempItem = new JMenuItem();
            tempItem.setText(newName);
            tempItem.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(newName.trim()));
            dinoGUIView.addItemjPopupMenu_listInsertion(tempItem);
            dinoGUIView.pack();
        }
    }

}
