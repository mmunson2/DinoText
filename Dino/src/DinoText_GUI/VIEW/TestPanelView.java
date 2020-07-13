/******************************************************************************************************************
 *      ███        ▄████████    ▄████████     ███             ▄███████▄    ▄████████ ███▄▄▄▄      ▄████████  ▄█
 * ▀█████████▄   ███    ███   ███    ███ ▀█████████▄        ███    ███   ███    ███ ███▀▀▀██▄   ███    ███ ███
 *    ▀███▀▀██   ███    █▀    ███    █▀     ▀███▀▀██        ███    ███   ███    ███ ███   ███   ███    █▀  ███
 *     ███   ▀  ▄███▄▄▄       ███            ███   ▀        ███    ███   ███    ███ ███   ███  ▄███▄▄▄     ███
 *     ███     ▀▀███▀▀▀     ▀███████████     ███          ▀█████████▀  ▀███████████ ███   ███ ▀▀███▀▀▀     ███
 *     ███       ███    █▄           ███     ███            ███          ███    ███ ███   ███   ███    █▄  ███
 *     ███       ███    ███    ▄█    ███     ███            ███          ███    ███ ███   ███   ███    ███ ███▌    ▄
 *    ▄████▀     ██████████  ▄████████▀     ▄████▀         ▄████▀        ███    █▀   ▀█   █▀    ██████████ █████▄▄██
 ******************************************************************************************************************/

package DinoText_GUI.VIEW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TestPanelView extends JInternalFrame {
    private JButton jButton_getTime;
    private JTextPane jTextPane_currentTime;
    private JPanel jPanel_testPanel;

    public TestPanelView() {
        this.setContentPane(jPanel_testPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public TestPanelView(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, true, true, true, true);
        this.setContentPane(jPanel_testPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /*************************************
     * ACTION LISTENERS
     ************************************/
    public void addjButton_getTimeListener(ActionListener listenForjButton_getTime) {
        jButton_getTime.addActionListener(listenForjButton_getTime);
    }

    /*************************************
     * SET TEXT
     ************************************/
    public void setTextjTextPane_currentTime(String text) {
        jTextPane_currentTime.setText(text);
    }


    /*************************************
     * SET VISIBILITY
     ************************************/
    public void setVisiblejTextPane_currentTime(boolean bool) {
        jTextPane_currentTime.setVisible(bool);
    }

    public void setVisiblejButton_getTime(boolean bool) {
        jButton_getTime.setVisible(bool);
    }

}
