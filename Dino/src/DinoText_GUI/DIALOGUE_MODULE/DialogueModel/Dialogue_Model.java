package DinoText_GUI.DIALOGUE_MODULE.DialogueModel;

import Dino.FileTypes;
import DinoText_GUI.Util.DinoWriter;

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
    private static Set<String> listNames = new LinkedHashSet<>();

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
    public void setListNames(HashSet<String> set) { listNames = set; }

    public void setListNames(Set<String> set) { listNames = set; }


    /***************************************************************************
     * getListNames
     *
     **************************************************************************/
    public Set<String> getListNames() { return listNames; }

    /***************************************************************************
     * Add List Names
     *
     **************************************************************************/
    public void addListName(String name) {
        listNames.add(name);
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile() {
        DinoWriter writer = new DinoWriter();

        writer.writeDialogueToFile(name, dialogue, listNames.toArray(new String[listNames.size()]), staticVars.toArray(new String[staticVars.size()]));
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
}


