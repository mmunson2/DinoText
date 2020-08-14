package Dino;

import Dino.Delimiter.Delimiter;
import Dino.Delimiter.Reference;
import Dino.List.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;


/*******************************************************************************
 * DialogueParser
 *
 * This class is used to extract information from dialogue files. Its data is
 * then retrieved and stored by a Dino Object.
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.25-alpha
 ******************************************************************************/
public class DialogueParser
{
    private Scanner dialogueIn;
    private List[] lists;
    private Set<String> traitNames;

    private final String dialogue;
    private final String unformattedDialogue;

    private String[] staticVars;

    private int[][] indices;

    private final File parentDirectory;


    /***************************************************************************
     * DialogueParser Constructor
     *
     * Initializes instance variables and performs all file operations
     *
     * Construction of the DialogueParser Object is likely the most expensive
     * operation in the entire Dino sequence because of the file access. If
     * a large amount of DialogueParser Objects need to be created, they
     * should be initialized during a loading sequence.
     *
     * @param path The dialogue file path
     * @since 0.5-beta
     **************************************************************************/
    public DialogueParser(String path)
    {
        File file = (new File(path)).getAbsoluteFile();
        this.parentDirectory = file.getParentFile();

        initializeDialogue(file.getAbsolutePath());
        initializeLists();
        initializeStaticVars();


        StringBuilder builder = new StringBuilder();

        while(dialogueIn.hasNextLine())
        {
            String nextLine = Parser.getNextLine(dialogueIn);

            if(nextLine == null)
                break;

            builder.append(nextLine);
            builder.append('\n');
        }

        initializeIndices(builder.toString());

        this.unformattedDialogue = builder.toString();

        System.err.println("DialogueParser found unformatted d: " + unformattedDialogue);

        this.dialogue = formatDialogue(builder.toString());
    }

    /***************************************************************************
     * getDialogue
     *
     * To simplify the String appending process in Dino, all References
     * (eg. \L[NAME], \S[player]) have been replaced with a single escape
     * character. The indices array is responsible for tracking the location
     * of each Reference.
     *
     * @return The modified dialogue String
     *
     * @since 0.25-alpha
     **************************************************************************/
    public String getDialogue()
    {
        return this.dialogue;
    }

    public String getUnformattedDialogue()
    {
        return this.unformattedDialogue;
    }

    /***************************************************************************
     * getListArray
     *
     * Returns an Array of List Objects representing all of the Lists referenced
     * in the dialogue file.
     *
     * Note: returned Array is not a deep copy!
     *
     * @return the List Array
     *
     * @since 0.25-alpha
     **************************************************************************/
    public List[] getListArray()
    {
        return this.lists;
    }

    /***************************************************************************
     * getIndices
     *
     * The indices array contains the reference type, list or variable number,
     * and position within the dialogue file. The array is organized as follows:
     * ____________________________________
     * |        |   0    |    1    |      |
     * | index  |  LIST  |  STATIC | .... |
     * |        |        |         |      |
     * ------------------------------------
     * |        | LIST   | STATIC  |      |
     * |   0    | index1 | index1  | ...  |
     * |        | List#  | Static# |      |
     * ------------------------------------
     * |        | LIST   | STATIC  |      |
     * |   1    | index1 | index1  | ...  |
     * |        | StrPos | StrPos  |      |
     * ------------------------------------
     * |        | LIST   | STATIC  |      |
     * |   2    | index2 | index2  | ...  |
     * |        | List#  | Static# |      |
     * ------------------------------------
     * |        |        |         |      |
     * |   3    |  ...   |   ...   | ...  |
     * |        |        |         |      |
     * ------------------------------------
     *
     * The first dimension of the array corresponds to the Reference ordinal
     * (e.g. LIST, STATIC). Each entry is provided two spaces within the indices
     * array. The first corresponds to the list or variable number. The second
     * is the location of the entry in the dialogue file.
     *
     * Note: Array is not a deep copy!
     *
     * @return the indices array
     * @since 0.25-alpha
     **************************************************************************/
    public int[][] getIndices() { return this.indices; }


    /***************************************************************************
     * getStaticVars
     *
     * The staticVars array holds the name of each static variable, as well
     * as the String attached to it. For instance, a static variable might be
     * named "player", and at runtime could be assigned "Dino."
     *
     * The staticVars Array is organized as follows:
     * ____________________________
     * |        |   0    |    1   |
     * | index  |Variable|Variable|
     * |        |  Name  |  Value |
     * ----------------------------
     * |        |Variable|Variable|
     * |   0    |   1    |   1    |
     * |        |  Name  |  Value |
     * ----------------------------
     * |        |Variable|Variable|
     * |   1    |   2    |   2    |
     * |        |  Name  |  Value |
     * ----------------------------
     * |        |        |        |
     * |   ...  |   ...  |   ...  |
     * |        |        |        |
     * ----------------------------
     *
     * Note that each value is initialized to the name. This is intended to
     * assist the developer in identifying any static variables that are not
     * initialized.
     *
     * @return the staticVars array
     * @since 0.25-alpha
     **************************************************************************/
    public String[][] getStaticVars()
    {
        String[][] retVal = new String[staticVars.length][2];

        for(int i = 0; i < retVal.length; i++)
        {
            retVal[i][0] = this.staticVars[i];
            retVal[i][1] = this.staticVars[i]; //Initialize value to name
        }

        return retVal;
    }

    /***************************************************************************
     * getTraitNames
     *
     * The traitNames array holds the names of all traits in all lists contained
     * in the Dialogue Object. This entire array can be passed into each
     * List Object when updating values.
     *
     * @return The traitNames String array
     *
     * @since 0.25-alpha
     **************************************************************************/
    public String[] getTraitNames()
    {
        return Arrays.copyOf(this.traitNames.toArray(),
                this.traitNames.size(), String[].class);
    }

