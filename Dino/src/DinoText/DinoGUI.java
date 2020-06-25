package DinoText;

import DinoParser.Dino;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DinoGUI {
    private JTextField input;
    private JPanel panel1;
    private JButton writeToFileButton;
    private JButton submitButton;
    private JTextPane output;
    private JTextPane listContents;
    private String dialogue;
    private String name;
    private String nextListItem;
    private Iterator<DinoList> itr;
    private DinoList currentList;
    private int count = 1;
    private static Set<DinoList> lists = new LinkedHashSet<>();
    private enum Mode {GETUSERDIALOGUE, POPULATELIST, DIALOGUEFILENAME, RESTART}
    private static Mode mode;



    public DinoGUI() {
        mode = Mode.GETUSERDIALOGUE;
        dialogue = "";
        name = "";

        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DinoWriter writer = new DinoWriter();

                writer.writeDialogueToFile(name, dialogue, lists);

                for (DinoList list : lists) {
                    writer.writeListToFile(list);
                }

                input.setText("");
                output.setText("Press the enter key to create a new dialogue file. Press the X in the top right corner to exit.");
                mode = Mode.RESTART;
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (mode) {
                    case GETUSERDIALOGUE:
                        String nextLine = input.getText();
                        StringBuilder builder = new StringBuilder();

                        if (nextLine.contains("\\")) {
                            recursiveEscapeHandler(nextLine);
                        }

                        dialogue = builder.toString();

                        mode = Mode.POPULATELIST;


                        itr = lists.iterator();

                        currentList = itr.next();

                        output.setText("Populate List: \nList name: " + currentList.getName() + "\nEnter list contents" +
                                " one line at a time. \nPress the enter key to complete a list entry." +
                                "\nPress \"Submit\" when all entries are made. \nPress the X in the upper right corner " +
                                "to quit without saving.");

                        nextListItem = "";

                        input.setText("");
                        break;
                    case POPULATELIST:
                        if (itr.hasNext()) {
                            count = 1;
                            currentList = itr.next();
                            input.setText("");
                            output.setText("Populate List: \nList name: " + currentList.getName() + "\nEnter list contents" +
                                    " one line at a time. \nPress the enter key to complete a list entry." +
                                    "\nPress \"Submit\" when all entries are made. \nPress the X in the upper right corner " +
                                    "to quit without saving.");
                        } else {
                            input.setText("");
                            output.setText("Enter a name for the dialogue file. Press the enter key when done. ");
                            mode = Mode.DIALOGUEFILENAME;
                        }
                        listContents.setText("");
                }
            }
        });

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (mode) {
                    case GETUSERDIALOGUE:
                        break;

                    case POPULATELIST:
                        output.setText("Populate List: \nList name: " + currentList.getName() + "\nEnter list contents" +
                                " one line at a time. \nPress the enter key to complete a list entry." +
                                "\nPress \"Submit\" when all entries are made. \nPress the X in the upper right corner " +
                                "to quit without saving.");

                        nextListItem = input.getText();

                        System.out.println(count + ": "); // DEBUG

                        currentList.add(nextListItem);

                        listContents.setText(listContents.getText() + "\n" + count + ": " + nextListItem);

                        count++;

                        break;

                    case DIALOGUEFILENAME:
                        name = input.getText();

                        if (name.length() < 4
                                || !name.substring(name.length() - 4).equals(".txt")) {
                            name += ".txt";
                        }
                        input.setText("");

                        output.setText("Press \"Write to File\" to create the dialogue file.");
                        break;

                    case RESTART:
                        mode = Mode.GETUSERDIALOGUE;
                        dialogue = "";
                        name = "";
                        nextListItem = "";
                        itr = null;
                        currentList = null;
                        count = 1;
                        lists = new LinkedHashSet<>();
                        output.setText("Enter dialogue in as many lines as necessary. \nUse \"\\L[NAME]\" to " +
                                "specify a list, where NAME is the list name.  \nYou will be asked to populate each " +
                                "list once the dialogue is complete.  \nPress \"Submit Dialogue\" to complete dialogue " +
                                "entry. \nPress the X in the upper right corner to quit without saving.");
                        break;
                }
                input.setText("");
            }
        });
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
    private static void recursiveEscapeHandler(String nextLine) {
        int escapeIndex = nextLine.indexOf('\\');

        //Base case
        if (escapeIndex == -1) {
            return;
        }

        //Look for open bracket, wary of ArrayIndexOutOfBoundsException
        if (nextLine.length() > (escapeIndex + 2)
                && nextLine.charAt(escapeIndex + 1) == 'L'
                && nextLine.charAt(escapeIndex + 2) == '[') {
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
    private static void parseListName(String listString) {
        //Todo: (Maybe) improve error handling
        if (!listString.contains("]")) {
            System.out.println("List name error: Missing \"]\", " +
                    "could not parse!");
            System.exit(-1);
        }

        int closeIndex = listString.indexOf("]");
        listString = listString.substring(0, closeIndex);

        lists.add(new DinoList(listString));
    }



    public static void main(String args[]) throws IOException {
        DinoGUI dinogui = new DinoGUI();
        JFrame frame = new JFrame("DinoGUI");
        frame.setContentPane(new DinoGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
