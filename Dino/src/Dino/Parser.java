package Dino;

import java.util.Scanner;

/*******************************************************************************
 * Parser
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.25-alpha
 *
 * This class provides static methods to assist in the parsing of files.
 *
 ******************************************************************************/
class Parser
{

    /***************************************************************************
     * getNextLine
     *
     * @since 0.25-alpha
     **************************************************************************/
    static String getNextLine(Scanner dialogueIn)
    {
        String nextLine;

        while(true)
        {
            if(!dialogueIn.hasNext())
            {
                return null;
            }

            nextLine = dialogueIn.nextLine();

            if(nextLine.length() > 0 && nextLine.charAt(0) != '#')
                return nextLine;
        }
    }
}
