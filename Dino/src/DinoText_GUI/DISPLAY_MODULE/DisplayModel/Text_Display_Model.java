// Camden Brewster

package DinoText_GUI.DISPLAY_MODULE.DisplayModel;

import Dino.Dino;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Trait_Setting_View;
import DinoText_GUI.DISPLAY_MODULE.DisplayView.Variable_Setting_View;

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

    private int charsPerLine;
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
        this.charsPerLine = 30;
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
    public int getCharsPerLine() { return charsPerLine; }

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
    public void setCharsPerLine(int i) { charsPerLine = i; }

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
            //System.out.println("|" + chars[i] + "|");
            //System.out.println("newWord: |" + newWord + "|" + " wordCharCount: " + wordCharCount + " lineCharCount: " + lineCharCount + " lineNum: " + lineNum);
            //System.out.println("newPage: \n|" + newPage + "|\n");

            // if current char is not a space, add to new word
            if (chars[i] != ' ')
            {
                newWord += chars[i];
                wordCharCount++;
            }
            else
            {
                // if the first word is longer than charsPerLine
                if (lineCharCount == 0 && wordCharCount > charsPerLine && lineNum < linesPerPage)
                {
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                    i++;
                    continue;
                }

                // adds word to current line if it fits
                if (lineCharCount + wordCharCount <= charsPerLine)
                {
                    newPage += newWord;
                    lineCharCount += wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // goes to new line then adds the new word if lineNum is less than linesPerPage
                else if (lineNum < linesPerPage)
                {
                    newPage += "\n";
                    lineNum++;
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // goes to a new page then adds the new word
                else
                {
                    pages.add(newPage);
                    lineNum = 1;
                    newPage = newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                // adds the space only if there is room on the current line
                if (lineCharCount < charsPerLine)
                {
                    newPage += chars[i];
                    lineCharCount++;
                }
            }
            i++;
            // checking to add the last word in the String
            if (i < chars.length)
            {
                continue;
            }
            // adding the last word using the above logic
            else
            {
                if (lineCharCount + wordCharCount <= charsPerLine)
                {
                    newPage += newWord;
                    lineCharCount += wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                else if (lineNum < linesPerPage)
                {
                    newPage += "\n";
                    lineNum++;
                    newPage += newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
                else
                {
                    pages.add(newPage);
                    lineNum = 1;
                    newPage = newWord;
                    lineCharCount = wordCharCount;
                    newWord = "";
                    wordCharCount = 0;
                }
            }
        }
        pages.add(newPage);
        numPages = pages.size();
    }
}
