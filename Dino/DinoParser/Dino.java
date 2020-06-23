package DinoParser;

import java.util.Random;

/*******************************************************************************
 * Dino
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.1
 *
 * The Dino API allows for dynamic text to be quickly generated and returned
 * for display.
 *
 ******************************************************************************/
public class Dino
{
    private String dialogue;
    private String[][] listContents;
    private int[][] indices;


    /***************************************************************************
     * Dino
     *
     * Constructs a Dino Object capable of generating randomized dialogue.
     * This constructor handles all file reads and parsing, so call it during
     * a load sequence if possible.
     *
     * @param path Path to the dialogue file to be processed.
     *
     **************************************************************************/
    public Dino(String path)
    {
        DialogueParser parser = new DialogueParser(path);
        this.dialogue = parser.getDialogue();
        this.listContents = parser.getListArray();
        this.indices = parser.getIndices();
    }

    /***************************************************************************
     * getDialogue
     *
     * Generates randomized dialogue and returns it ready for display.
     *
     * @return A string containing the randomized dialogue
     **************************************************************************/
    public String getDialogue()
    {
        String retVal = this.dialogue;

        int totalIndices = getTotalIndices();
        int indexOffset = 0;
        int runningMin = Integer.MIN_VALUE;

        for(int i = 0; i < totalIndices; i++)
        {
            int[] minIndex = getNextMin(runningMin);

            runningMin = this.indices[minIndex[0]][minIndex[1]];

            int originalLength = retVal.length();

            retVal = insertToIndex(minIndex[0],
                    indices[minIndex[0]][minIndex[1]] + indexOffset,
                    retVal);

            indexOffset += retVal.length() - originalLength;
        }

        return retVal;
    }

    /***************************************************************************
     * getTotalIndices
     *
     **************************************************************************/
    private int getTotalIndices()
    {
        int sum = 0;

        for(int array[] : indices)
        {
            sum += array.length;
        }

        return sum;
    }

    /***************************************************************************
     * getNextMin
     *
     **************************************************************************/
    private int[] getNextMin(int runningMin)
    {
        int nextMin = Integer.MAX_VALUE;
        int[] retVal = new int[2];

        for(int i = 0; i < indices.length; i++)
        {
            for(int j = 0; j < indices[i].length; j++)
            {
                   if(indices[i][j] < nextMin && indices[i][j] > runningMin)
                   {
                       nextMin = indices[i][j];
                       retVal[0] = i;
                       retVal[1] = j;
                   }
            }
        }

        return retVal;
    }

    /***************************************************************************
     * insertToIndex
     *
     **************************************************************************/
    private String insertToIndex(int listNumber, int index, String dialogue)
    {
        String retVal = dialogue.substring(0,index);

        retVal += getRandomLine(listNumber);

        retVal += dialogue.substring(index + 1);

        return retVal;
    }

    /***************************************************************************
     * getRandomLine
     *
     **************************************************************************/
    private String getRandomLine(int listNumber)
    {
        Random random = new Random();

        int index = random.nextInt(listContents[listNumber].length);

        return listContents[listNumber][index];
    }


}
