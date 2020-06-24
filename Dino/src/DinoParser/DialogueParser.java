package DinoParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/*******************************************************************************
 * DialogueParser
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.1
 *
 * Used to extract the contents of a dialogue file and the list files connected
 * to it.
 *
 ******************************************************************************/
class DialogueParser
{
    private Scanner dialogueIn;
    private ListParser[] lists;

    private String dialogue;
    private int[] indices;

    private File parentDirectory;


    /***************************************************************************
     * DialogueParser
     *
     * Initializes instance variables and performs all file operations
     *
     * @param path The dialogue file path
     *
     **************************************************************************/
    DialogueParser(String path)
    {
        File file = (new File(path)).getAbsoluteFile();
        this.parentDirectory = file.getParentFile();

        initializeDialogue(path);
        initializeLists();

        StringBuilder builder = new StringBuilder();

        while(dialogueIn.hasNextLine())
        {
            String nextLine = Parser.getNextLine(dialogueIn);
            builder.append(nextLine);
            builder.append('\n');
        }

        initializeIndices(builder.toString());

        this.dialogue = formatDialogue(builder.toString());
    }

    /***************************************************************************
     * getDialogue
     *
     * Returns modified dialogue String. \L[NAME] has been replaced with \.
     * The indices array must be used to insert list entries into the
     * dialogue.
     *
     **************************************************************************/
    String getDialogue()
    {
        return this.dialogue;
    }

    /***************************************************************************
     * getListArray
     *
     * • First dimension corresponds to the list number. This is determined
     *   by the order of the list names in the dialogue file
     *
     * • Second dimension corresponds to elements inside the list. This is
     *   what should be substituted into the text.
     *
     **************************************************************************/
    String[][] getListArray()
    {
        String[][] retVal = new String[this.lists.length][];

        for(int i = 0; i < retVal.length; i++)
        {
            retVal[i] = this.lists[i].getList();
        }

        return retVal;
    }

    /***************************************************************************
     * getIndices
     *
     * • Ordered list that contains the list number, then the index in the
     *   modified dialogue String where the list element should be inserted.
     *
     *   • Example: for an indices array of size 4:
     *          • indices[0] = list number of first reference
     *          • indices[1] = index of first reference in dialogue String
     *          • indices[2] = list number of second reference
     *          • indices[3] = index of second reference in dialogue String
     *
     **************************************************************************/
    int[] getIndices() { return this.indices; }


    /***************************************************************************
     * initializeIndices
     *
     * • Creates the indices array and fills it with -1. 0 is a valid list
     *   number and index so it needs to be negative.
     *
     **************************************************************************/
    private void initializeIndices(String dialogue)
    {
        Scanner stringScan = new Scanner(dialogue);
        String next;

        int referenceSum = 0;

        //Figure out how many list references exist total
        while(stringScan.hasNext())
        {
            next = stringScan.next();

            if(next.length() > 3
            && next.charAt(0) == '\\'
            && next.charAt(1) == 'L'
            && next.charAt(2) == '[')
            {
                referenceSum++;
            }
        }

        //Initialize array so we have two elements for each reference
        this.indices = new int[referenceSum * 2];

        //Initialize indices to -1
        for(int i = 0; i < indices.length; i++)
        {
            this.indices[i] = -1;
        }

    }


    /***************************************************************************
     * formatDialogue
     *
     * • Removes the \L[NAME] from the dialogue String, leaves a "\" in its
     *   place. This simplifies inserting to an index since you don't need to
     *   know the length of the name anymore.
     *
     * • Records the index of each escape character and inserts it into the
     *   indices array.
     *
     * • We need to keep track of our index in the "new" String, that is, the
     *   String with the L[NAME] part removed. Since this is the String being
     *   sent to the Dino API, we want to record indices relative to this
     *   String.
     *
     * • We also need to keep track of our index in the "old" String. The
     *   primary use of this index is when we want to append the rest of the
     *   "old" String to the "new" one.
     *
     * • Initializing newStringIndex is a bit of an odd quirk about this
     *   method. We normally
     *
     **************************************************************************/
    private String formatDialogue(String dialouge)
    {
        StringBuilder formattedDialogue = new StringBuilder();

        //This string will be cut down as we iterate so that indexOf() works
        String cutDown = dialouge;

        int escapeIndex; //The index of the escape character
        int oldStringIndex = 0; //The index relative to the original String
        int newStringIndex = -1; //The index relative to the new dialogue String

        //Go until there are no more escape characters
        while(cutDown.indexOf('\\') != -1)
        {
            escapeIndex = cutDown.indexOf('\\');

            if(cutDown.charAt(escapeIndex + 1) == 'L'
            && cutDown.charAt(escapeIndex + 2) == '[')
            {
                String name = cutDown.substring(escapeIndex + 3);
                name = parseListName(name);

                String lastString = cutDown.substring(0, escapeIndex + 1);
                formattedDialogue.append(lastString);

                oldStringIndex += lastString.length() + 3 + name.length();
                newStringIndex += lastString.length();

                addIndex(name, newStringIndex);

                cutDown = cutDown.substring(escapeIndex + 4 + name.length());
            }
            //This handles escape characters in the dialogue
            //We don't want to mess with them, so we cut down and move on
            else
            {
                escapeIndex = cutDown.indexOf('\\');

                String lastString = cutDown.substring(0, escapeIndex + 1);
                formattedDialogue.append(lastString);

                oldStringIndex += lastString.length();
                newStringIndex += lastString.length();

                cutDown = cutDown.substring(escapeIndex + 1);
            }
        }

        formattedDialogue.append(dialouge.substring(oldStringIndex));

        return formattedDialogue.toString();
    }


