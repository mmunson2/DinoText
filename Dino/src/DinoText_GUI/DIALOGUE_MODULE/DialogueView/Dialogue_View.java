package DinoText_GUI.DIALOGUE_MODULE.DialogueView;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

/*******************************************************************************
 * Dialogue View
 *
 ******************************************************************************/
public class Dialogue_View {
    private JPanel jPanel_dialogueEditor;
    private JScrollPane jScrollPane_dialogueInput;
    private JPopupMenu jPopupMenu_listInsertion;
    private JTextPane jTextPane_dialogueInput;
    private JMenu jMenu_listInsertion;
    private JToolBar jToolBar_topBar;
    private ArrayList<JButton> listButtons; //  buttons in the popup that may not be in the dialogue
    private HashSet<String> listNames;
    private JDialog jDialog_Preferences = new JDialog();
    private JLabel jLabel_title;
    private JOptionPane jOptionPane_Preferences = new JOptionPane("Preferences");
    private int numArrows;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Dialogue_View() {
        // TODO: DELETE WHEN PREFERENCES MENU IMPLEMENTED
        jDialog_Preferences.setContentPane(jOptionPane_Preferences);
        jDialog_Preferences.pack();
        // END TODO

        jPopupMenu_listInsertion = new JPopupMenu();
        jMenu_listInsertion = new JMenu("Insert New");
        jPopupMenu_listInsertion.add(jMenu_listInsertion);

        listButtons = new ArrayList<>();
        listNames = new HashSet<>();

        jPanel_dialogueEditor.setComponentPopupMenu(jPopupMenu_listInsertion);
        numArrows = 0;
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

    public JMenu getjMenu_listInsertion() {
        return jMenu_listInsertion;
    }

    /***************************************************************************
     * get text pane
     **************************************************************************/
    public JTextPane getjTextPane_dialogueInput() {
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

    /***************************************************************************
     * get all buttons
     **************************************************************************/
    public ArrayList<JButton> getAllListButtons() {
        return listButtons;
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

    public void setCaretToEnd() {
        jTextPane_dialogueInput.setCaretPosition(jTextPane_dialogueInput.getStyledDocument().getLength());
        jTextPane_dialogueInput.requestFocus();
    }

    /***************************************************************************
     * insert Button to JTextPane (Dynamic List)
     **************************************************************************/
    public void insertButtonjTextPane_DynamicList(String listName, ActionListener actionListener, Color color) {
        jTextPane_dialogueInput.insertComponent(makeButtonjTextPane_DynamicList(listName, actionListener, null));
    }

    public JButton makeButtonjTextPane_DynamicList(String listName, ActionListener actionListener, Color color) {
        JButton newList = new JButton(listName);
        newList.setName(listName);
        newList.setText(listName);
        newList.addActionListener(actionListener);
        newList.setBackground(null);
        listButtons.add(newList);
        return newList;
    }

    /***************************************************************************
     * Get List Names
     *
     **************************************************************************/
    public HashSet<String> getSetListNames() {
        listNames = new HashSet<>();
        for (JButton jb : listButtons) {
            listNames.add(jb.getName());
        }

        return listNames;
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
     * add item to popup menu - new insertion
     *
     **************************************************************************/
    public void addItemjPopupMenu_listInsertion(JMenuItem temp) {
        // else
        jMenu_listInsertion.add(temp);
        jPopupMenu_listInsertion.pack();
    }

    /***************************************************************************
     * remove item to popup menu
     *
     **************************************************************************/
    public void removeItemjPopupMenu_listInsertion(String item) {
        for (Component c : getjMenu_listInsertion().getMenuComponents()) {
            if (((JMenuItem) c).getText() == item) {
                jMenu_listInsertion.remove(c);
//                removeListName(((JMenuItem) c).getText());
            }
        }
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
    public String requestListNamejOptionPane_listInsertion(String category) {
        JTextArea textArea = new JTextArea();
        String listName = JOptionPane.showInputDialog(category + ":");
        return listName;
    }

    /***************************************************************************
     * Clear List Button
     *
     **************************************************************************/
    public void clearListButtonsjPopupMenu() {
        listButtons.clear();
    }

    /***************************************************************************
     * Get Active List Buttons
     *
     **************************************************************************/
    public ArrayList<JButton> getActiveListButtons() {
        ArrayList<JButton> activeButtons = new ArrayList<JButton>();
        ElementIterator iterator = new ElementIterator(jTextPane_dialogueInput.getStyledDocument());
        Element element;
        while ((element = iterator.next()) != null) {
            AttributeSet as = element.getAttributes();
            if (as.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                if (StyleConstants.getComponent(as) instanceof JButton) {
                    activeButtons.add((JButton) StyleConstants.getComponent(as));
                }
            }
        }
        return activeButtons;
    }


    /***************************************************************************
     * Set Caret Position
     *
     **************************************************************************/
    public void setCaret_jTextPane_dialogueInput(int pos) {
        jTextPane_dialogueInput.setCaretPosition(pos);
    }

    public void pack() {
        SwingUtilities.getWindowAncestor(jTextPane_dialogueInput).pack();
    }

    public int getCaret_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getCaretPosition();
    }

    public void clearjTextPane_dialogueInput() {
        setText_jTextPane_dialogueInput("");
    }

    public void insertLabel_Arrow_jTextPane() {
        JLabel arrow = new JLabel();
        arrow.setFocusable(false);
        arrow.setText("  \u2794  ");
        if (numArrows < getActiveListButtons().size() - 1) {
            jTextPane_dialogueInput.insertComponent(arrow);
            numArrows++;
        }
    }

    public void insertAllArrows() {
        clearAllArrows();
        insertArrows();
    }

    private void insertArrows() {
        numArrows = 0;
        //remove any existing arrows
        int cursorPos = 0;
        int components = 0;
        ElementIterator iterator = new ElementIterator(jTextPane_dialogueInput.getStyledDocument());
        Element element;
        Element temp = null;
        while ((element = iterator.next()) != null) {
            if (cursorPos > 0) {
                temp = element;
            }

            AttributeSet as = element.getAttributes();
            if (as.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                if (StyleConstants.getComponent(as) instanceof JButton) { // TODO: Find out where sizeless buttons are coming from

                    components++;

                    if (StyleConstants.getComponent(as).getSize().height <= 0 ||
                            StyleConstants.getComponent(as).getSize().width <= 0) {
                        jTextPane_dialogueInput.remove(StyleConstants.getComponent(as));
                        components--;
                        continue;
                    }


                    setCaret_jTextPane_dialogueInput(++cursorPos); // increase to account for button
                    insertLabel_Arrow_jTextPane();
                    cursorPos++; // increase to account for new arrow
                }
            }
        }
    }


    private void clearAllArrows() {
        //remove any existing arrows


        ElementIterator iterator = new ElementIterator(jTextPane_dialogueInput.getStyledDocument());
        Element element;
        int caretPos = 0;

        while ((element = iterator.next()) != null) {
            AttributeSet as = element.getAttributes();

            if (as.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                if (StyleConstants.getComponent(as) instanceof JLabel) {
                    try {
                        jTextPane_dialogueInput.getDocument().remove(caretPos, 1);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
                caretPos++;
            }
        }
    }
}
