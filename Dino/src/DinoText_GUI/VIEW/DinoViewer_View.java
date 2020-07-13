package DinoText_GUI.VIEW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DinoViewer_View extends JFrame {
    private JTextField JTextField_Input;
    private JPanel panel1;
    private JLabel JLabel_Filename;
    private JTextPane JTextPane_Output;

    public DinoViewer_View() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 580);
        this.setVisible(true);
    }

    // getters
    public String getJtextField_Input() {
        return JTextField_Input.getText();
    }

    public String getJLabel_Filename() {
        return JLabel_Filename.getText();
    }

    public String getJTextPane_Output() {
        return JTextPane_Output.getText();
    }

    // setters
    public void setJTextField_Input(String input) {
        JTextField_Input.setText(input);
    }

    public void setJLabel_Filename(String input) {
        JLabel_Filename.setText(input);
    }

    public void setJTextPane_Output(String output) {
        JTextPane_Output.setText(output);
    }

    // action Listener

    public void addListener_JTextField_Input(ActionListener listenForJTextField_input) {
        JTextField_Input.addActionListener(listenForJTextField_input);
    }

}
