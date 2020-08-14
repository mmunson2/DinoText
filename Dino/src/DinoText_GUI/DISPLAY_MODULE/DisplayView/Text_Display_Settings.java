// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class Text_Display_Settings extends JFrame
{
    private JSpinner charactersSpinner;
    private JSpinner linesSpinner;
    private JPanel panel;

    public Text_Display_Settings()
    {
        this.setContentPane(panel);
        this.setSize(300, 300);
        this.setTitle("Preview Settings");
    }

    public void charactersSpinnerListener(ChangeListener changeListener) { charactersSpinner.addChangeListener(changeListener); }

    public void linesSpinnerListener(ChangeListener changeListener) {
        linesSpinner.addChangeListener(changeListener);
    }

    public int getCharsPerLine() {
        return (int) charactersSpinner.getValue();
    }

    public int getLinesPerPage() {
        return (int) linesSpinner.getValue();
    }

    public void setCharactersSpinner(int numChar) {
        charactersSpinner.setValue(numChar);
    }

    public void setLinesSpinner(int numLines) {
        linesSpinner.setValue(numLines);
    }
}