    /***************************************************************************
     * parseListNames
     *
     * • This method assumes that the "\L[" has been chopped off
     *
     * • The remaining String can be any length, but it must have a "]"
     *
     * @param listString String to parse the List name from
     * @return The text before the "]"
     *
     **************************************************************************/
    private String parseListName(String listString)
    {
        if(!listString.contains("]"))
        {
            System.err.println("List name error: Missing \"]\", " +
                    "could not parse!");
            System.exit(-1);
        }

        int closeIndex = listString.indexOf("]");
        listString = listString.substring(0,closeIndex);

        if(getListNameIndex(listString) == -1)
        {
            System.err.println("List Error: " + listString + " is not" +
                    "linked to the dialogue file");
            System.exit(-1);
        }

        return listString;
    }

    /***************************************************************************
     * getListNameIndex
     *
     * Returns the index of a list given its name
     *
     * @param name The name of the list
     * @return The index of the list in the lists Array. -1 if not found.
     *
     **************************************************************************/
    private int getListNameIndex(String name)
    {
        for(int i = 0; i < this.lists.length; i++)
        {
            if(this.lists[i].getName().equals(name))
            {
                return i;
            }
        }

        return -1;
    }

    /***************************************************************************
     * addIndex
     *
     * • The indices array is always even
     * • The list is traversed with increments of two
     * • For any even index i:
     *      • indices[i] = the list number
     *      • indices[i + 1] = the index where the list contents should go
     * • The indices array MUST be in the order that the list references occur!
     *
     **************************************************************************/
    private void addIndex(String listName, int index)
    {
        int listNumber = getListNameIndex(listName);

        for(int i = 0; i < indices.length; i += 2)
        {
            if(this.indices[i] == -1)
            {
                this.indices[i] = listNumber;
                this.indices[i + 1] = index;
                break;
            }
        }
    }

    /***************************************************************************
     * initializeLists
     *
     * • Initializes the Lists Array based on the list names at the top of
     *   the dialogue file
     *
     * • List file names must be their name + ".txt".
     *
     * •
     *
     **************************************************************************/
    private void initializeLists()
    {
        Scanner stringScan;

        int listCount = getListCount();
        this.lists = new ListParser[listCount];

        if(listCount == 0)
        {
            return;
        }

        String listNames = Parser.getNextLine(dialogueIn);

        stringScan = new Scanner(listNames);
        int listIndex = 0;

        while(this.lists.length > 0 && stringScan.hasNext())
        {
            if(listIndex > lists.length)
            {
                System.err.println("DialogueParser Warning: " +
                        "List references exceed size");
                break;
            }

            String nextList = stringScan.next();

            lists[listIndex] = new ListParser(
                    new File(Paths.get(parentDirectory.toString(), nextList).toString() + ".txt"));
            listIndex++;
        }

        if(listIndex != lists.length)
        {
            System.out.println("DialogueParser Warning: " +
                    "Size exceeds list references ");
        }

    }

    /***************************************************************************
     * getListCount
     *
     **************************************************************************/
    private int getListCount()
    {
        Scanner stringScan;

        String listCountLine = Parser.getNextLine(dialogueIn);
        stringScan = new Scanner(listCountLine);

        stringScan.next();
        stringScan.next();

        String listCountString = stringScan.next();

        return Integer.parseInt(listCountString);
    }


    /***************************************************************************
     * initializeDialogue
     *
     **************************************************************************/
    private void initializeDialogue(String path)
    {
        try
        {
            this.dialogueIn = new Scanner(new FileInputStream(path));
        }
        catch(IOException e)
        {
            System.err.println("DialogueParser could not locate file: " + path);
            System.exit(-1);
        }
    }
}
