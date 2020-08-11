package DinoText_GUI.DIALOGUE_MODULE.DialogueModel;

import Dino.FileTypes;
import DinoText_GUI.Util.DinoWriter;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/*******************************************************************************
 * Dialogue Model
 *
 ******************************************************************************/
public class Dialogue_Model {

    private String name;
    private String dialogue;

    private static Set<String> staticVars = new LinkedHashSet<>();
    private static Set<File> listPaths = new LinkedHashSet<>();

    /***************************************************************************
     * addStaticVar
     *
     **************************************************************************/
    public void addStaticVar(String name) {
        staticVars.add(name);
    }

    /***************************************************************************
     * getStaticVars
     *
     **************************************************************************/
    public Set<String> getStaticVars(){
        return staticVars;
    }

    /***************************************************************************
     * setStaticVars
     *
     **************************************************************************/
    public void setStaticVars(Set<String> newVars){
        staticVars = newVars;
    }

    /***************************************************************************
     * setName
     *
     **************************************************************************/
    public void setName(String name) { this.name = name; }

    /***************************************************************************
     * getName
     *
     **************************************************************************/
    public String getName() { return name; }

    /***************************************************************************
     * setListNames
     *
     **************************************************************************/
    public void setListNames(HashSet<File> set) { listPaths = set; }

    public void setListNames(Set<File> set) { listPaths = set; }

    public void setListFiles(HashSet<File> set) { listPaths = set; }


    /***************************************************************************
     * getListNames
     *
     **************************************************************************/
    public Set<String> getListNames()
    {
        Set<String> listNames = new LinkedHashSet<>();

        for(File path : listPaths)
        {
            String listName = path.getName();

            if(FileTypes.hasListExtension(listName))
            {
                listName = FileTypes.trimListExtension(listName);
            }

            listNames.add(listName);
        }


        return listNames;
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile() {
        DinoWriter writer = new DinoWriter();
        String[] listNames = new String[listPaths.size()];
        int i = 0;
        for (File file : listPaths) {
            listNames[i] = file.getAbsolutePath();
            i++;
        }
        //Todo: Switch this over to the DinoList call
        writer.writeDialogueToFile(name, dialogue, listNames, staticVars.toArray(new String[staticVars.size()]));
    }

    /***************************************************************************
     * setDialogue
     *
     **************************************************************************/
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
    /***************************************************************************
     * getDialogue
     *
     **************************************************************************/
    public String getDialogue() {
        return dialogue;
    }

    public void addListFile(File file) {
        listPaths.add(file);
    }
}
