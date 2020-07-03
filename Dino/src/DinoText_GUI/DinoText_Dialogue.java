/*******************************************************************************************
 *   ████████▄   ▄█     ▄████████  ▄█        ▄██████▄     ▄██████▄  ███    █▄     ▄████████
 * ███   ▀███ ███    ███    ███ ███       ███    ███   ███    ███ ███    ███   ███    ███
 * ███    ███ ███▌   ███    ███ ███       ███    ███   ███    █▀  ███    ███   ███    █▀
 * ███    ███ ███▌   ███    ███ ███       ███    ███  ▄███        ███    ███  ▄███▄▄▄
 * ███    ███ ███▌ ▀███████████ ███       ███    ███ ▀▀███ ████▄  ███    ███ ▀▀███▀▀▀
 * ███    ███ ███    ███    ███ ███       ███    ███   ███    ███ ███    ███   ███    █▄
 * ███   ▄███ ███    ███    ███ ███▌    ▄ ███    ███   ███    ███ ███    ███   ███    ███
 * ████████▀  █▀     ███    █▀  █████▄▄██  ▀██████▀    ████████▀  ████████▀    ██████████
 *****************************************************************************************/
package DinoText_GUI;

import DinoText_GUI.CONTROLLER.DinoText_Dialogue_Controller;
import DinoText_GUI.CONTROLLER.TestPanelController;
import DinoText_GUI.MODEL.DinoText_Dialogue_Model;
import DinoText_GUI.MODEL.TestPanelModel;
import DinoText_GUI.VIEW.DinoText_Dialogue_View;
import DinoText_GUI.VIEW.TestPanelView;

import javax.swing.*;

public class DinoText_Dialogue {

    public static void main(String args[]) {
        JFrame jFrame_dinoText = new JFrame();
        JDesktopPane jDesktopPane_dinoText = new JDesktopPane();

        DinoText_Dialogue_Model dinoGUIModel = new DinoText_Dialogue_Model();
        DinoText_Dialogue_View dinoGUIView = new DinoText_Dialogue_View("Dialogue Editor", true, true, true, true);
        DinoText_Dialogue_Controller dinoGUIController = new DinoText_Dialogue_Controller(dinoGUIModel, dinoGUIView);

        TestPanelModel testPanelModel = new TestPanelModel();
        TestPanelView testPanelView = new TestPanelView("Test Panel", true, true, true, true);
        TestPanelController testPanelController = new TestPanelController(testPanelModel, testPanelView);

        // Internal Frame
        testPanelView.setBounds(1,1,200,250);
        dinoGUIView.setBounds(1,1,200,250);

        // Desktop Panel
        jDesktopPane_dinoText.add(dinoGUIView);
        jDesktopPane_dinoText.add(testPanelView);
        for (JInternalFrame j : jDesktopPane_dinoText.getAllFrames()) {
            j.pack();
            j.setVisible(true);
        }
        jDesktopPane_dinoText.setVisible(true);

        //Frame
        jFrame_dinoText.add(jDesktopPane_dinoText);
        jFrame_dinoText.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame_dinoText.setSize(600, 580);
        jFrame_dinoText.setVisible(true);
    }
}
