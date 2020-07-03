package DinoText_GUI.VIEW;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoText_Dialogue_View extends JInternalFrame {

    private JPanel jPanel_dialogueEditor;

    private JTextField jTextField_input;

    private JScrollPane jScrollPane_dialogueInput;
    private JScrollPane jScrollPane_listContents;

    private JButton jButton_submit;

    private JTextPane jTextPane_output;
    private JTextPane jTextPane_listContents;
    private JTextPane jTextPane_dialogueInput;

    private JLabel jLabel_instructions;
    private JLabel jLabel_listContents;

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
    public void addjButton_submitListener(ActionListener listenForjButton_submit) {
        jButton_submit.addActionListener(listenForjButton_submit);
    }

    public void removejButton_submitListener(ActionListener listenForjButton_submit) {
        jButton_submit.removeActionListener(listenForjButton_submit);
    }

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

    public void setText_jTextPane_output(String input) {
        jTextPane_output.setText(input);
    }

    public void setText_jTextPane_listContents(String input) {
        jTextPane_listContents.setText(input);
    }

    public void setText_jButton_submit(String input) { jButton_submit.setText(input); }

    /*************************************
     * GET TEXT
     ************************************/
    public String getText_jTextField_input() {
        return jTextField_input.getText();
    }

    public String getText_jTextPane_dialogueInput() {
        return jTextPane_dialogueInput.getText();
    }

    public String getText_jTextPane_output() {
        return jTextPane_output.getText();
    }

    public String getText_jTextPane_listContents() {
        return jTextPane_output.getText();
    }


    /*************************************
     * SET VISIBILITY
     ************************************/
    public void setVisiblejButton_submit(boolean bool) {
        jButton_submit.setVisible(bool);
    }

    public void setVisibleTSDialogueInput(boolean bool) {
        jTextPane_dialogueInput.setVisible(bool);
        jScrollPane_dialogueInput.setVisible(bool);
    }

    public void setVisiblejTextField_input(boolean bool) {
        jTextField_input.setVisible(bool);
    }

    public void setVisibleTSLListContents(boolean bool) {
        jScrollPane_listContents.setVisible(bool);
        jTextPane_listContents.setVisible(bool);
        jLabel_listContents.setVisible(bool);
    }

    public void setVisiblejTextPane_output(boolean bool) {
        jTextPane_output.setVisible(bool);
    }

    public void setVisiblejLabel_instructions(boolean bool) {
        jLabel_instructions.setVisible(bool);
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
}
