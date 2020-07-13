package DinoText_GUI.VIEW.Dialogue;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoText_Dialogue_View extends JInternalFrame {

    private JPanel jPanel_dialogueEditor;

    private JTextField jTextField_input;

    private JScrollPane jScrollPane_dialogueInput;


    private JTextPane jTextPane_dialogueInput;

    private JLabel jLabel_instructions;


    public DinoText_Dialogue_View() {
        this.setContentPane(jPanel_dialogueEditor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public DinoText_Dialogue_View(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, true, true, true, true);
        this.setContentPane(jPanel_dialogueEditor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /*************************************
     * ACTION LISTENERS
     ************************************/
    public void addjTextField_inputListener(ActionListener listenForjTextField_input) {
        jTextField_input.addActionListener(listenForjTextField_input);
    }

    public void removejTextField_inputListener(ActionListener listenForjTextField_input) {
        jTextField_input.removeActionListener(listenForjTextField_input);
    }

    /*************************************
     * SET TEXT
     ************************************/
    public void setText_jTextField_input(String input) {
        jTextField_input.setText(input);
    }

    public void setText_jTextPane_dialogueInput(String input) {
        jTextPane_dialogueInput.setText(input);
    }

    /*************************************
     * GET TEXT
     ************************************/
    public String getText_jTextField_input() {
        return jTextField_input.getText();
    }

    public String getText_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getText();
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

    /*************************************
     * POPUP MENU
     ************************************/
    class jPopupMenu_ListInsertion extends JPopupMenu {
        JMenuItem anItem;

        public jPopupMenu_ListInsertion() {
            addItems();
        }
    }

    private void addItems() {

    }
}