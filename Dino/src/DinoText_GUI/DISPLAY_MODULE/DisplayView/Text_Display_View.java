// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Component;
import java.awt.event.ActionListener;

/*******************************************************************************
 * Text Display View
 *
 ******************************************************************************/
public class Text_Display_View extends JFrame
{
    private JPanel panel;
    private JTextPane textPane;
    private JButton nextButton;
    private JButton prevButton;
    private JTextPane pageCounter;
    private JButton generateNewButton;
    private JButton traitSettings;
    private JButton variableSettings;
    private JButton settingsButton;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Text_Display_View()
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

    /***************************************************************************
     * Listener - Generate New Button
     *
     **************************************************************************/
    public void add_generateNew_button_listener(ActionListener actionListener)
    {
        this.generateNewButton.addActionListener(actionListener);
    }

    /***************************************************************************
     * Listener - Trait Settings Button
     *
     **************************************************************************/
    public void traitSettingsListener(ActionListener actionListener) { traitSettings.addActionListener(actionListener); }

    /***************************************************************************
     * Listener - Variable Settings Button
     *
     **************************************************************************/
    public void variableSettingsListener(ActionListener actionListener) { variableSettings.addActionListener(actionListener); }

    /***************************************************************************
     * Listener - Next Button
     *
     **************************************************************************/
    public void nextButtonListener(ActionListener actionListener) {
        nextButton.addActionListener(actionListener);
    }

    /***************************************************************************
     * Listener - Previous Button
     *
     **************************************************************************/
    public void prevButtonListener(ActionListener actionListener) {
        prevButton.addActionListener(actionListener);
    }

    public void settingsButtonListener(ActionListener actionListener) { settingsButton.addActionListener(actionListener); }

    /***************************************************************************
     * set Text Pane
     *
     **************************************************************************/
    public void setTextPane(String str) {
        textPane.setText(str);
    }

    /***************************************************************************
     * Set Page Counter
     *
     **************************************************************************/
    public void setPageCounter(String page) { pageCounter.setText(page); }

    /***************************************************************************
     * set PanelVisible
     *
     **************************************************************************/
    public void setPanelVisible(boolean bool) {
        panel.setVisible(bool);
        SwingUtilities.getWindowAncestor(panel).pack();
    }

    /***************************************************************************
     * Panel is Visible
     *
     **************************************************************************/
    public boolean panelIsVisible() {
        return panel.isVisible();
    }

    /***************************************************************************
     * get JPanel
     *
     **************************************************************************/
    public Component getJpanel() {
        return panel;
    }
}
