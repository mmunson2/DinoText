package DinoText_GUI.CONTROLLER.Dialogue;

import DinoParser.Dino;
import DinoText_GUI.CONTROLLER.Table.Table_Controller;
import DinoText_GUI.CONTROLLER.Display.Text_Display_Controller;
import DinoText_GUI.DinoText;
import DinoText_GUI.MODEL.Dialogue.Dialogue_Model;
import DinoText_GUI.MODEL.Table.Table_Manager;
import DinoText_GUI.MODEL.Table.Table_Model;
import DinoText_GUI.VIEW.Dialogue.Dialogue_View;
import DinoText_GUI.VIEW.Table.Table_TabbedPane;

import javax.swing.*;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

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
        initializejToolBar();
        newDialogue();
    }


    /***************************************************************************
     * Initialize JToolBar
     *
     **************************************************************************/
    private void initializejToolBar() {
        JMenu menu = new JMenu("File");
        JMenuItem menuItem;

        menuItem = new JMenuItem();
        menuItem.setText("New");
        menuItem.addActionListener(new listener_JMenuItem_New());
        menu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Open");
        menuItem.addActionListener(new listener_JMenuItem_Open());
        menu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Save");
        menuItem.addActionListener(new listener_JMenuItem_Save());
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Preview");
        menuItem.addActionListener(new listener_JMenuItem_Preview());
        menu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Preferences");
        menuItem.addActionListener(new listener_JMenuItem_Preferences());
        menu.add(menuItem);


        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(menu);

        dinoGUIView.addJMenujToolBar_topBar(jMenuBar);

        JButton temp = new JButton("Insert Dynamic List");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_InsertDynamicList());
        dinoGUIView.addButtonjToolBar_topBar(temp);
        temp = new JButton("Insert Static Variable");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_InsertStaticVariable());
        dinoGUIView.addButtonjToolBar_topBar(temp);
    }

    /***************************************************************************
     * Dropdown Menu - Save
     *
     **************************************************************************/
    class listener_JMenuItem_Save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDialogueFile();
        }
    }

    /***************************************************************************
     * Dropdown Menu - Open
     *
     **************************************************************************/
    class listener_JMenuItem_Open implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");

            dinoGUIModel.setName(fileName);
            mostRecentSaved = fileName;

            dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
            dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
            dinoGUIModel.writeToFile();

            table_controller.writeToFile();
        }
    }

    /***************************************************************************
     * Dropdown Menu - New
     *
     **************************************************************************/
    class listener_JMenuItem_New implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDialogueFile();
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(dinoGUIView.getjPanel_dialogueEditor());
            jFrame.setVisible(false);
            DinoText.main(null);
        }
    }

    /***************************************************************************
     * Dropdown Menu - Preview
     *
     **************************************************************************/
    class listener_JMenuItem_Preview implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mostRecentSaved == null) {
                saveDialogueFile();
            }

            if (dinoGUIModel.getName() != null) {
                Dino dino = new Dino(mostRecentSaved);
                textDisplayController.setDino(dino);
            if(mostRecentSaved == null)
            {
                String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");

                dinoGUIModel.setName(fileName);
                mostRecentSaved = fileName;

                dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
                dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
                dinoGUIModel.writeToFile();

                table_controller.writeToFile();
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
     * Dropdown Menu - Preferences
     *
     **************************************************************************/
    private class listener_JMenuItem_Preferences implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dinoGUIView.setVisiblejDialog_Preferences(true);
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
     * Initialize Dropdown Menu
     *
     **************************************************************************/
    private void initializejPopupMenu() {
        dinoGUIView.setInvokerjPopupMenu_listInsertion(dinoGUIView.getjTextPane_dialogueInput());
        dinoGUIView.addItemjPopupMenu_listInsertion("Insert Dynamic List", new listener_jPopupMenu_listInsertion_InsertDynamicList());
        dinoGUIView.addItemjPopupMenu_listInsertion("Insert Static Variable", new listener_jPopupMenu_listInsertion_InsertStaticVariable());
        dinoGUIView.addListenerjTextPane_dialogueInput(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    dinoGUIView.showjPopupMenu_listInsertion(me);
                }
            }

        });


    }

    /***************************************************************************
     * Insert Dynamic List
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_InsertDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();
            //TODO Table_Controller.createList(listName)?
            if (listName != null) {
                dinoGUIView.insertButtonjTextPane_DynamicList(listName, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO Table_Controller.openList(listName)?
                    }
                }, Color.yellow);

                dinoGUIView.addItemjPopupMenu_listInsertion(listName, new listener_jPopupMenu_listInsertion_SelectExistingList(listName));

                table_controller.addList(listName);

            }

            dinoGUIView.setFocusTSDialogueInput();
        }
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
     * Insert Static Variable
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_InsertStaticVariable implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String varName = dinoGUIView.requestListNamejOptionPane_listInsertion(); // get name of
            if (varName != null) {
                dinoGUIView.insertButtonjTextPane_StaticVar(varName, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO Table_Controller.openList(listName)?
                    }
                }, Color.red);

                dinoGUIView.addItemjPopupMenu_listInsertion(varName, new listener_jPopupMenu_listInsertion_SelectExistingList(varName));

                dinoGUIModel.addStaticVar(varName);

                dinoGUIView.setFocusTSDialogueInput();
            }
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
            dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
            dinoGUIModel.writeToFile();

            table_controller.writeToFile();
        }
    }


    /***************************************************************************
     * get ListNames
     *
     **************************************************************************/
    public HashSet<String> getSetListNames() {
        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        return dinoGUIView.getSetListNames();
    }

    /***************************************************************************
     * get Dialogue
     *
     **************************************************************************/
    public String getText_jTextPane_dialogueInput() {
        return dinoGUIView.getText_jTextPane_dialogueInput();
    }

}
