package DinoText_GUI.CONTROLLER.Dialouge;

import DinoParser.Dino;
import DinoText_GUI.CONTROLLER.Table.Table_Controller;
import DinoText_GUI.CONTROLLER.Display.Text_Display_Controller;
import DinoText_GUI.MODEL.Dialogue.Dialogue_Model;
import DinoText_GUI.VIEW.Dialogue.Dialogue_View;

import javax.swing.*;
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
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Save");
        menuItem.addActionListener(new listener_JMenuItem_Save());
        menu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("New");
        menuItem.addActionListener(new listener_JMenuItem_New());
        menu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("Preview");
        menuItem.addActionListener(new listener_JMenuItem_Preview());
        menu.add(menuItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(menu);

        dinoGUIView.addJMenujToolBar_topBar(jMenuBar);

        JButton temp = new JButton("Insert Dynamic List");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_InsertDynamicList());
        dinoGUIView.addButtonjToolBar_topBar(temp);
        temp = new JButton("Delete Dynamic List");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_DeleteDynamicList());
        //dinoGUIView.addButtonjToolBar_topBar(temp);
        temp = new JButton("Write to File");
        temp.addActionListener(new listener_jPopupMenu_listInsertion_WriteToFile());
        //dinoGUIView.addButtonjToolBar_topBar(temp);
    }

    /***************************************************************************
     * Save
     *
     **************************************************************************/
    public void save()
    {
        String fileName = JOptionPane.showInputDialog("Dialogue File Name: ");

        dinoGUIModel.setName(fileName);

        dinoGUIModel.setListNames(dinoGUIView.getSetListNames());
        dinoGUIModel.newDialogue(dinoGUIView.getText_jTextPane_dialogueInput());
        dinoGUIModel.writeToFile();

        table_controller.writeToFile();

        Dino dino = new Dino(fileName);
        textDisplayController.setDino(dino);
    }


    /***************************************************************************
     * Dropdown Menu - Save
     *
     **************************************************************************/
    class listener_JMenuItem_Save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            save();
        }
    }

    /***************************************************************************
     * New
     *
     **************************************************************************/
    class listener_JMenuItem_New implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    /***************************************************************************
     * Dropdown Menu - Preview
     *
     **************************************************************************/
    class listener_JMenuItem_Preview implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            save();


            if (textDisplayController.panelIsVisible()) {
                textDisplayController.setPanelVisible(false);
            } else {
                textDisplayController.setPanelVisible(true);
            }
        }
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
                dinoGUIView.insertButtonjTextPane(listName, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO Table_Controller.openList(listName)?
                    }
                }, Color.yellow);

                dinoGUIView.addItemjPopupMenu_listInsertion(listName, new listener_jPopupMenu_listInsertion_SelectExistingList(listName));

                table_controller.addList(listName);
            }
        }
    }

    /***************************************************************************
     * Select Existing List
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_SelectExistingList implements ActionListener {
        private String listName;

        listener_jPopupMenu_listInsertion_SelectExistingList(String name) {
            super();
            listName = name;
        }

        public void actionPerformed(ActionEvent e) {
            dinoGUIView.insertButtonjTextPane(listName, dinoGUIView.getButton(listName).getAction(), Color.yellow); //TODO this button may not link to the right name
        }
    }

    /***************************************************************************
     * Delete Dynamic List
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_DeleteDynamicList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String listName = dinoGUIView.requestListNamejOptionPane_listInsertion();
            System.out.println(dinoGUIView.getText_jTextPane_dialogueInput());
            //TODO dinoGUIView.removeButtonjPopupMenu(dinoGUIView.getButton(listName)); CONCURRENT MODIFICATION EXCEPTION
        }
    }

    /***************************************************************************
     * Write To File
     *
     **************************************************************************/
    class listener_jPopupMenu_listInsertion_WriteToFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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
    public String getText_jTextPane_dialogueInput() { return dinoGUIView.getText_jTextPane_dialogueInput(); }
}
