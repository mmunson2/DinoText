package DinoText_GUI.VIEW;

import javax.swing.*;

public class DinoGUIView extends JFrame{

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
        this.setContentPane(new DinoGUIView().panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /*************************************
     * SET TEXT
     ************************************/
    public void setText_jTextField_input(String input){
        jTextField_input.setText(input);
    }

    public void setText_jTextPane_dialogueInput(String input){
        jTextPane_dialogueInput.setText(input);
    }

    public void setText_jTextPane_output(String input){
        jTextPane_output.setText(input);
    }

    public void setText_jTextPane_listContents(String input){
        jTextPane_output.setText(input);
    }

    /*************************************
     * GET TEXT
     ************************************/
    public String getText_jTextField_input(){
        return jTextField_input.getText();
    }

    public String getText_jTextPane_dialogueInput(){
        return jTextPane_dialogueInput.getText();
    }

    public String getText_jTextPane_output(){
        return jTextPane_output.getText();
    }

    public String getText_jTextPane_listContents(){
        return jTextPane_output.getText();
    }

    /*************************************
     * SET VISIBILITY
     ************************************/
    public void setWriteButtonVisible(boolean bool) {
        if (bool)
        jButton_writeToFile.setVisible(true);
        else
            jButton_writeToFile.setVisible(false);
    }

    public void setSubmitButtonVisible(boolean bool) {
        if (bool)
        jButton_submit.setVisible(true);
        else
            jButton_submit.setVisible(false);
    }

    public void setDialogueVisible(boolean bool) {
        if (bool) {
            jTextPane_dialogueInput.setVisible(true);
            jScrollPane_dialogueInput.setVisible(true);
        }
        else {
            jTextPane_dialogueInput.setVisible(false);
            jScrollPane_dialogueInput.setVisible(false);
        }
    }

    public void setInputVisible(boolean bool) {
        if (bool)
        jTextField_input.setVisible(true);
        else
            jTextField_input.setVisible(false);
    }

    public void setListContentsVisible(boolean bool) {
        if (bool) {
            jScrollPane_listContents.setVisible(true);
            jTextPane_listContents.setVisible(true);
            jLabel_listContents.setVisible(true);
        }
        else {
            jScrollPane_listContents.setVisible(false);
            jTextPane_listContents.setVisible(false);
            jLabel_listContents.setVisible(false);
        }
    }

}
