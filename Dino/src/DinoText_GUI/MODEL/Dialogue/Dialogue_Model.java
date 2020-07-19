package DinoText_GUI.MODEL.Dialogue;

import DinoText_GUI.MODEL.DinoList;
import DinoText_GUI.MODEL.DinoWriter;

import java.util.ArrayList;
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

    private static HashSet<String> listNames;

    private static Set<DinoList> dupCheckSet = new LinkedHashSet<>();
    private static ArrayList<DinoList> lists = new ArrayList<>();

    /***************************************************************************
     * newDialogue
     *
     **************************************************************************/
    public void newDialogue(String userDialogue) {
        StringBuilder builder = new StringBuilder();

        if (userDialogue.contains("\\")) {
            recursiveEscapeHandler(userDialogue);
        }

        dialogue = userDialogue;
    }

    /***************************************************************************
     * setName
     *
     **************************************************************************/
    public void setName(String name) { this.name = name; }

    /***************************************************************************
     * setListNames
     *
     **************************************************************************/
    public void setListNames(HashSet<String> set) { listNames = set; }

    /***************************************************************************
     * nameDialogue
     *
     **************************************************************************/
    public void nameDialogue(String dialogueName) {
        name = dialogueName;

        if (name.length() < 4
                || !name.substring(name.length() - 4).equals(".txt")) {
            name += ".txt";
        }
    }

    /***************************************************************************
     * writeToFile
     *
     **************************************************************************/
    public void writeToFile() {
        System.out.println("Test: ");
        System.out.println(this.dialogue);

        DinoWriter writer = new DinoWriter();

        writer.writeDialogueToFile(name, dialogue, new LinkedHashSet<>(lists), new LinkedHashSet<String>());
    }

    /***************************************************************************
     * DinoGUI + DinoText Methods
     **************************************************************************/

    /***************************************************************************
     * recursiveEscapeHandler
     *
     * Searches a line for escape characters. If a "\L[" is found in the String,
     * parseListName is called to attempt to retrieve the name of the list and
     * add it to the Set.
     *
     * Recursion is used because of the possibility of multiple escape
     * characters in a single line of text. The algorithm traverses the
     * String from left to right. When an escape character has been handled,
     * the String is trimmed down to the characters right of the current
     * character. The base case is when indexOf("\\") returns -1, indicating
     * there are no additional escape characters in the String.
     *
     * @param nextLine A string that may contain an escape character
     *
     **************************************************************************/
    private static void recursiveEscapeHandler(String nextLine) {
        int escapeIndex = nextLine.indexOf('\\');

        //Base case
        if (escapeIndex == -1) {
            return;
        }

        //Look for open bracket, wary of ArrayIndexOutOfBoundsException
        if (nextLine.length() > (escapeIndex + 2)
                && nextLine.charAt(escapeIndex + 1) == 'L'
                && nextLine.charAt(escapeIndex + 2) == '[') {
            String listName = nextLine.substring(escapeIndex + 3);
            parseListName(listName);
        }

        String remainder = nextLine.substring(escapeIndex + 1);

        recursiveEscapeHandler(remainder);
    }

    /***************************************************************************
     * parseListName
     *
     * Attempts to extract the name of the list. Fails if no closing bracket
     * can be found.
     *
     * The name is added to the LinkedHashSet. Duplicates are automatically
     * rejected in the data structure so they need not be checked for here.
     *
     * @param listString A string representing the text after an open bracket,
     *                   with the assumption that a closed bracket exists
     *                   somewhere.
     *
     **************************************************************************/
    private static void parseListName(String listString) {
        //Todo: (Maybe) improve error handling
        if (!listString.contains("]")) {
            System.out.println("List name error: Missing \"]\", " +
                    "could not parse!");
            System.exit(-1);
        }

        int closeIndex = listString.indexOf("]");
        listString = listString.substring(0, closeIndex);

        if (dupCheckSet.add(new DinoList(listString))) {
            lists.add(new DinoList(listString));
        }
    }
}


