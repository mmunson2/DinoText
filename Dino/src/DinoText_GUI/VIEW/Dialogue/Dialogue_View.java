package DinoText_GUI.VIEW.Dialogue;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*******************************************************************************
 * Dialogue View
 *
 ******************************************************************************/
public class Dialogue_View {
    private JPanel jPanel_dialogueEditor;
    private JScrollPane jScrollPane_dialogueInput;
    private JPopupMenu jPopupMenu_listInsertion;
    private JTextPane jTextPane_dialogueInput;
    private JLabel jLabel_instructions;
    private JToolBar jToolBar_topBar;
    private ArrayList<JButton> listButtons;
    private ArrayList<JButton> staticVarButtons;
    private HashSet<String> listNames;
    private JDialog jDialog_Preferences = new JDialog();
    private JOptionPane jOptionPane_Preferences = new JOptionPane("Preferences");

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Dialogue_View() {
        // TODO: DELETE WHEN PREFERENCES MENU IMPLEMENTED
        jDialog_Preferences.setContentPane(jOptionPane_Preferences);
        jDialog_Preferences.pack();
        // END TODO

        listButtons = new ArrayList<JButton>();
        staticVarButtons = new ArrayList<JButton>();
        listNames = new HashSet<String>();
        jPopupMenu_listInsertion = new JPopupMenu();
        jPanel_dialogueEditor.setComponentPopupMenu(jPopupMenu_listInsertion);
    }

    /***************************************************************************
     * set jOptionPane_Preferences
     * use this to pass in the JOptionPane from another class
     **************************************************************************/
    public void setjOptionPane_Preferences(JOptionPane pane) {
        jOptionPane_Preferences = pane;
        jDialog_Preferences.setContentPane(jOptionPane_Preferences);
        jDialog_Preferences.pack();
    }

    /***************************************************************************
     * set visibility of jOptionPane_Preferences
     **************************************************************************/
    public void setVisiblejDialog_Preferences(boolean bool) {
        jDialog_Preferences.setVisible(bool);
    }

    /***************************************************************************
     * add button to toolbar
     **************************************************************************/
    public void addButtonjToolBar_topBar(JButton button) {
        jToolBar_topBar.add(button);
    }

    /***************************************************************************
     * add menu to toolbar
     **************************************************************************/
    public void addJMenujToolBar_topBar(JMenuBar menu) {
        jToolBar_topBar.add(menu);
    }

    /***************************************************************************
     * Listener - Dialogue Input
     *
     **************************************************************************/
    public void addListenerjTextPane_dialogueInput(MouseAdapter mouseAdapter) {
        jTextPane_dialogueInput.addMouseListener(mouseAdapter);
    }

    /***************************************************************************
     * set dialogue ext
     **************************************************************************/
    public void setText_jTextPane_dialogueInput(String input) {
        jTextPane_dialogueInput.setText(input);
    }

    /***************************************************************************
     * get dialogue editor
     **************************************************************************/
    public JPanel getjPanel_dialogueEditor() {
        return jPanel_dialogueEditor;
    }

