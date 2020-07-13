package Camden;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CamdenController
{
    private CamdenModel model;
    private CamdenView view;

    public CamdenController(CamdenModel model, CamdenView view)
    {
        this.model = model;
        this.view = view;

        this.view.nextButtonListener(new nextButtonListener());
        this.view.prevButtonListener(new prevButtonListener());
        this.view.setPageCounter(this.model.getCurrentPage());

        this.view.setCharactersSpinner(this.model.getCharsPerLine());
        this.view.charactersSpinnerListener(new charactersSpinnerListener());

        this.view.setLinesSpinner(this.model.getLinesPerPage());
        this.view.linesSpinnerListener(new linesSpinnerListener());
    }

    public void setDialogue(String str)
    {
        model.setText(str);
        model.formatText();
    }

    public void update()
    {
        model.formatText();
        ArrayList<String> pages = model.getPages();
        view.setTextPane(pages.get(model.getCurrentPage() - 1));
    }

    private void setPage(int i)
    {
        model.setCurrentPage(i);
        view.setPageCounter(i);
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
}
