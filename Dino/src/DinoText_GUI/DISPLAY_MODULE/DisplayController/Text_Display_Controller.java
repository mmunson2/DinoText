// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayController;

import DinoText_GUI.DIALOGUE_MODULE.DialogueController.Dialogue_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Text_Display_Settings;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Variable_Setting_View;
import DinoText_GUI.Util.DinoConfig;
import Dino.Dino;
import DinoText_GUI.DISPLAY_MODULE.DisplayModel.Text_Display_Model;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Text_Display_View;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Trait_Setting_View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*******************************************************************************
 * Text_Display_Controller
 *
 ******************************************************************************/
public class Text_Display_Controller
{
    private Text_Display_Model model;
    private Text_Display_View view;
    private DinoConfig config;
    private Trait_Setting_View traitSettings = null;
    private Variable_Setting_View variableSettings = null;
    private Text_Display_Settings settings = null;

    private Dialogue_Controller dialogueController;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Text_Display_Controller(Text_Display_Model model, Text_Display_View view, DinoConfig config)
    {
        this.model = model;
        this.view = view;
        this.config = config;
        this.settings = new Text_Display_Settings();

        // loading from config
        this.model.setCharsPerLine(this.config.getCharsPerLine());
        this.model.setLinesPerPage(this.config.getLinesPerPage());

        // setting page button listeners
        this.view.nextButtonListener(new nextButtonListener());
        this.view.prevButtonListener(new prevButtonListener());

        // setting up character spinner
        this.settings.setCharactersSpinner(this.model.getCharsPerLine());
        this.settings.charactersSpinnerListener(new charactersSpinnerListener());

        // setting up lines spinner
        this.settings.setLinesSpinner(this.model.getLinesPerPage());
        this.settings.linesSpinnerListener(new linesSpinnerListener());

        // setting up settings listeners
        this.view.traitSettingsListener(new traitSettingsListener());
        this.view.variableSettingsListener(new variableSettingsListener());
        this.view.settingsButtonListener(new SettingsButtonListener());

        this.view.add_generateNew_button_listener(new generateNewListener());
    }

    public void close()
    {
        if (settings != null)
        {
            settings.setVisible(false);
            settings = null;
        }
        if (traitSettings != null)
        {
            traitSettings.setVisible(false);
            traitSettings = null;
        }
        if (variableSettings != null)
        {
            variableSettings.setVisible(false);
            variableSettings = null;
        }
    }

    /***************************************************************************
     * setDialogue
     **************************************************************************/
    public void setDialogue(String str)
    {
        model.setText(str);
        format();
        updateDisplay();
    }

    /***************************************************************************
     * setDialogueController
     **************************************************************************/
    public void setDialogueController(Dialogue_Controller controller)
    {
        this.dialogueController = controller;
    }

    /***************************************************************************
     * setDino
     **************************************************************************/
    public void setDino(Dino dino)
    {
        this.model.setDino(dino);
        resetTraitSettings();
        resetVariableSettings();
        this.generateNewText();
    }

    /***************************************************************************
     * Generate New Text
     **************************************************************************/
    public void generateNewText()
    {
        //dialogueController.saveExistingDialogueFile();
        //this.model.setDino(dialogueController.getDino());
        this.model.generateNewText();
        this.format();
        this.updateDisplay();
    }

    /***************************************************************************
     * format
     **************************************************************************/
    public void format()
    {
        model.formatText();
    }

    /***************************************************************************
     * update display
     **************************************************************************/
    public void updateDisplay()
    {
        ArrayList<String> pages = model.getPages();
        view.setTextPane(pages.get(model.getCurrentPage() - 1));
        setPage(model.getCurrentPage());
    }

    /***************************************************************************
     * update config
     **************************************************************************/
    public void updateConfig()
    {
        model.setCharsPerLine(config.getCharsPerLine());
        model.setLinesPerPage(config.getLinesPerPage());
        format();
        setPage(1);
        updateDisplay();
    }

    /***************************************************************************
     * setPage
     **************************************************************************/
    private void setPage(int i)
    {
        model.setCurrentPage(i);
        view.setPageCounter(i + " / " + model.getNumPages());
    }

    // closes and deletes the trait settings window
    public void resetTraitSettings()
    {
        if (traitSettings != null)
        {
            boolean visible = traitSettings.isVisible();
            int x = traitSettings.getX();
            int y = traitSettings.getY();
            traitSettings.setVisible(false);
            traitSettings = new Trait_Setting_View(model.getDino());
            traitSettings.setLocation(x, y);
            traitSettings.setVisible(visible);
        }
    }

    // closes and deletes the variable settings window
    public void resetVariableSettings()
    {
        if (variableSettings != null)
        {
            boolean visible = variableSettings.isVisible();
            int x = variableSettings.getX();
            int y = variableSettings.getY();
            variableSettings.setVisible(false);
            variableSettings = new Variable_Setting_View(model.getDino());
            variableSettings.setLocation(x, y);
            variableSettings.setVisible(visible);
        }
    }

    /***************************************************************************
     * Next Button
     **************************************************************************/
    class nextButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int currentPage = model.getCurrentPage();
            int numPages = model.getNumPages();

            if (currentPage < numPages)
            {
                setPage(currentPage + 1);
            }

            updateDisplay();
        }
    }

    /***************************************************************************
     * Previous Button
     **************************************************************************/
    class prevButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int currentPage = model.getCurrentPage();

            if (currentPage > 1)
            {
                setPage(currentPage - 1);
            }

            updateDisplay();
        }
    }

    /***************************************************************************
     * Character Count Spinner
     **************************************************************************/
    class charactersSpinnerListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int chars = settings.getCharsPerLine();

            if (chars < 10)
            {
                settings.setCharactersSpinner(10);
                chars = 10;
            }

            config.setCharsPerLine(chars);

            updateConfig();
        }
    }

    /***************************************************************************
     * Line Count Spinner
     **************************************************************************/
    class linesSpinnerListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int lines = settings.getLinesPerPage();

            if (lines < 1)
            {
                settings.setLinesSpinner(1);
                lines = 1;
            }

            config.setLinesPerPage(lines);

            updateConfig();
        }
    }

    /***************************************************************************
     * Settings Button
     **************************************************************************/
    class SettingsButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!settings.isVisible())
            {
                settings.setVisible(true);
            }
            else
            {
                settings.setVisible(false);
            }
        }
    }

    /***************************************************************************
     * Trait Settings Button
     **************************************************************************/
    class traitSettingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (traitSettings == null)
            {
                traitSettings = new Trait_Setting_View(model.getDino());
            }
            if (!traitSettings.isVisible())
            {
                traitSettings.setVisible(true);
            }
        }
    }

    /***************************************************************************
     * Variable Settings Button
     **************************************************************************/
    class variableSettingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (variableSettings == null)
            {
                variableSettings = new Variable_Setting_View(model.getDino());
            }
            if (!variableSettings.isVisible())
            {
                variableSettings.setVisible(true);
            }
        }
    }

    /***************************************************************************
     * Generate New Button
     **************************************************************************/
    class generateNewListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            generateNewText();
        }
    }

    /***************************************************************************
     * setPanelVisible
     *
     **************************************************************************/
    public void setPanelVisible(boolean bool) { view.setPanelVisible(bool); }

    /***************************************************************************
     * panelIsVisible
     *
     **************************************************************************/
    public boolean panelIsVisible() {
        return view.isVisible();
    }
}
