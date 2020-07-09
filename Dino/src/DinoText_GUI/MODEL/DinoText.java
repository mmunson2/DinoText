package DinoText;

import DinoParser.Delimiter.Delimiter;
import DinoParser.Delimiter.Reference;

import static DinoText.DinoText_Traits.*;
import static DinoText.DinoText_Util.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/*******************************************************************************
 * DinoText Prototype
 *
 * @author Matthew Munson
 * Date: 6/17/2020
 * @version 0.25-alpha
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
 * @since 0.25-alpha
 ******************************************************************************/
public class DinoText
{
    private static Scanner keyboard = new Scanner(System.in);
    private static Set<DinoList> lists = new LinkedHashSet<>();
    private static Set<String> staticVars = new LinkedHashSet<>();

    /***************************************************************************
     * main method
     *
     * Handles the three program phases. Writing to file is delayed until all
     * user input has been received, as they have the option to quit without
     * saving even during the list creation phase.
     *
     * @param args Currently unused
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static void main(String[] args)
    {
        DinoWriter writer = new DinoWriter();

        String dialogueName = promptDialogueName();


        printIntroText();
        String dialogue = getUserDialogue();


        for(DinoList list : lists)
        {
            if(listFileExists(dialogueName, list.getName()))
            {
                if(!promptOverwrite(list))
                {
                    list.setSkipWrite(true);
                    continue;
                }
            }

            populateList(list);
        }

        writer.writeDialogueToFile(dialogueName, dialogue, lists, staticVars);

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
     * @since 0.25-alpha
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
            else if(nextLine.contains(Delimiter.ESCAPE_STRING))
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
     * @since 0.25-alpha
     **************************************************************************/
    private static String promptDialogueName()
    {
        System.out.println("Enter a name for the dialogue file: ");

        String name = keyboard.next();
        keyboard.nextLine();

        if(name.length() < 4 || !name.endsWith(".txt"))
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
     * //Todo: Update doc
     * //Todo: Move to different class
     * @since 0.25-alpha
     **************************************************************************/
    private static void recursiveEscapeHandler(String nextLine)
    {
        int escapeIndex = nextLine.indexOf(Delimiter.ESCAPE_STRING);

        //Base case
        if(escapeIndex == -1)
        {
            return;
        }

        String reducedString = nextLine.substring(escapeIndex);

        boolean verified = Delimiter.verify(reducedString);

        if(verified)
        {
            Reference ref = Delimiter.getReference(reducedString,
                    false);

            String name = Delimiter.getName(reducedString, false);

            switch(ref)
            {
                case LIST:

                    lists.add(new DinoList(name));
                    break;

                case STATIC:

                    staticVars.add(name);
                    break;


                default:

                    System.err.println(
                            "Warning: Unidentified reference in DinoText." +
                                    "Update likely required.");
            }

        }

        String remainder = nextLine.substring(escapeIndex + 1);

        recursiveEscapeHandler(remainder);
    }


    /***************************************************************************
     * populateList
     *
     * Allows the user to enter list data over multiple lines. Each newline
     * is treated as a new list entry, as entries are expected to be somewhat
     * short. The list is terminated by entering \finish, and the user can quit
     * at any time using \exit.
     *
     * @param list The List to be initialized by the user.
     *
     * @since 0.25-alpha
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

        boolean enterProbability = promptYesNo("Enter individual " +
                "probability values?");

        if(enterProbability)
        {
            populateProbability(list);
        }

        if(enterProbability)
        {
            boolean linkTraits = promptYesNo("Link Traits?");

            if(linkTraits)
            {
                populateTraits(list);
            }
        }

    }

    /***************************************************************************
     * populateProbability
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static void populateProbability(DinoList list)
    {
        System.out.println("\n________________________________________");
        System.out.println("Populate Probability: ");
        System.out.println();

        for(int i = 0; i < list.size(); i++)
        {
            System.out.print(list.getEntry(i).getListEntry());
            System.out.println(" Probability: ");

            double probability = keyboard.nextDouble();

            list.setProbability(i,probability);
        }

        keyboard.nextLine();
    }


    /***************************************************************************
     * listFileExists
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static boolean listFileExists(String dialogueName, String listName)
    {
        File file = (new File(dialogueName)).getAbsoluteFile();
        File parentDirectory = file.getParentFile();

        File listFile = new File(Paths.get(parentDirectory.toString(),
                listName).toString() + ".txt");

        return listFile.exists();
    }

    /***************************************************************************
     * promptOverwrite
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static boolean promptOverwrite(DinoList list)
    {
        String listName = list.getName();

        System.out.println("List: \"" + listName + "\" already exists. " +
                "Would you like to:");
        System.out.println("1) Overwrite \"" + listName + "\"");
        System.out.println("2) Link to the existing \"" + listName + "\"");

        int choice = promptNumberMenu(2);

        switch(choice)
        {
            case 1:
                return true;
            case 2:
                return false;

        }

        return false;
    }

    /***************************************************************************
     * printIntroText
     *
     * Prints some instructions for the user.
     *
     * @since 0.25-alpha
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
