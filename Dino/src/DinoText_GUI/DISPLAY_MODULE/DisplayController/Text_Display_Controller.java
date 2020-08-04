// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayController;

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
    private Trait_Setting_View window = null;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Text_Display_Controller(Text_Display_Model model, Text_Display_View view, DinoConfig config)
    {
        this.model = model;
        this.view = view;
        this.config = config;

        // loading from config
        this.model.setCharsPerLine(this.config.getCharsPerLine());
        this.model.setLinesPerPage(this.config.getLinesPerPage());

        // setting page button listeners
        this.view.nextButtonListener(new nextButtonListener());
        this.view.prevButtonListener(new prevButtonListener());

        // setting up character spinner
        this.view.setCharactersSpinner(this.model.getCharsPerLine());
        this.view.charactersSpinnerListener(new charactersSpinnerListener());

        // setting up lines spinner
        this.view.setLinesSpinner(this.model.getLinesPerPage());
        this.view.linesSpinnerListener(new linesSpinnerListener());

        // setting up settings listeners
        this.view.traitSettingsListener(new traitSettingsListener());
        this.view.variableSettingsListener(new variableSettingsListener());

        this.view.add_generateNew_button_listener(new generateNewListener());
    }

    /***************************************************************************
     * setDialogue
     *
     **************************************************************************/
    public void setDialogue(String str)
    {
        model.setText(str);
        format();
        updateDisplay();
    }

    /***************************************************************************
     * setDino
     *
     **************************************************************************/
    public void setDino(Dino dino)
    {
        model.resetTraitSettings();
        model.resetVariableSettings();
        this.model.setDino(dino);
        this.generateNewText();
    }

    /***************************************************************************
     * Generate New Text
     *
     **************************************************************************/
    public void generateNewText()
    {
        this.model.generateNewText();
        this.format();
        this.updateDisplay();
    }

    /***************************************************************************
     * format
     *
     **************************************************************************/
    public void format()
    {
        model.formatText();
    }

    /***************************************************************************
     * update display
     *
     **************************************************************************/
    public void updateDisplay()
    {
        ArrayList<String> pages = model.getPages();
        view.setTextPane(pages.get(model.getCurrentPage() - 1));
        setPage(model.getCurrentPage());
    }

    /***************************************************************************
     * update config
     *
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
     *
     **************************************************************************/
    private void setPage(int i)
    {
        model.setCurrentPage(i);
        view.setPageCounter(i + " / " + model.getNumPages());
    }

    /***************************************************************************
     * Next Button
     *
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
     *
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
     *
     **************************************************************************/
    class charactersSpinnerListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int chars = view.getCharsPerLine();

            config.setCharsPerLine(chars);

            updateConfig();
        }
    }

    /***************************************************************************
     * Line Count Spinner
     *
     **************************************************************************/
    class linesSpinnerListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            int lines = view.getLinesPerPage();

            config.setLinesPerPage(lines);

            updateConfig();
        }
    }

    /***************************************************************************
     * Trait Settings Button
     *
     **************************************************************************/
    class traitSettingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            model.openTraitSettings();
        }
    }

    /***************************************************************************
     * Variable Settings Button
     *
     **************************************************************************/
    class variableSettingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            model.openVariableSettings();
        }
    }

    /***************************************************************************
     * Generate New Button
     *
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
