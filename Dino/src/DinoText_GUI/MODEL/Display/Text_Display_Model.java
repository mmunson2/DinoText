package DinoText_GUI.MODEL.Display;

import java.util.ArrayList;

public class Text_Display_Model
{
    private String text;

    private ArrayList<String> pages;

    private int currentPage;
    private int numPages;

    private int charPerLine;
    private int linesPerPage;

    public Text_Display_Model()
    {
        this.text = "";
        this.pages = new ArrayList<>();
        this.charPerLine = 30;
        this.linesPerPage = 2;

        this.currentPage = 1;
        this.numPages = 1;
    }

    // getters
    public ArrayList<String> getPages() { return pages; }

    public int getCurrentPage() { return currentPage; }

    public int getNumPages() { return numPages; }

    public int getCharsPerLine() { return charPerLine; }

    public int getLinesPerPage() { return linesPerPage; }

    // setters
    public void setText(String str) { this.text = str; }

    public void setCurrentPage(int i) { currentPage = i; }

    public void setCharsPerLine(int i) { charPerLine = i; }

    public void setLinesPerPage(int i) { linesPerPage = i; }

    // converts String text to an ArrayList
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
