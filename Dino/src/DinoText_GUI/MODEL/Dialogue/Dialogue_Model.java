package DinoText_GUI.MODEL.Dialogue;

import DinoText_GUI.MODEL.DinoWriter;

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

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile() {
        System.out.println(this.dialogue);

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