    /***************************************************************************
     * initializeIndices
     *
     * • Creates the indices array and fills it with -1. 0 is a valid list
     *   number and index so it needs to be negative.
     * @since 0.25-alpha
     **************************************************************************/
    private void initializeIndices(String dialogue)
    {
        int referenceSum = 0;

        int[] referenceCounts = Delimiter.referenceCounter(dialogue,
                true);

        this.indices = new int[referenceCounts.length][];

        int listIndex = Reference.LIST.ordinal();
        int staticIndex = Reference.STATIC.ordinal();

        this.indices[listIndex] = new int[referenceCounts[listIndex] * 2];
        this.indices[staticIndex] = new int[referenceCounts[staticIndex] * 2];

        //Initialize indices to -1
        for(int i = 0; i < indices.length; i++)
        {
            for(int j = 0; j < indices[i].length; j++)
            {
                this.indices[i][j] = -1;
            }
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
     * @since 0.25-alpha
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
        while(cutDown.contains(Delimiter.ESCAPE_STRING))
        {
            escapeIndex = cutDown.indexOf(Delimiter.ESCAPE_STRING);
            String subString = cutDown.substring(escapeIndex);

            if(Delimiter.verify(subString))
            {
                String lastString = cutDown.substring(0, escapeIndex + 1);
                formattedDialogue.append(lastString);

                String name = Delimiter.getName(subString, false);

                Reference ref = Delimiter.getReference(subString, false);
                int delimiterLength = Delimiter.getCombinedDelimiterLength(ref);


                oldStringIndex += lastString.length() + delimiterLength - 1
                        + name.length();
                newStringIndex += lastString.length();

                addIndex(ref, name, newStringIndex);

                cutDown = cutDown.substring(escapeIndex + delimiterLength
                        + name.length());
            }
            //This handles escape characters in the dialogue
            //We don't want to mess with them, so we cut down and move on
            else
            {
                escapeIndex = cutDown.indexOf(Delimiter.ESCAPE_STRING);

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
     * getNameIndex
     *
     * @since 0.25-alpha
     **************************************************************************/
    private int getNameIndex(Reference ref, String name)
    {

        switch(ref)
        {
            case LIST:

                for(int i = 0; i < this.lists.length; i++)
                {
                    if(this.lists[i].getName().equals(name))
                    {
                        return i;
                    }
                }

                return -1;

            case STATIC:

                for(int i = 0; i < this.staticVars.length; i++)
                {
                    if(this.staticVars[i].equals(name))
                    {
                        return i;
                    }
                }

                return -1;


            default:

                System.err.println("DialogueParser: Error in " +
                        "getStaticNameIndex, update likely required.");

                return -1;
        }
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
     * @since 0.25-alpha
     **************************************************************************/
    private void addIndex(Reference ref, String name, int index)
    {
        int refIndex = ref.ordinal();

        switch(ref)
        {
            case LIST:
            case STATIC:

                int staticListNumber = getNameIndex(ref, name);

                for(int i = 0; i < indices[refIndex].length; i += 2)
                {
                    if(this.indices[refIndex][i] == -1)
                    {
                        this.indices[refIndex][i] = staticListNumber;
                        this.indices[refIndex][i + 1] = index;
                        break;
                    }
                }

                break;

            default:

                System.err.println("DialogueParser: Error in addIndex, update" +
                        "likely required.");
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
     * @since 0.25-alpha
     **************************************************************************/
    private void initializeLists()
    {
        this.traitNames = new LinkedHashSet<>();

        Scanner stringScan;

        int listCount = getListCount();
        this.lists = new List[listCount];

        if(listCount == 0)
        {
            return;
        }

        for(int i = 0; i < listCount; i++)
        {
            String absolutePath = Parser.getNextLine(dialogueIn);

            File listPath = new File(absolutePath);

            ListParser parser = new ListParser(listPath);

            lists[i] = new List(parser);

            this.traitNames.addAll(parser.getTraitNames());

        }

        for(List list: lists)
        {
            String[] traitNames = Arrays.copyOf(this.traitNames.toArray(),
                    this.traitNames.size(), String[].class);

            list.initializeTraits(traitNames);
        }

    }

    /***************************************************************************
     * initializeStaticVars
     *
     * @since 0.25-alpha
     **************************************************************************/
    private void initializeStaticVars()
    {
        Scanner stringScan;

        int staticVarCount = getListCount();

        this.staticVars = new String[staticVarCount];

        if(staticVarCount == 0)
            return;

        String listNames = Parser.getNextLine(dialogueIn);

        stringScan = new Scanner(listNames);
        int index = 0;

        while(this.staticVars.length > 0 && stringScan.hasNext())
        {
            if(index > staticVars.length)
            {
                System.err.println("DialogueParser Warning: " +
                        "Static references exceed size");
                break;
            }

            String nextList = stringScan.next();

            this.staticVars[index] = nextList;

            index++;
        }

        if(index != staticVars.length)
        {
            System.out.println("DialogueParser Warning: " +
                    "Size exceeds list references ");
        }

    }

    /***************************************************************************
     * getListCount
     *
     * @since 0.25-alpha
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
     * @since 0.25-alpha
     **************************************************************************/
    private void initializeDialogue(String path)
    {
        try
        {
            this.dialogueIn = new Scanner(new FileInputStream(path)); // TODO: This is the part that fails
        }
        catch(IOException e)
        {
            System.err.println("DialogueParser could not locate file: " + path);
            System.exit(-1);
        }
    }
}
