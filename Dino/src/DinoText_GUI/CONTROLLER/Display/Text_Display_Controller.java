package DinoText_GUI.CONTROLLER.Display;

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

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Text_Display_Controller(Text_Display_Model model, Text_Display_View view)
    {
        this.model = model;
        this.view = view;

        this.view.nextButtonListener(new nextButtonListener());
        this.view.prevButtonListener(new prevButtonListener());

        this.view.setCharactersSpinner(this.model.getCharsPerLine());
        this.view.charactersSpinnerListener(new charactersSpinnerListener());

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
        model.formatText();
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
        this.update();
    }


    /***************************************************************************
     * update
     *
     **************************************************************************/
    public void update()
    {
        model.formatText();
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

            setPage(1);
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


            setPage(1);
            update();
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
