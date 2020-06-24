package DinoViewer.DinoParser;

import java.io.FileInputStream;
import java.io.IOException;
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
 *
 * //Todo: restructure indices array to single dimensional
 * //Todo: Fix escape character crash
 ******************************************************************************/
class DialogueParser
{
    private Scanner dialogueIn;
    private ListParser[] lists;
    //Row number corresponds to list index, columns are the index where the
    //reference was used
    private int[][] indices;
    private String dialogue;


    /***************************************************************************
     * DialogueParser
     *
     **************************************************************************/
    DialogueParser(String path)
    {
        initializeDialogue(path);

        int listCount = getListCount();
        lists = new ListParser[listCount];

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
     **************************************************************************/
    String getDialogue()
    {
        return this.dialogue;
    }

    /***************************************************************************
     * getListArray
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
     **************************************************************************/
    int[][] getIndices()
    {
        return this.indices;
    }


    /***************************************************************************
     * initializeIndices
     *
     **************************************************************************/
    private void initializeIndices(String dialogue)
    {
        this.indices = new int[lists.length][];

        Scanner stringScan = new Scanner(dialogue);
        String next;
        int[] nameCounts = new int[lists.length];


        while(stringScan.hasNext())
        {
            next = stringScan.next();

            if(next.length() > 3
            && next.charAt(0) == '\\'
            && next.charAt(1) == 'L'
            && next.charAt(2) == '[')
            {
                next = next.substring(3);
                String name = parseListName(next);

                int nameIndex = getListNameIndex(name);
                nameCounts[nameIndex]++;
            }
        }

        //Initialize each array to its respective size
        for(int i = 0; i < indices.length; i++)
        {
            indices[i] = new int[nameCounts[i]];
        }

        //Initialize all array values to -1
        for(int i = 0; i < indices.length; i++)
        {
            for(int j = 0; j < indices[i].length; j++)
            {
                indices[i][j] = -1;
            }
        }

        //Check for any list entries that weren't used at all
        for(int i = 0; i < indices.length; i++)
        {
            if(indices[i].length == 0)
            {
                System.out.println("Warning: List #" + i + " with name: "
                + lists[i].getName() + " has zero references in dialogue" +
                        "file.");
            }
        }
    }


    /***************************************************************************
     * formatDialogue
     *
     **************************************************************************/
    private String formatDialogue(String dialouge)
    {
        //This string will be cut down as we iterate
        StringBuilder formattedDialogue = new StringBuilder();
        String cutDown = dialouge;
        int index;
        int runningIndex = 0;
        int reducedIndex = 0;

        //Go until there are no more escape characters
        while(cutDown.indexOf('\\') != -1)
        {
            index = cutDown.indexOf('\\');

            if(cutDown.charAt(index + 1) == 'L'
            && cutDown.charAt(index + 2) == '[')
            {
                String name = cutDown.substring(index + 3);
                name = parseListName(name);

                String lastString = cutDown.substring(0, index + 1);
                formattedDialogue.append(lastString);

                runningIndex += lastString.length() + 3 + name.length();
                reducedIndex += lastString.length() - 1;

                addIndex(name, reducedIndex);
                reducedIndex++; //Lol I don't know why this works

                cutDown = cutDown.substring(index + 4 + name.length());
            }
            else
            {
                //Base case: It's just a \ symbol, so cut down and move on
                index = cutDown.indexOf('\\');

                String lastString = dialouge.substring(0, index + 1);
                formattedDialogue.append(lastString);
                runningIndex += lastString.length();
                reducedIndex += lastString.length();

                cutDown = cutDown.substring(index + 1);
            }

        }

        formattedDialogue.append(dialouge.substring(runningIndex));

        return formattedDialogue.toString();
    }


    /***************************************************************************
     * parseListNames
     *
     **************************************************************************/
    private String parseListName(String listString)
    {
        //Todo: (Maybe) improve error handling
        if(!listString.contains("]"))
        {
            System.out.println("List name error: Missing \"]\", " +
                    "could not parse!");
            System.exit(-1);
        }

        int closeIndex = listString.indexOf("]");
        listString = listString.substring(0,closeIndex);

        if(getListNameIndex(listString) == -1)
        {
            System.out.println("List Error: " + listString + " is not" +
                    "linked to the dialogue file");
            System.exit(-1);
        }

        return listString;
    }

    /***************************************************************************
     * getListNameIndex
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
     **************************************************************************/
    private void addIndex(String listName, int index)
    {
        int[] list = this.indices[getListNameIndex(listName)];

        for(int i = 0; i < list.length; i++)
        {
            if(list[i] == -1)
            {
                list[i] = index;
                break;
            }
        }
    }

    /***************************************************************************
     * initializeLists
     *
     **************************************************************************/
    private void initializeLists()
    {
        Scanner stringScan;
        String listNames = Parser.getNextLine(dialogueIn);

        stringScan = new Scanner(listNames);
        int listIndex = 0;

        while(stringScan.hasNext())
        {
            if(listIndex > lists.length)
            {
                System.err.println("DialogueParser Warning: " +
                        "List references exceed size");
                break;
            }

            String nextList = stringScan.next();

            lists[listIndex] = new ListParser(nextList);
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
