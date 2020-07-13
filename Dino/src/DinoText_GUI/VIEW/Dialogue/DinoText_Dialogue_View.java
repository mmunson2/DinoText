package DinoText_GUI.VIEW.Dialogue;

import org.apache.commons.math3.ode.events.EventHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DinoText_Dialogue_View {
    private JPanel jPanel_dialogueEditor;
    private JTextField jTextField_input;
    private JScrollPane jScrollPane_dialogueInput;
    private JPopupMenu jPopupMenu_listInsertion;
    private JTextPane jTextPane_dialogueInput;
    private JLabel jLabel_instructions;
    private JToolBar jToolBar_topBar;
    private ArrayList<JButton> listButtons;


    public DinoText_Dialogue_View() {
        listButtons = new ArrayList<JButton>();
        jPopupMenu_listInsertion = new JPopupMenu();
        jPanel_dialogueEditor.setComponentPopupMenu(jPopupMenu_listInsertion);

        initializejToolBar_topBar();
    }

    /*************************************
     * JTOOLBAR
     ************************************/
    private void initializejToolBar_topBar() {
        jToolBar_topBar.add(new JComboBox(new String[]{"Save", "New", "Preview"}));
    }

    public void addButtonjToolBar_topBar(JButton button) {
        jToolBar_topBar.add(button);
    }

    /*************************************
     * ACTION LISTENERS
     ************************************/
    public void editListenerjTextField_input(ActionListener listenForjTextField_input, boolean bool) {
        if (bool) jTextField_input.addActionListener(listenForjTextField_input);
        else jTextField_input.removeActionListener(listenForjTextField_input);
    }

    public void addListenerjTextPane_dialogueInput(MouseAdapter mouseAdapter) {
        jTextPane_dialogueInput.addMouseListener(mouseAdapter);
    }

    /*************************************
     * SETTER
     ************************************/
    public void setText_jTextField_input(String input) {
        jTextField_input.setText(input);
    }

    public void setText_jTextPane_dialogueInput(String input) {
        jTextPane_dialogueInput.setText(input);
    }

    /*************************************
     * GETTER
     ************************************/
    public JPanel getjPanel_dialogueEditor() {
        return jPanel_dialogueEditor;
    }

    public String getText_jTextField_input() {
        return jTextField_input.getText();
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


    /*************************************
     * SET VISIBILITY
     ************************************/

    public void setVisibleTSDialogueInput(boolean bool) {
        jTextPane_dialogueInput.setVisible(bool);
        jScrollPane_dialogueInput.setVisible(bool);
    }

    public void setVisiblejTextField_input(boolean bool) {
        jTextField_input.setVisible(bool);
    }


    /*************************************
     * FOCUS REQUEST
     ************************************/
    public void setFocusTSDialogueInput() {
        jTextPane_dialogueInput.requestFocusInWindow();
    }

    public void setFocusjTextField_input() {
        jTextField_input.requestFocusInWindow();
    }


    /*************************************
     * MISC
     ************************************/
    public void highlightWord(String wordToFind, Color color) throws BadLocationException {
        Highlighter highlighter = jTextPane_dialogueInput.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter highlightPainter =
                new DefaultHighlighter.DefaultHighlightPainter(color);

        String dialogueInputText = jTextPane_dialogueInput.getText();

        String words[] = dialogueInputText.split(" ");

        for (String word : words) {
            if (word.equals(wordToFind)) {
                highlighter.addHighlight(dialogueInputText.indexOf(word), word.length(),
                        highlightPainter);
            }
        }
    }

    public void dehighlightWord(String wordToFind) {
        // TODO: 7/6/2020
    }

    public void insertButtonjTextPane(String listName, ActionListener actionListener, Color color) {
        JButton newList = new JButton(listName);
        newList.setName(listName);
        newList.setText(listName);
        newList.addActionListener(actionListener);
        newList.setBackground(color);
        jTextPane_dialogueInput.insertComponent(newList);
        listButtons.add(newList);
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        jPanel_dialogueEditor = new JPanel();
        jPanel_dialogueEditor.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
        jLabel_instructions = new JLabel();
        jLabel_instructions.setText("Dialogue Editor");
        jLabel_instructions.setVisible(true);
        jPanel_dialogueEditor.add(jLabel_instructions, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jScrollPane_dialogueInput = new JScrollPane();
        jScrollPane_dialogueInput.setVisible(false);
        jPanel_dialogueEditor.add(jScrollPane_dialogueInput, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 300), null, null, 0, false));
        jTextPane_dialogueInput = new JTextPane();
        jTextPane_dialogueInput.setPreferredSize(new Dimension(1, 1));
        jTextPane_dialogueInput.setText("");
        jScrollPane_dialogueInput.setViewportView(jTextPane_dialogueInput);
        jTextField_input = new JTextField();
        jTextField_input.setText("");
        jTextField_input.setVisible(false);
        jPanel_dialogueEditor.add(jTextField_input, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(1, 1), null, 0, false));
        jToolBar_topBar = new JToolBar();
        jToolBar_topBar.setEnabled(true);
        jToolBar_topBar.setFloatable(false);
        jPanel_dialogueEditor.add(jToolBar_topBar, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel_dialogueEditor;
    }
}
