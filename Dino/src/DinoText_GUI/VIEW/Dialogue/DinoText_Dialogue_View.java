package DinoText_GUI.VIEW.Dialogue;

import org.apache.commons.math3.ode.events.EventHandler;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DinoText_Dialogue_View {
    private JPanel jPanel_dialogueEditor;
    private JScrollPane jScrollPane_dialogueInput;
    private JPopupMenu jPopupMenu_listInsertion;
    private JTextPane jTextPane_dialogueInput;
    private JLabel jLabel_instructions;
    private JToolBar jToolBar_topBar;
    private ArrayList<JButton> listButtons;
    private HashSet<String> listNames;


    public DinoText_Dialogue_View() {
        listButtons = new ArrayList<JButton>();
        listNames = new HashSet<String>();
        jPopupMenu_listInsertion = new JPopupMenu();
        jPanel_dialogueEditor.setComponentPopupMenu(jPopupMenu_listInsertion);
    }

    /*************************************
     * JTOOLBAR
     ************************************/
    public void addButtonjToolBar_topBar(JButton button) {
        jToolBar_topBar.add(button);
    }

    public void addJMenujToolBar_topBar(JMenuBar menu) {
        jToolBar_topBar.add(menu);
    }

    /*************************************
     * ACTION LISTENERS
     ************************************/

    public void addListenerjTextPane_dialogueInput(MouseAdapter mouseAdapter) {
        jTextPane_dialogueInput.addMouseListener(mouseAdapter);
    }

    /*************************************
     * SETTER
     ************************************/

    public void setText_jTextPane_dialogueInput(String input) {
        jTextPane_dialogueInput.setText(input);
    }

    /*************************************
     * GETTER
     ************************************/
    public JPanel getjPanel_dialogueEditor() {
        return jPanel_dialogueEditor;
    }

    public String getText_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getText();
    }

    public JPopupMenu getjPopupMenu_listInsertion() {
        jPopupMenu_listInsertion.setInvoker(jTextPane_dialogueInput);
        return jPopupMenu_listInsertion;
    }

    public Component getjTextPane_dialogueInput() {
        return jTextPane_dialogueInput;
    }

    public JButton getButton(String name) {
        for (JButton button : listButtons) {
            if (button.getName().equals(name)) {
                return button;
            }
        }
        return null;
    }

    public ArrayList<JButton> getAllButtons() {
        return listButtons;
    }

    /*************************************
     * SET VISIBILITY
     ************************************/

    public void setVisibleTSDialogueInput(boolean bool) {
        jTextPane_dialogueInput.setVisible(bool);
        jScrollPane_dialogueInput.setVisible(bool);
    }

    /*************************************
     * FOCUS REQUEST
     ************************************/
    public void setFocusTSDialogueInput() {
        jTextPane_dialogueInput.requestFocusInWindow();
    }

    /*************************************
     * MISC
     ************************************/

    public void insertButtonjTextPane(String listName, ActionListener actionListener, Color color) {
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

    public HashSet<String> getSetListNames() {
        for (JButton jb : listButtons) {
            listNames.add(jb.getName());
        }
        return listNames;
    }

    /*************************************
     * POPUP MENU
     ************************************/
    public void setInvokerjPopupMenu_listInsertion(Component invoker) {
        jPopupMenu_listInsertion.setInvoker(invoker);
    }

    public void addItemjPopupMenu_listInsertion(String listName, ActionListener action) {
        JMenuItem item = new JMenuItem("Insert New " + listName);
        jPopupMenu_listInsertion.add(item);
        item.addActionListener(action);
    }

    public void showjPopupMenu_listInsertion(MouseEvent e) {
        jPopupMenu_listInsertion.show(e.getComponent(), e.getX(), e.getY());
    }

    public String requestListNamejOptionPane_listInsertion() {
        JTextArea textArea = new JTextArea();
        String listName = JOptionPane.showInputDialog("List Name: ");
        return listName;
    }

    public void removeButtonjPopupMenu(JButton button) {
        for (JButton jb : listButtons) {
            if (jb.equals(button)) {
                listButtons.remove(jb);
            }
        }
    }

}
