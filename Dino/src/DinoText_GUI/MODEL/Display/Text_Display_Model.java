package DinoText_GUI.MODEL.Display;

import DinoParser.Dino;

import java.util.ArrayList;

/*******************************************************************************
 * Text Display Model
 *
 ******************************************************************************/
public class Text_Display_Model
{
    private String text;
    private ArrayList<String> pages;

    private int currentPage;
    private int numPages;

    private int charPerLine;
    private int linesPerPage;

    private Dino dino;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Text_Display_Model()
    {
        this.text = "";
        this.pages = new ArrayList<>();
        this.charPerLine = 30;
        this.linesPerPage = 2;

        this.currentPage = 1;
        this.numPages = 1;
    }

    /***************************************************************************
     * generate New Text
     **************************************************************************/
    public void generateNewText()
    {
        this.text = dino.getDialogue();
    }

    /***************************************************************************
     * get Pages
     **************************************************************************/
    public ArrayList<String> getPages() { return pages; }

    /***************************************************************************
     * get Current Page
     **************************************************************************/
    public int getCurrentPage() { return currentPage; }

    /***************************************************************************
     * get Number of Pages
     **************************************************************************/
    public int getNumPages() { return numPages; }

    /***************************************************************************
     * get Chars Per Line
     **************************************************************************/
    public int getCharsPerLine() { return charPerLine; }

    /***************************************************************************
     * get Lines Per Page
     **************************************************************************/
    public int getLinesPerPage() { return linesPerPage; }

    /***************************************************************************
     * Get Dino
     **************************************************************************/
    public Dino getDino()
    {
        return this.dino;
    }

    /***************************************************************************
     * Set Dino
     **************************************************************************/
    public void setDino(Dino dino)
    {
        this.dino = dino;
    }

    /***************************************************************************
     * Set Text
     **************************************************************************/
    public void setText(String str) { this.text = str; }

    /***************************************************************************
     * Set Current Page
     **************************************************************************/
    public void setCurrentPage(int i) { currentPage = i; }

    /***************************************************************************
     * Set Chars Per Line
     **************************************************************************/
    public void setCharsPerLine(int i) { charPerLine = i; }

    /***************************************************************************
     * Set Lines Per Page
     **************************************************************************/
    public void setLinesPerPage(int i) { linesPerPage = i; }

    /***************************************************************************
     * Format Text
     *
     * converts String text to an ArrayList
     **************************************************************************/
    public void formatText()
    {
        pages = new ArrayList<>();

        char[] chars = text.toCharArray();
        String newPage = "";
        String newWord = "";
        int lineCharCount = 0;
        int wordCharCount = 0;
        int lineNum = 1;

        int i = 0;
        while (i < chars.length)
        {
            if (chars[i] != ' ')
            {
                newWord += chars[i];
                wordCharCount++;
                i++;
            }

            if (i == chars.length || chars[i] == ' ')
            {
                if (i < chars.length)
                {
                    newWord += chars[i];
                    i++;
                }
                if (lineCharCount + wordCharCount + 1 <= charPerLine)
                {
                    newPage += newWord;
                    lineCharCount += wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                else if (lineNum < linesPerPage)
                {
                    newPage += "\n";
                    lineCharCount = 0;
                    lineNum++;
                }
                else
                {
                    pages.add(newPage);
                    newPage = "";
                    lineCharCount = 0;
                    lineNum = 1;
                }
            }
        }
        pages.add(newPage);
        numPages = pages.size();
    }
}
