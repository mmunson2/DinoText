/*
 * DinoConfig.java
 * Camden Brewster
 *
 * To add a new config, copy the template at the bottom and change the names of each
 * variable and method. When changing this file, an error will show when running the
 * program because the old config can no longer be read, this is fine. The old config
 * file will be overwritten with the new one.
 *
 */

package DinoText_GUI.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DinoConfig implements Serializable
{
    public static DinoConfig loadConfig()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("dino.cfg");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            DinoConfig config = (DinoConfig) objectIn.readObject();
            objectIn.close();
            return config;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new DinoConfig();
    }

    private void save()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("dino.cfg");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public DinoConfig() { save(); }

    // CamdenController charsPerLine
    private int charsPerLine_default = 30;
    public int getCharsPerLine_default() { return charsPerLine_default; }
    public void setCharsPerLine_default(int i) { charsPerLine_default = i; save(); }
    private int charsPerLine = 30;
    public int getCharsPerLine() { return charsPerLine; }
    public void setCharsPerLine(int i) { charsPerLine = i; save(); }

    // CamdenController linesPerPage
    private int linesPerPage_default = 3;
    public int getLinesPerPage_default() { return linesPerPage_default; }
    public void setLinesPerPage_default(int i) { linesPerPage_default = i; save(); }
    private int linesPerPage = 3;
    public int getLinesPerPage() { return linesPerPage; }
    public void setLinesPerPage(int i) { linesPerPage = i; save(); }

    /* TEMPLATE
    private int template_default = 3;
    public int getTemplate_default() { return template_default; }
    public void setTemplate_default(int i) { template_default = i; save(); }
    private int template = 3;
    public int getTemplate() { return template; }
    public void setTemplate(int i) { template = i; save(); }
    */
}
