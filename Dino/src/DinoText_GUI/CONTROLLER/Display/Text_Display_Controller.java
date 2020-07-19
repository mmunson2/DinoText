package DinoText_GUI.CONTROLLER.Display;

import DinoParser.Dino;
import DinoText_GUI.MODEL.Display.Text_Display_Model;
import DinoText_GUI.VIEW.Display.Text_Display_View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Text_Display_Controller
{
    private Text_Display_Model model;
    private Text_Display_View view;

    private Dino dino;

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
    }

    public void setDialogue(String str)
    {
        model.setText(str);
        model.formatText();
        update();
    }

    public void setDino(Dino dino)
    {
        this.dino = dino;
    }

    public void update()
    {
        model.formatText();
        ArrayList<String> pages = model.getPages();
        view.setTextPane(pages.get(model.getCurrentPage() - 1));
        setPage(model.getCurrentPage());
    }

    private void setPage(int i)
    {
        model.setCurrentPage(i);
        view.setPageCounter(i + " / " + model.getNumPages());
    }

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

    //ihsan added
    public void setPanelVisible(boolean bool) { view.setPanelVisible(bool); }

    public boolean panelIsVisible() {
        return view.isVisible();
    }
}
