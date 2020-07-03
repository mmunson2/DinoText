package DinoParser;

import DinoParser.Delimiter.Delimiter;
import DinoParser.Delimiter.Reference;
import DinoParser.List.List;

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
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.2-alpha
 *
 * Used to extract the contents of a dialogue file and the list files connected
 * to it.
 *
 ******************************************************************************/
class DialogueParser
{
    private Scanner dialogueIn;
    private List[] lists;
    private Set<String> traitNames;

    private String dialogue;
    private String[] staticVars;

    private int[][] indices;

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
        initializeStaticVars();


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
    List[] getListArray()
    {
        return this.lists;
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
    int[][] getIndices() { return this.indices; }

    String[][] getStaticVars()
    {
        String[][] retVal = new String[staticVars.length][2];

        for(int i = 0; i < retVal.length; i++)
        {
            retVal[i][0] = this.staticVars[i];
            retVal[i][1] = this.staticVars[i];
        }

        return retVal;
    }

    String[] getTraitNames()
    {
        return Arrays.copyOf(this.traitNames.toArray(),
                this.traitNames.size(), String[].class);
    }


    /***************************************************************************
     * initializeIndices
     *
     * • Creates the indices array and fills it with -1. 0 is a valid list
     *   number and index so it needs to be negative.
     *
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
     **************************************************************************/
    private void addIndex(Reference ref, String name, int index)
    {
        int refIndex = ref.ordinal();

        switch(ref)
        {
            case LIST:

                int listNumber = getNameIndex(ref, name);

                for(int i = 0; i < indices[refIndex].length; i += 2)
                {
                    if(this.indices[refIndex][i] == -1)
                    {
                        this.indices[refIndex][i] = listNumber;
                        this.indices[refIndex][i + 1] = index;
                        break;
                    }
                }

                break;

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

            ListParser parser = new ListParser(new File(
                    Paths.get(parentDirectory.toString(),
                            nextList).toString() + ".txt"));


            lists[listIndex] = new List(parser);

            this.traitNames.addAll(parser.getTraitNames());

            listIndex++;
        }


        if(listIndex != lists.length)
        {
            System.out.println("DialogueParser Warning: " +
                    "Size exceeds list references ");
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