    /***************************************************************************
     * get text
     **************************************************************************/
    public String getText_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getText();
    }

    /***************************************************************************
     * get JPopup menu
     **************************************************************************/
    public JPopupMenu getjPopupMenu_listInsertion() {
        jPopupMenu_listInsertion.setInvoker(jTextPane_dialogueInput);
        return jPopupMenu_listInsertion;
    }

    /***************************************************************************
     * get text pane
     **************************************************************************/
    public Component getjTextPane_dialogueInput() {
        return jTextPane_dialogueInput;
    }

    /***************************************************************************
     * get Button
     **************************************************************************/
    public JButton getListButton(String name) {
        for (JButton button : listButtons) {
            if (button.getName().equals(name)) {
                return button;
            }
        }
        return null;
    }

    public JButton getStaticVarButton(String name) {
        for (JButton button : staticVarButtons) {
            if (button.getName().equals(name)) {
                return button;
            }
        }
        return null;
    }

    /***************************************************************************
     * get all buttons
     **************************************************************************/
    public ArrayList<JButton> getAllListButtons() {
        return listButtons;
    }

    public ArrayList<JButton> getAllStaticVarButtons() {
        return staticVarButtons;
    }

    /*************************************
     * SET VISIBILITY
     ************************************/

    /***************************************************************************
     * Make dialogue input visible
     **************************************************************************/
    public void setVisibleTSDialogueInput(boolean bool) {
        jTextPane_dialogueInput.setVisible(bool);
        jScrollPane_dialogueInput.setVisible(bool);
    }

    /*************************************
     * FOCUS REQUEST
     ************************************/

    /***************************************************************************
     * setFocus
     **************************************************************************/
    public void setFocusTSDialogueInput() {
        jTextPane_dialogueInput.requestFocusInWindow();
    }

    /*************************************
     * MISC
     ************************************/

    /***************************************************************************
     * insert Button to JTextPane (Static Variable)
     **************************************************************************/
    public void insertButtonjTextPane_StaticVar(String varName, ActionListener actionListener, Color color) {
        AttributeSet current = jTextPane_dialogueInput.getParagraphAttributes();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontSize(set, 0);

        int caretPos = jTextPane_dialogueInput.getCaretPosition();
        try {
            jTextPane_dialogueInput.getDocument().insertString(caretPos, "\\S[" + varName + "]", set); //TODO: How are we representing static vars?
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JButton newList = new JButton(varName);
        newList.setName(varName);
        newList.setText(varName);
        newList.addActionListener(actionListener);
        newList.setBackground(color);
        jTextPane_dialogueInput.insertComponent(newList);
        staticVarButtons.add(newList);
//
//        try {
//            jTextPane_dialogueInput.getDocument().insertString(caretPos + (varName.length() + 5), "\b", null);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }

    }
    /***************************************************************************
     * insert Button to JTextPane (Dynamic List)
     **************************************************************************/
    public void insertButtonjTextPane_DynamicList(String listName, ActionListener actionListener, Color color) {
        AttributeSet current = jTextPane_dialogueInput.getParagraphAttributes();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontSize(set, 0);

        int caretPos = jTextPane_dialogueInput.getCaretPosition();
        try {
            jTextPane_dialogueInput.getDocument().insertString(caretPos, "\\L[" + listName + "]", set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JButton newList = new JButton(listName);
        newList.setName(listName);
        newList.setText(listName);
        newList.addActionListener(actionListener);
        newList.setBackground(color);
        jTextPane_dialogueInput.insertComponent(newList);
        listButtons.add(newList);
//
//        try {
//            jTextPane_dialogueInput.getDocument().insertString(caretPos + (listName.length() + 5), "\b", null);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }

    }

    /***************************************************************************
     * Get List Names
     *
     **************************************************************************/
    public HashSet<String> getSetListNames() {
        for (JButton jb : listButtons) {
            listNames.add(jb.getName());
        }
        return listNames;
    }

    /***************************************************************************
     * Set List Names
     *
     **************************************************************************/
    public void setSetListNames(HashSet<String> newNames) {
        listNames = newNames;
    }

    /*************************************
     * POPUP MENU
     ************************************/

    /***************************************************************************
     * set popup menu invoker
     *
     **************************************************************************/
    public void setInvokerjPopupMenu_listInsertion(Component invoker) {
        jPopupMenu_listInsertion.setInvoker(invoker);
    }

    /***************************************************************************
     * add item to popup menu
     *
     **************************************************************************/
    public void addItemjPopupMenu_listInsertion(String listName, ActionListener action) {
        JMenuItem item = new JMenuItem("Insert New " + listName);
        jPopupMenu_listInsertion.add(item);
        item.addActionListener(action);
    }

    /***************************************************************************
     * remove item to popup menu
     *
     **************************************************************************/
    public void removeItemjPopupMenu_listInsertion(int oldPos) {
        jPopupMenu_listInsertion.remove(oldPos);
    }

    /***************************************************************************
     * show popup menu
     *
     **************************************************************************/
    public void showjPopupMenu_listInsertion(MouseEvent e) {
        jPopupMenu_listInsertion.show(e.getComponent(), e.getX(), e.getY());
    }

    /***************************************************************************
     * Request list name
     *
     **************************************************************************/
    public String requestListNamejOptionPane_listInsertion() {
        JTextArea textArea = new JTextArea();
        String listName = JOptionPane.showInputDialog("List Name: ");
        return listName;
    }

    /***************************************************************************
     * Remove button from popup menu
     *
     **************************************************************************/
    public void removeButtonjPopupMenu(JButton button) {
        for (JButton jb : listButtons) {
            if (jb.equals(button)) {
                listButtons.remove(jb);
            }
        }
    }

    public String getSelectedText_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getSelectedText();
    }
}
