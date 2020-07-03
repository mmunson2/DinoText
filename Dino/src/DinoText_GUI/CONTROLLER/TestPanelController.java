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

package DinoText_GUI.CONTROLLER;

import DinoText_GUI.MODEL.TestPanelModel;
import DinoText_GUI.VIEW.TestPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanelController {

    TestPanelModel testPanelModel;
    TestPanelView testPanelView;

    public TestPanelController(TestPanelModel tpm, TestPanelView tpv) {
        testPanelModel = tpm;
        testPanelView = tpv;
        newTestPanel();
    }

    /*************************************
     * ACTION LISTENERS
     ************************************/
    class listener_testButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            testPanelView.setTextjTextPane_currentTime( "" + System.currentTimeMillis());
        }
    }

    private void newTestPanel() {
        testPanelView.setVisiblejTextPane_currentTime(true);
        testPanelView.setVisiblejButton_getTime(true);
        testPanelView.addjButton_getTimeListener(new listener_testButton());
    }
}
