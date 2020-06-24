package DinoText;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;


/*******************************************************************************
 * DinoText Prototype
 *
 * @author Matthew Munson
 * Date: 6/17/2020
 * @version 0.1
 *
 * Command line proof of concept for a dynamic text creation tool. Organized
 * into three phases:
 *
 * 1) Enter dialogue. This is the 'Mad-Libs' style script, where any number
 * of fill-in-the-blanks can be inserted. These blanks can be named anything
 * the user desires. A list can be referenced multiple times in a piece of
 * dialogue, list names are NOT case sensitive.
 *
 * 2) Populate lists. Each fill-in-the-blank created by the user must be
 * populated.
 *
 * 3) Write to file. The dialogue and list files are written as text files.
 * These will be viewable using a separate command line program.
 *
 ******************************************************************************/
public class DinoText
{
    private static Scanner keyboard = new Scanner(System.in);
    private static Set<DinoList> lists = new LinkedHashSet<>();

    /***************************************************************************
     * main method
     *
     * Handles the three program phases. Writing to file is delayed until all
     * user input has been received, as they have the option to quit without
     * saving even during the list creation phase.
     *
     * @param args Currently unused
     *
     **************************************************************************/
    public static void main(String[] args)
    {
        DinoWriter writer = new DinoWriter();

        printIntroText();
        String dialogue = getUserDialogue();

        for(DinoList list : lists)
        {
            populateList(list);
        }

        String dialogueName = promptDialogueName();
        writer.writeDialogueToFile(dialogueName, dialogue, lists);

        for(DinoList list : lists)
        {
            writer.writeListToFile(list);
        }

    }

    /***************************************************************************
     * getUserDialogue
     *
     * Allows the user to enter dialogue over multiple lines, an enter press
     * inserts a newline into the String. The dialogue is terminated by entering
     * \finish, and the user can quit at any time using \exit. If an escape
     * character is used in any other case, the escape character handler is
     * called to identify it.
     *
     * A StringBuilder is used in the place of repeated concatenation for
     * efficiency.
     *
     * @return A string representing multiple lines of user dialogue
     *
     **************************************************************************/
    private static String getUserDialogue()
    {
        String nextLine;
        StringBuilder builder = new StringBuilder();

        while(true)
        {
            nextLine = keyboard.nextLine();

            if(nextLine.equalsIgnoreCase("\\finish"))
            {
                break;
            }
            if(nextLine.equalsIgnoreCase("\\exit"))
            {
                System.exit(1);
            }
            else if(nextLine.contains("\\"))
            {
                recursiveEscapeHandler(nextLine);
            }

            builder.append("\n");
            builder.append(nextLine);

        }

        return builder.toString();
    }


    /***************************************************************************
     * promptDialogueName
     *
     * Asks the user to provide a name for the dialogue file. Automatically
     * appends ".txt" if not entered by the user.
     *
     * @return A string representing the intended name of the dialogue file.
     *
     **************************************************************************/
    private static String promptDialogueName()
    {
        System.out.println("Enter a name for the dialogue file: ");

        String name = keyboard.next();
        keyboard.nextLine();

        if(name.length() < 4
                || !name.substring(name.length() - 4).equals(".txt"))
        {
            name += ".txt";
        }

        return name;
    }


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
    private static void recursiveEscapeHandler(String nextLine)
    {
        int escapeIndex = nextLine.indexOf('\\');

        //Base case
        if(escapeIndex == -1)
        {
            return;
        }

        //Look for open bracket, wary of ArrayIndexOutOfBoundsException
        if(nextLine.length() > (escapeIndex + 2)
           && nextLine.charAt(escapeIndex + 1) == 'L'
           && nextLine.charAt(escapeIndex + 2) == '[')
        {
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
    private static void parseListName(String listString)
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

        lists.add(new DinoList(listString));

    }

    /***************************************************************************
     * populateList
     *
     * Allows the user to enter list data over multiple lines. Each newline
     * is treated as a new list entry, as entries are expected to be somewhat
     * short. The list is terminated by entering \finish, and the user can quit
     * at any time using \exit.
     *
     * @param list The DinoList to be initialized by the user.
     *
     **************************************************************************/
    private static void populateList(DinoList list)
    {
        System.out.println("\n________________________________________");
        System.out.println("Populate List: ");
        System.out.println("List name: " + list.getName());
        System.out.println("Enter list contents one line at a time. ");
        System.out.println("Type \"\\finish\" to complete dialogue entry. " +
                "\nType \\exit to quit without saving.");
        System.out.println();


        String nextLine;
        int count = 1;
        while(true)
        {
            System.out.print(count + ": ");

            nextLine = keyboard.nextLine();

            if(nextLine.equalsIgnoreCase("\\finish"))
            {
                break;
            }
            if(nextLine.equalsIgnoreCase("\\exit"))
            {
                System.exit(1);
            }

            list.add(nextLine);

            count++;
        }
    }

    /***************************************************************************
     * printIntroText
     *
     * Prints some instructions for the user.
     *
     **************************************************************************/
    private static void printIntroText()
    {
        System.out.println();
        System.out.println("Enter dialogue in as many lines as necessary. " +
                "Use \\L[NAME] to specify a list, where NAME is the " +
                "list name." +
                " You will be asked to populate each list once the" +
                " dialogue is complete.");
        System.out.println();
        System.out.println("Type \"\\finish\" to complete dialogue entry. " +
                "\nType \\exit to quit without saving.");
    }


}
