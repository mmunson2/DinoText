package DinoText_GUI.DIALOGUE_MODULE.DialogueController;

import DinoDictionary.DinoDictionary;
import DinoDictionary.WordSuggest;
import Dino.Dino;
import Dino.FileTypes;
import Dino.DialogueParser;
import Dino.List.List;

import DinoText_GUI.TABLE_MODULE.Table_Controller.Table_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayController.Text_Display_Controller;
import DinoText_GUI.DinoText;
import DinoText_GUI.DIALOGUE_MODULE.DialogueModel.Dialogue_Model;
import DinoText_GUI.DIALOGUE_MODULE.DialogueView.Dialogue_View;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
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

    private static final String DYNAMICLISTNAME = "Phrase List";
    private static final String STATICVARNAME = "Game Value";


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
        dinoGUIView.addKeyListenerjTextPane_dialogueInput(new listener_JPanel_dialogueInput_backspace());
        initializejPopupMenu();
        initializejToolBar_File();
        initializejToolBar_ListTools();
        initializejToolBar_DictionaryTools();
        newDialogue();
        insertionHelper("Untitled List", false);

//        dinoGUIView.getjTextPane_dialogueInput().addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent ke) {
//                if (ke.getKeyCode() == 8) {
//                    dinoGUIView.getjTextPane_dialogueInput().setEditable(true);
//                } else {
//                    dinoGUIView.getjTextPane_dialogueInput().setEditable(false);
//                }
//            }
//        });

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
        fileMenu.setText("Save As");
        fileMenu.addActionListener(new listener_JMenuItem_File_SaveAs());
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
        menuItem.setText("Insert " + DYNAMICLISTNAME);
        menuItem.addActionListener(new listener_JMenuItem_Tools_InsertDynamicList());
        tools.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Convert to " + DYNAMICLISTNAME);
        menuItem.addActionListener(new listener_JMenuItem_Tools_ConvertToDynamicList());
        tools.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Insert " + STATICVARNAME);
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
        menuItem.setText(DYNAMICLISTNAME + " Candidate Suggestion");
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
            saveExistingDialogueFile();
        }
    }

    /***************************************************************************
     * File Dropdown Menu - Save As
     *
     **************************************************************************/
    class listener_JMenuItem_File_SaveAs implements ActionListener {
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
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "DINO and DLIST files", "dino", "dlist");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(dinoGUIView.getjTextPane_dialogueInput());

            File file = chooser.getSelectedFile();

            if (file == null) //Cancelled operation
            {
                return;
            } else if (FileTypes.hasListExtension(file.getName())) {
                table_controller.openFile(file);
            } else if (FileTypes.hasDialogueExtension(file.getName())) {
                String dialogueName = FileTypes.trimDialogueExtension(file.getName());

                DialogueParser parser = new DialogueParser(file.getAbsolutePath());
                //Todo: Take this dialogue String and put it into the view
                parseUnformattedDialogue(parser.getUnformattedDialogue());

                List[] lists = parser.getListArray();
                //Todo: Take these list names and put it wherever you keep them
                int j = 0;
                for (List list : lists) {
                    dinoGUIModel.getListNames().add(list.getName());
                    j++;
                }
                String[] listNames = new String[lists.length];

                for (int i = 0; i < lists.length; i++) {
                    listNames[i] = lists[i].getName();
                }

                for (int i = 0; i < listNames.length; i++) {
                    String listFileName = listNames[i] +
                            FileTypes.LIST_EXTENSION;

                    table_controller.openFile(listFileName);
                }
            } else //File type not recognized
            {
                JOptionPane.showMessageDialog(null,
                        "Could not open file: " + file.getName());
            }


            if (file != null) {
                saveDialogueFileHelper(file.getName());
            }
        }
    }

    private void parseUnformattedDialogue(String unformattedDialogue) {

        String[] split = unformattedDialogue.split(" ");
        dinoGUIView.setText_jTextPane_dialogueInput(unformattedDialogue);
        String searchString = ""; // text that has already been iterated through
        int offset = 0;
        int listCount = 0;
        boolean listFound = false;

        for (int i = 0; i < split.length; i++) {
            String word = split[i];

            if (word.contains("\\L[") || word.contains("\\S[")) {
                if (!word.contains("]")) {
                    do {
                        // get next word, then check to see if the list is complete.
                        // add a space between words
                        word += " " + split[++i];
                    } while (!word.contains("]"));
                }

                searchString += word + " ";

                int selectionStart = searchString.lastIndexOf(word) + offset;


                dinoGUIView.setCaret_jTextPane_dialogueInput(selectionStart);
                switch (word.charAt(1)) {
                    case 'L':
                        if (listCount > 0) {
                            dinoGUIView.insertIcon_Arrow_jTextPane();
                        }
                        insertionHelper(word.substring(3, word.length() - 1));
                        listFound = true;
                        break;
                    case 'S':
                        staticHelper(word.substring(3, word.length() - 1));
                        break;
                }
                if (listCount > 0) {
                    dinoGUIView.setSelectedText_jTextPane_dialogueInput(selectionStart + 2 + word.length(), selectionStart + (2 * word.length()) + 3);
                } else {
                    dinoGUIView.setSelectedText_jTextPane_dialogueInput(selectionStart + 1 + word.length(), selectionStart + (2 * word.length()) + 1);
                }
                dinoGUIView.deleteSelectedText_jTextPane_dialogueInput();

                if (listFound) {
                    listCount++;
                    listFound = false;
                }
                offset++;
            } else {
                searchString += word + " ";
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
            if (saveExistingDialogueFile()) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor());
                jFrame.setVisible(false);
                DinoText.main(null);
            }
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

                Dino dino = getDino();
                textDisplayController.setDino(dino);
                if (mostRecentSaved == null) {
                    String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");
                    saveDialogueFileHelper(fileName);
                }

                if (textDisplayController.panelIsVisible()) {
                    textDisplayController.setPanelVisible(false);
                    System.out.println("Setting invisible");

                } else {
                    textDisplayController.setPanelVisible(true);
                    System.out.println("Setting visible");
                }
            }
        }
    }

    public Dino getDino() {
        return new Dino(mostRecentSaved);
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
            if (dinoGUIView.getSelectedText_jTextPane_dialogueInput() != null) {
                String word = dinoGUIView.getSelectedText_jTextPane_dialogueInput();

                if (word.length() > 0 && word.trim().length() > 0) {

                    String listName = dinoGUIView.requestListNamejOptionPane_listInsertion(DYNAMICLISTNAME);
                    conversionHelper(word, listName);

                }
            } else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please highlight a word/sentence and try again.");
            }
        }
    }

    public void conversionHelper(String word, String listName) {

        if (word != null && word.trim().length() > 0) {
            conversionHelper(listName);
            table_controller.addEntry(word.trim());
        }
    }

    public void conversionHelper(String listName) {
        if (listName.length() > 0 && listName.trim().length() > 0) {


            //inserts button into panel
            dinoGUIView.insertButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //When a list button is pressed, change the list tab
                    table_controller.switchToName(listName.trim());
                }
            }, Color.yellow);

            //adds button to table
            table_controller.addList(listName.trim());

            // add button to list
            jPopupMenu_listInsertion_updateMenuItems();

        }

        dinoGUIView.setFocusTSDialogueInput();

        dinoGUIView.deleteSelectedText_jTextPane_dialogueInput();
    }


    public void jPopupMenu_listInsertion_updateMenuItems() {
        for (String listName : table_controller.getListNames()) {
            JMenuItem temp = new JMenuItem();
            temp.setText(listName.trim());
            temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(listName.trim()));
            dinoGUIView.addItemjPopupMenu_listInsertion(temp);
        }
    }

    /***************************************************************************
     * Tools Dropdown Menu - Insert Dynamic List
     *
     **************************************************************************/
    class listener_JMenuItem_Tools_InsertDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion(DYNAMICLISTNAME);
            if (dinoGUIView.getjTextPane_dialogueInput().getComponents().length > 0) {
                dinoGUIView.insertIcon_Arrow_jTextPane();
            }
            insertionHelper(listName);

            dinoGUIView.setFocusTSDialogueInput();
        }
    }

    public void insertionHelper(String listName) {
        insertionHelper(listName, true);
    }

    public void insertionHelper(String listName, boolean insertNow) {
        if (listName != null)

            if (listName.trim().length() > 0) {
                if (insertNow) {
                    dinoGUIView.insertButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //When a list button is pressed, change the list tab
                            table_controller.switchToName(listName.trim());
                        }
                    }, Color.yellow);

                } else {
                    dinoGUIView.makeButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //When a list button is pressed, change the list tab
                            table_controller.switchToName(listName.trim());
                        }
                    }, Color.yellow);
                }

                table_controller.addList(listName.trim());

                // add button to list
                jPopupMenu_listInsertion_updateMenuItems();
            } else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please enter a name.");
            }
        System.out.println(dinoGUIView.getjTextPane_dialogueInput().getComponentCount());

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
            String varName = dinoGUIView.requestListNamejOptionPane_listInsertion(STATICVARNAME); // get name of
            if (varName != null) {
                if (varName.trim().length() > 0) {
                    varName = varName.trim();

                    staticHelper(varName);

                } else {
                    JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please enter a name.");
                }
            }
        }
    }

    public void staticHelper(String varName) {
        dinoGUIView.insertButtonjTextPane_StaticVar(varName, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If we make a static variable menu, it should be
                //updated here
            }
        }, Color.red);

        // add button to list
        JMenuItem temp = new JMenuItem();
        temp.setText(varName);
        temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(varName));
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        dinoGUIModel.addStaticVar(varName);

        dinoGUIView.setFocusTSDialogueInput();
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
        dinoGUIView.clearjTextPane_dialogueInput();
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
        temp.setText(DYNAMICLISTNAME);
        temp.addActionListener(new listener_JMenuItem_Tools_InsertDynamicList());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText(DYNAMICLISTNAME + " Conversion");
        temp.addActionListener(new listener_JMenuItem_Tools_ConvertToDynamicList());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText(STATICVARNAME);
        temp.addActionListener(new listener_JMenuItem_Tools_InsertStaticVariable());
        dinoGUIView.addItemjPopupMenu_listInsertion(temp);

        temp = new JMenuItem();
        temp.setText(DYNAMICLISTNAME + " Candidate Suggestion");
        temp.addActionListener(new listener_JMenuItem_Tools_DynamicWordCandidateSuggestion());
        dinoGUIView.addItemjPopupMenu_dictionary(temp);

        temp = new JMenuItem();
        temp.setText("Find Synonyms");
        temp.addActionListener(new listener_JMenuItem_Tools_Synonyms());
        dinoGUIView.addItemjPopupMenu_dictionary(temp);

        temp = new JMenuItem();
        temp.setText("Remove All Highlights");
        temp.addActionListener(new listener_JMenuItem_Tools_Dehighlight());
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
            parseUnformattedDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
            dinoGUIView.setFocusTSDialogueInput();
        }
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    public boolean saveDialogueFile() {
        String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");
        if (fileName != null) {
            saveDialogueFileHelper(fileName);
            return true;
        }
        return false;
    }

    private void saveDialogueFileHelper(String fileName) {
        dinoGUIModel.setName(fileName);
        mostRecentSaved = fileName;

        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        dinoGUIView.setText_jTextPane_dialogueInput(dinoGUIView.getText_jTextPane_dialogueInput().replaceAll("\\s{2,}", " "));
        dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
        dinoGUIModel.writeToFile();

        table_controller.writeAllToFile();
        ((JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor())).setTitle("Dino Text - " + fileName);
        parseUnformattedDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
        textDisplayController.setDino(getDino());
    }

    public boolean saveExistingDialogueFile() {
        String fileName = ((JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor())).getTitle();
        if (fileName.length() > 9) {
            fileName = fileName.substring(12);
            saveDialogueFileHelper(fileName);
            return true;
        } else {
            saveDialogueFile();
        }
        return false;
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
            dinoGUIModel.setListNames(dinoGUIView.getSetListNames());

            //remove oldname from jpopupmenu
            dinoGUIView.removeItemjPopupMenu_listInsertion(oldPos + 3);

            //add newname to jpopupmenu

            // add button to list
            jPopupMenu_listInsertion_updateMenuItems();
            dinoGUIView.pack();
        }
    }
