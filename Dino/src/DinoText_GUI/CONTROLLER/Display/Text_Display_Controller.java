// Camden Brewster

package DinoText_GUI.CONTROLLER.Display;

import DinoText_GUI.DinoConfig;
import DinoParser.Dino;
import DinoText_GUI.MODEL.Display.Text_Display_Model;
import DinoText_GUI.VIEW.Display.Text_Display_View;

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
        update();
    }

    /***************************************************************************
     * setDino
     *
     **************************************************************************/
    public void setDino(Dino dino)
    {
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
        this.update();
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
     * update
     *
     **************************************************************************/
    public void update()
    {
        ArrayList<String> pages = model.getPages();
        view.setTextPane(pages.get(model.getCurrentPage() - 1));
        setPage(model.getCurrentPage());
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

            update();
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

            update();
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
            model.setCharsPerLine(chars);

            config.setCharsPerLine(chars);

            setPage(1);
            format();
            update();
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
            model.setLinesPerPage(lines);

            config.setLinesPerPage(lines);

            setPage(1);
            format();
            update();
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
