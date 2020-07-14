package Camden;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

public class CamdenView extends JFrame
{
    private JPanel panel;
    private JTextPane textPane;
    private JButton nextButton;
    private JButton prevButton;
    private JTextPane pageCounter;
    private JSpinner charactersSpinner;
    private JSpinner linesSpinner;
    private JSpinner fontSizeSpinner;

    public CamdenView()
    {
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Dialogue Preview");

        StyledDocument doc = pageCounter.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    // listeners
    public void nextButtonListener(ActionListener actionListener) {
        nextButton.addActionListener(actionListener);
    }

    public void prevButtonListener(ActionListener actionListener) {
        prevButton.addActionListener(actionListener);
    }

    public void charactersSpinnerListener(ChangeListener changeListener) { charactersSpinner.addChangeListener(changeListener); }

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

    public void setPageCounter(String page) { pageCounter.setText(page); }

    public void setCharactersSpinner(int numChar) {
        charactersSpinner.setValue(numChar);
    }

    public void setLinesSpinner(int numLines) {
        linesSpinner.setValue(numLines);
    }

    // ihsan added
    public void setPanelVisible(boolean bool) {
        panel.setVisible(bool);
        SwingUtilities.getWindowAncestor(panel).pack();
    }

    public boolean panelIsVisible() {
        return panel.isVisible();
    }

    public Component getJpanel() {
        return panel;
    }
}
