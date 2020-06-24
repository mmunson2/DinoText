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
    private int[] indices;

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

        int indexOffset = 0;

        for(int i = 0; i < this.indices.length; i += 2)
        {
            int originalLength = retVal.length();

            retVal = insertToIndex(this.indices[i],
                    this.indices[i + 1] + indexOffset, retVal);

            indexOffset += retVal.length() - originalLength;
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