//
//    public void insertAllArrows() {
//        String unformattedDialogue = dinoGUIView.getText_jTextPane_dialogueInput();
//        String[] split = unformattedDialogue.split(" ");
//
//        String searchString = ""; // text that has already been iterated through
//        int offset = 0;
//
//        for (int i = 0; i < split.length; i++) {
//            if (split[i].contains("\\")) {
//                insert
//            }
//
//        }
//    }

    /***************************************************************************
     * Dehighlight all
     *
     **************************************************************************/
    private class listener_JMenuItem_Tools_Dehighlight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.dehighlightAll();
        }
    }

    private class listener_JPanel_dialogueInput_backspace implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }


        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 8) {
                boolean space = false;
                boolean first = false;


                JTextPane pane = dinoGUIView.getjTextPane_dialogueInput();

                if (pane.getText().length() >= 0) {
                    if (pane.getCaretPosition() == 0) {
                        newDialogue();
                        return;
                    }

                    try {
                        if (pane.getText(pane.getCaretPosition() - 2, 1).contains("]")) {
                            pane.setCaretPosition(pane.getCaretPosition() - 2);
                            space = true;
                            first = true;
                        }
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                    while (pane.getCaretPosition() > 0 && StyleConstants.getFontSize(new SimpleAttributeSet(pane.getInputAttributes())) == 0) {
                        if (first) {
                            pane.setCaretPosition(pane.getCaretPosition() + 1);
                            first = false;
                        }


                        try {
                            pane.getDocument().remove(pane.getCaretPosition(), 1);
                        } catch (BadLocationException ex) {
                        }

                        pane.setCaretPosition(pane.getCaretPosition() - 1);
                    }

                    if (dinoGUIView.getjTextPane_dialogueInput().getComponentCount() > 1) {
                        try {
                            pane.getDocument().remove(pane.getCaretPosition(), 1);

                            if (space)
                                pane.setCaretPosition(pane.getCaretPosition() - 1);

                        } catch (BadLocationException ex) {
                        }
                    }

                    if (space)
                        pane.setCaretPosition(pane.getCaretPosition() + 1);

                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}
