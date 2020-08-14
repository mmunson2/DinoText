// Camden Brewster

package DinoDictionary;

import DinoText_GUI.Util.DinoConfig;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class APIsettings extends JOptionPane
{
    private JCheckBox enable;
    private JPanel panel;
    private JTextField apiKey;

    DinoConfig config;

    public APIsettings()
    {
        config = DinoConfig.loadConfig();
        enable.setSelected(config.isAPI_enabled());
        apiKey.setEditable(config.isAPI_enabled());
        apiKey.setText(config.getAPI_key());

        enable.addActionListener(new enableListener());
        apiKey.addKeyListener(new apiKeyListener());
    }

    public JPanel getPanel() { return panel; }

    private class enableListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            config.setAPI_enabled(enable.isSelected());
            apiKey.setEditable(config.isAPI_enabled());
        }
    }

    private class apiKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e)
        {
            config.setAPI_key(apiKey.getText());
        }
    }
}
