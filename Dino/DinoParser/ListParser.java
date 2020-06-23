package DinoParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/*******************************************************************************
 * ListParser
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.1
 *
 ******************************************************************************/
class ListParser
{
    private String[] list;
    private Scanner file;
    private String name;


    /***************************************************************************
     * ListParser
     *
     **************************************************************************/
    ListParser(String name)
    {
        Scanner stringScan;

        initialize(name);
        this.name = name;

        //Unused currently, sanity check? Remove entirely?
        String nameLine = Parser.getNextLine(file);

        String sizeLine = Parser.getNextLine(file);

        stringScan = new Scanner(sizeLine);
        stringScan.next(); //Ignore the "Size: " text

        int size = stringScan.nextInt();
        this.list = new String[size];

        for(int i = 0; i < list.length; i++)
        {
            String listEntry = Parser.getNextLine(file);
            stringScan = new Scanner(listEntry);
            stringScan.next(); //Ignore the entry number
            String line = stringScan.nextLine();
            line = line.substring(1);
            list[i] = line;
        }
    }

    /***************************************************************************
     * getName
     *
     **************************************************************************/
     String getName()
    {
        return this.name;
    }

    /***************************************************************************
     * getList
     *
     **************************************************************************/
     String[] getList()
    {
        return this.list;
    }



    /***************************************************************************
     * initialize
     *
     **************************************************************************/
    private void initialize(String name)
    {
        String path = name + ".txt";

        try
        {
            this.file = new Scanner(
                    new FileInputStream(path));
        }
        catch(IOException e)
        {
            System.err.println("ListParser could not locate file: " + path);
            System.exit(-1);
        }
    }

}
