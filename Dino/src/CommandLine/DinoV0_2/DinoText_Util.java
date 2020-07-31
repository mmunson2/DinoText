package CommandLine.DinoV0_2;

import java.util.InputMismatchException;
import java.util.Scanner;

/*******************************************************************************
 * DinoText_Util
 *
 * @author Matthew Munson
 * Date: 7/2/2020
 * @version 0.25-alpha
 *
 * Methods that prompt the user for information.
 *
 ******************************************************************************/
public class DinoText_Util
{
    private static Scanner keyboard = new Scanner(System.in);

    /***************************************************************************
     * promptNumberMenu
     *
     * @since 0.25-alpha
     **************************************************************************/
    static int promptNumberMenu(int choices)
    {
        System.out.println("Enter a number between 1 and " + choices
                + ":");

        while(true)
        {
            try
            {
                int choice = keyboard.nextInt();
                keyboard.nextLine();

                if(choice > 0 && choice <= choices)
                {
                    return choice;
                }
                else
                {
                    System.out.println("Error: " + choice + " is not between " +
                            "1 and " + choices + ".");
                    System.out.println("Enter a number between 1 and " + choices
                            + ":");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error: Invalid character detected.");
                System.out.println("Enter a number between 1 and " + choices
                        + ":");
                keyboard.nextLine();
            }
        }
    }


    /***************************************************************************
     * promptYesNo
     *
     * @since 0.25-alpha
     **************************************************************************/
    static boolean promptYesNo(String message)
    {
        String nextLine;

        while(true)
        {
            System.out.println(message + " (Y/N)");
            nextLine = keyboard.nextLine();

            if (nextLine.equalsIgnoreCase("Y"))
            {
                return true;
            }
            else if (nextLine.equalsIgnoreCase("N"))
            {
                return false;
            }
            else
            {
                System.out.println("Entry: \"" + nextLine + "\" " +
                        "could not be interpreted");
                System.out.println("Please enter a single Y or N");
            }
        }
    }
}
