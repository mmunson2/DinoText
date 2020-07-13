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
import java.awt.event.ActionListener;

public class TestPanelView{
    private JButton jButton_getTime;
    private JTextPane jTextPane_currentTime;
    private JPanel jPanel_testPanel;


    /*************************************
     * ACTION LISTENERS
     ************************************/
    public void addjButton_getTimeListener(ActionListener listenForjButton_getTime) {
        jButton_getTime.addActionListener(listenForjButton_getTime);
    }

    /*************************************
     * GETTER
     ************************************/
    public JPanel getjPanel_testPanel() {
        return jPanel_testPanel;
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
