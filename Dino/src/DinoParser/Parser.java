package DinoParser;

import java.util.Scanner;

/*******************************************************************************
 * Parser
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.1
 *
 ******************************************************************************/
class Parser
{

    /***************************************************************************
     * getNextLine
     *
     **************************************************************************/
    static String getNextLine(Scanner dialogueIn)
    {
        String nextLine;

        while(true)
        {
            nextLine = dialogueIn.nextLine();

            if(nextLine.length() > 0 && nextLine.charAt(0) != '#')
                return nextLine;
        }
    }
}
