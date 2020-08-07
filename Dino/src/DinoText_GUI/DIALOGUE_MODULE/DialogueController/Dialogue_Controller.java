package DinoText_GUI.DIALOGUE_MODULE.DialogueController;

import Dino.Dino;
import Dino.FileTypes;
import Dino.DialogueParser;
import Dino.List.List;

import DinoText_GUI.SYSTEM_CONTROLLER.System_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Controller.Table_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayController.Text_Display_Controller;
import DinoText_GUI.DinoText;
import DinoText_GUI.DIALOGUE_MODULE.DialogueModel.Dialogue_Model;
import DinoText_GUI.DIALOGUE_MODULE.DialogueView.Dialogue_View;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

/*******************************************************************************
 * Dialogue Controller
 *
 ******************************************************************************/
public class Dialogue_Controller {

    private Dialogue_Model dinoGUIModel;
    private Dialogue_View dinoGUIView;
    private Text_Display_Controller textDisplayController;
    private Table_Controller table_controller;

    private static final String DYNAMICLISTNAME = "Part of Conversation";

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
        newDialogue();
        insertionHelper("Untitled List", false);

        dinoGUIView.getjTextPane_dialogueInput().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = dinoGUIView.getjTextPane_dialogueInput().getText();
                int l = value.length();
                if (ke.getKeyCode() == 8) {
                    dinoGUIView.getjTextPane_dialogueInput().setEditable(true);
                } else {
                    dinoGUIView.getjTextPane_dialogueInput().setEditable(false);
                }
            }
        });
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
        menuItem.setText("Insert New " + DYNAMICLISTNAME);
        menuItem.addActionListener(new listener_JMenuItem_Tools_InsertDynamicList());
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
                newDialogue();
                table_controller.openFile(file);
            } else if (FileTypes.hasDialogueExtension(file.getName())) {
                newDialogue();
                String dialogueName = FileTypes.trimDialogueExtension(file.getName());

                DialogueParser parser = new DialogueParser(file.getAbsolutePath());

                //Todo: Take this dialogue String and put it into the view
                parseUnformattedDialogue(parser.getUnformattedDialogue());

                List[] lists = parser.getListArray();
                //Todo: Take these list names and put it wherever you keep them
                int j = 0;
                for (List list : lists) {
                    dinoGUIModel.addListName(list.getName());
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
            jPopupMenu_listInsertion_updateMenuItems();
        }
    }

    private void parseUnformattedDialogue(String unformattedDialogue) {
        String[] split = unformattedDialogue.split(" ");
        int listCount = 0;

        for (int i = 0; i < split.length; i++) {
            String list = split[i];
            if (list.contains("\\L[")) {
                if (!list.contains("]")) {
                    do {
                        // get next word, then check to see if the list is complete.
                        // add a space between words
                        list += " " + split[++i];
                        listCount++;
                    } while (!list.contains("]"));
                }

                if (list.length() > 4) {
                    insertionHelper(list.substring(3, list.length() - 1));
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
                saveExistingDialogueFile();

                Dino dino = getDino();
                textDisplayController.setDino(dino);
                if (mostRecentSaved == null) {
                    String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");
                    saveDialogueFileHelper(fileName);
                }

                if (textDisplayController.panelIsVisible()) {
                    textDisplayController.setPanelVisible(false);

                } else {
                    textDisplayController.setPanelVisible(true);
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


    public void jPopupMenu_listInsertion_updateMenuItems() {


        HashSet<String> currentLists = new HashSet<>();
        // checks if the table has a list the view does not
        for (String listName : table_controller.getListNames()) {
            JMenuItem temp = new JMenuItem();
            temp.setText(listName.trim());
            temp.addActionListener(new listener_jPopupMenu_listInsertion_SelectExistingList(listName.trim()));

            for (Component c : dinoGUIView.getjMenu_listInsertion().getMenuComponents()) {
                currentLists.add(((JMenuItem) c).getText());
            }

            if (!currentLists.contains(temp.getText())) {
                dinoGUIView.addItemjPopupMenu_listInsertion(temp);
            }
        }

        // checks if the table has a list the view does not
        currentLists = new HashSet<>(); // set of lists in table
        for (Component c : dinoGUIView.getjMenu_listInsertion().getMenuComponents()) {
            if (((JMenuItem) c).getText() != DYNAMICLISTNAME) {
                for (String listName : table_controller.getListNames()) { // populate listName with all lists in table
                    currentLists.add(listName.trim());
                }

                if (!currentLists.contains(((JMenuItem) c).getText())) { // if the table does not contain the button,
                    // remove it from the popup
                    dinoGUIView.removeItemjPopupMenu_listInsertion(((JMenuItem) c).getText());
//                    dinoGUIView.removeListName(((JMenuItem) c).getText());
                }

            }
        }

        dinoGUIView.insertAllArrows();
        dinoGUIView.getjPanel_dialogueEditor().validate(); // prints out labels!
    }

    /***************************************************************************
     * Tools Dropdown Menu - Insert Dynamic List
     *
     **************************************************************************/
    class listener_JMenuItem_Tools_InsertDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion(DYNAMICLISTNAME);

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
                            table_controller.switchToName(((JButton) (e.getSource())).getName().trim());
                        }
                    }, Color.yellow);

                } else {
                    dinoGUIView.makeButtonjTextPane_DynamicList(listName.trim(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //When a list button is pressed, change the list tab
                            table_controller.switchToName(((JButton) (e.getSource())).getName().trim());
                        }
                    }, Color.yellow);
                }

                table_controller.addList(listName.trim());

                // add button to list
                jPopupMenu_listInsertion_updateMenuItems(); // TODO This unlinks the tabs
            } else {
                JOptionPane.showMessageDialog(dinoGUIView.getjTextPane_dialogueInput(), "Please enter a name.");
            }
        dinoGUIView.setCaretToEnd();
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
        dinoGUIView.clearListButtonsjPopupMenu();
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
                dinoGUIView.insertButtonjTextPane_DynamicList(varName, (dinoGUIView.getListButton(varName).getActionListeners())[0], Color.yellow);
            dinoGUIView.setFocusTSDialogueInput();
            jPopupMenu_listInsertion_updateMenuItems();
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

        String dialogue = "";
        for (JButton b : dinoGUIView.getActiveListButtons()) {
            dialogue += "\\L[" + b.getName() + "]" + " ";
        }

        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        dinoGUIModel.setDialogue(dialogue);
        dinoGUIModel.writeToFile();

        table_controller.writeAllToFile();
        ((JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor())).setTitle("Dino Text - " + fileName);
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
     * get Dialogue
     *
     **************************************************************************/
    public String getDialogue() {
        dinoGUIModel.setDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
        return dinoGUIModel.getDialogue();
    }

    /***************************************************************************
     * rename List
     *
     **************************************************************************/
    public void renameList(String newName, String oldName) {
        HashSet<String> temp = dinoGUIView.getSetListNames();
        if (temp.contains(oldName)) {
            ArrayList<String> tempList = new ArrayList<>(Arrays.asList(temp.toArray(new String[0])));

//            Collections.sort(tempList);
            int oldPos = tempList.indexOf(oldName);

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
            dinoGUIModel.setListNames(dinoGUIView.getSetListNames());

            //remove oldname from jpopupmenu
            dinoGUIView.removeItemjPopupMenu_listInsertion(oldName);

            //add newname to jpopupmenu

            // add button to list
            jPopupMenu_listInsertion_updateMenuItems();
            dinoGUIView.pack();
        }
    }
}
