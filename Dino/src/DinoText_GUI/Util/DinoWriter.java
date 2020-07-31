package DinoText_GUI.Util;

import DinoText_GUI.Util.DinoList;

import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.Set;

/*******************************************************************************
 * DinoWriter
 *
 * @author Matthew Munson
 * Date: 6/18/2020
 * @version 0.4-alpha
 *
 * File interface for the DinoText dynamic text creation tool. Handles both
 * List and Dialogue file writes with separate methods.
 *
 * Note that a line beginning with a # symbol is ignored by the parser, this
 * symbol can be used to insert comments anywhere in any file type.
 *
 * //Todo: Place list files in the same directory as dialogue when path specified
 ******************************************************************************/
public class DinoWriter
{

    private FileWriter writer;

    public DinoWriter() {}

    /***************************************************************************
     * writeListToFile
     *
     * Writes a List file given a List Object. Simply utilizes the list
     * toString() method to populate data.
     *
     * See Examples/ListDemo.txt for formatting example
     *
     * @param list The List to write to file.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public void writeListToFile(DinoList list)
    {
        if(list.skipWrite())
            return;

        String fileName = list.getName() + ".txt";

        try
        {
            this.writer = new FileWriter(fileName);
            writeDate();

            this.writer.write(list.toString());

            this.writer.close();
        }
        catch (IOException e)
        {
            System.err.println("An error occurred while writing to file");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("> Successfully wrote " + list.getName() +
                " to file.");
    }



    /***************************************************************************
     * writeDialogueToFile
     *
     * Writes a dialogue file given its name, dialogue String, and Set of
     * linked DinoLists.
     *
     * See Examples/DialogueDemo.txt for formatting example
     *
     * @param path File path to the dialogue
     * @param dialogue The dialogue String to write
     * @param lists The set of DinoLists linked to the dialogue
     *
     *
     * @since 0.4-beta
     **************************************************************************/
    public void writeDialogueToFile(String path, String dialogue,
                                    Set<DinoList> lists, Set<String> staticVars)
    {
        String[] listsStringArray = new String[lists.size()];

        int index = 0;
        for(DinoList list: lists)
        {
            listsStringArray[index] = list.getName();
            index++;
        }

        String[] staticVarsStringArray = new String[staticVars.size()];
        index = 0;
        for(String staticVar: staticVars)
        {
            staticVarsStringArray[index] = staticVar;
            index++;
        }

        
        writeDialogueToFile(path, dialogue,
                listsStringArray, staticVarsStringArray);
    }

    /***************************************************************************
     * writeDialogueToFile
     *
     * Writes a dialogue file given its name, dialogue String, and Set of
     * linked DinoLists.
     *
     * See Examples/DialogueDemo.txt for formatting example
     *
     * @param path File path to the dialogue
     * @param dialogue The dialogue String to write
     * @param lists List Names
     * @param staticVars Static Variable Names
     *
     * @since 0.4-beta
     **************************************************************************/
    public void writeDialogueToFile(String path, String dialogue,
                                    String[] lists, String[] staticVars)
    {
        try
        {
            this.writer = new FileWriter(path);
            StringBuilder builder = new StringBuilder();
            writeDate();

            //Write lists to file
            this.writer.write(
                    "\nLists Referenced: " + lists.length + "\n");

            for(String list : lists)
            {
                builder.setLength(0);

                builder.append(list);
                builder.append(" ");

                this.writer.write(builder.toString());
            }

            this.writer.write('\n');

            //Write Static Variables to File:
            this.writer.write(
                    "\nStatic Variables: " +
                            staticVars.length + "\n");

            for(String var : staticVars)
            {
                builder.setLength(0);

                builder.append(var);
                builder.append(" ");

                this.writer.write(builder.toString());
            }

            this.writer.write('\n');

            //Write dialogue to file
            this.writer.write(dialogue);

            this.writer.close();
        }
        catch (IOException e)
        {
            System.err.println("An error occurred while writing to file");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("> Successfully wrote to " + path);
    }







    /***************************************************************************
     * writeDate
     *
     * Writes the generation time and date to the top of the file.
     *
     * Referenced: https://www.javatpoint.com/java-get-current-date
     *
     * @since 0.25-alpha
     **************************************************************************/
    private void writeDate() throws IOException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        this.writer.write("# Generated: " + dtf.format(now) + "\n");
    }

}
