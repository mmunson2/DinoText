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

import java.io.*;

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
            System.err.println("Could not load config file, creating default file.");
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
            System.err.println("Error while saving config.");
        }
    }

    public DinoConfig() { save(); }

    // CamdenController charsPerLine
    private int charsPerLine = 30;
    public void resetCharsPerLine() { charsPerLine = 30; save(); }
    public int getCharsPerLine() { return charsPerLine; }
    public void setCharsPerLine(int i) { charsPerLine = i; save(); }

    // CamdenController linesPerPage
    private int linesPerPage = 3;
    public void resetLinesPerPage() { linesPerPage = 3; save(); }
    public int getLinesPerPage() { return linesPerPage; }
    public void setLinesPerPage(int i) { linesPerPage = i; save(); }

    // File
    private File lastSavedDir = null;
    public File getLastSavedDir() {
        return lastSavedDir;
    }
    public void setLastSavedDir(File directory) { lastSavedDir = directory; save(); }

    // API Settings
    private boolean API_enabled = false;
    private String API_key = "";
    public void setAPI_enabled(boolean b) { API_enabled = b; save(); }
    public boolean isAPI_enabled() { return API_enabled; }
    public void setAPI_key(String s) { API_key = s; save();}
    public String getAPI_key() { return API_key; }


    /* TEMPLATE
    private <type> template = <default_value>;
    public void resetTemplate() { template = <default_value>; save(); }
    public <type> getTemplate() { return template; }
    public void setTemplate(<type> i) { template = i; save(); }
    */
}
