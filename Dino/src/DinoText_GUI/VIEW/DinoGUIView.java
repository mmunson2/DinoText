package DinoText_GUI.VIEW;

import javax.swing.*;

public class DinoGUIView extends JFrame {

    private JPanel panel1;

    private JTextField jTextField_input;

    private JScrollPane jScrollPane_dialogueInput;
    private JScrollPane jScrollPane_listContents;

    private JButton jButton_writeToFile;
    private JButton jButton_submit;

    private JTextPane jTextPane_output;
    private JTextPane jTextPane_listContents;
    private JTextPane jTextPane_dialogueInput;

    private JLabel jLabel_instructions;
    private JLabel jLabel_listContents;


    public DinoGUIView() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
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
        jTextPane_output.setText(input);
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

    public String getText_jTextPane_output() {
        return jTextPane_output.getText();
    }

    public String getText_jTextPane_listContents() {
        return jTextPane_output.getText();
    }

    /*************************************
     * SET VISIBILITY
     ************************************/
    public void setWriteButtonVisible(boolean bool) {
        jButton_writeToFile.setVisible(bool);
    }

    public void setSubmitButtonVisible(boolean bool) {
        jButton_submit.setVisible(bool);
    }

    public void setDialogueVisible(boolean bool) {
        jTextPane_dialogueInput.setVisible(bool);
        jScrollPane_dialogueInput.setVisible(bool);
    }

    public void setInputVisible(boolean bool) {
        jTextField_input.setVisible(bool);
    }

    public void setListContentsVisible(boolean bool) {
        jScrollPane_listContents.setVisible(bool);
        jTextPane_listContents.setVisible(bool);
        jLabel_listContents.setVisible(bool);

    }

    public void setjLabel_instructionsVisible(boolean bool) {
        jLabel_instructions.setVisible(bool);
    }

}
