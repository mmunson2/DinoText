package DinoText_GUI.MODEL;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/*******************************************************************************
 * DinoWriter
 *
 * @author Matthew Munson
 * Date: 6/18/2020
 * @version 0.1
 *
 * File interface for the DinoText dynamic text creation tool. Handles both
 * List and Dialogue file writes with separate methods.
 *
 * Note that a line beginning with a # symbol is ignored by the parser, this
 * symbol can be used to insert comments anywhere in any file type.
 *
 ******************************************************************************/
public class DinoWriter
{

    private FileWriter writer;

    public DinoWriter() {}

    /***************************************************************************
     * writeListToFile
     *
     * Writes a List file given a DinoList Object. Simply utilizes the list
     * toString() method to populate data.
     *
     * See Examples/ListDemo.txt for formatting example
     *
     * @param list The DinoList to write to file.
     *
     **************************************************************************/
    public void writeListToFile(DinoList list)
    {
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
     **************************************************************************/
    public void writeDialogueToFile(String path, String dialogue,
                                    Set<DinoList> lists)
    {
        try
        {
            this.writer = new FileWriter(path);
            StringBuilder builder = new StringBuilder();
            writeDate();

            this.writer.write(
                    "\nLists Referenced: " + lists.size() + "\n");

            for(DinoList list : lists)
            {
                builder.setLength(0);

                builder.append(list.getName());
                builder.append(" ");

                this.writer.write(builder.toString());
            }

            this.writer.write('\n');
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
     **************************************************************************/
    private void writeDate() throws IOException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        this.writer.write("# Generated: " + dtf.format(now) + "\n");
    }

}
