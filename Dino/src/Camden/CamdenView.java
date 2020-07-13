package Camden;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class CamdenView extends JFrame {
    private JPanel panel;
    private JTextPane textPane;
    private JButton nextButton;
    private JButton prevButton;
    private JTextPane pageCounter;
    private JSpinner charactersSpinner;
    private JSpinner linesSpinner;
    private JSpinner fontSizeSpinner;

    public CamdenView() {
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Dialogue Preview");
    }

    // listeners
    public void nextButtonListener(ActionListener actionListener) {
        nextButton.addActionListener(actionListener);
    }

    public void prevButtonListener(ActionListener actionListener) {
        prevButton.addActionListener(actionListener);
    }

    public void charactersSpinnerListener(ChangeListener changeListener) {
        charactersSpinner.addChangeListener(changeListener);
    }

    public void linesSpinnerListener(ChangeListener changeListener) {
        linesSpinner.addChangeListener(changeListener);
    }

    // getters
    public int getCharsPerLine() {
        return (int) charactersSpinner.getValue();
    }

    public int getLinesPerPage() {
        return (int) linesSpinner.getValue();
    }

    // setters
    public void setTextPane(String str) {
        textPane.setText(str);
    }

    public void setPageCounter(int page) {
        pageCounter.setText(Integer.toString(page));
    }

    public void setCharactersSpinner(int numChar) {
        charactersSpinner.setValue(numChar);
    }

    public void setLinesSpinner(int numLines) {
        linesSpinner.setValue(numLines);
    }

    public Component getJpanel() {
        return panel;
    }
}
