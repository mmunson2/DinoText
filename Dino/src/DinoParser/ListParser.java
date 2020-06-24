package DinoParser;

import java.io.File;
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
    private Scanner fileScanner;
    private String name;


    /***************************************************************************
     * ListParser
     *
     **************************************************************************/
    ListParser(File file)
    {
        String name = file.getName();

        Scanner stringScan;

        initialize(file);
        this.name = name.substring(0,name.length() - 4);

        //Unused currently, sanity check? Remove entirely?
        String nameLine = Parser.getNextLine(fileScanner);

        String sizeLine = Parser.getNextLine(fileScanner);

        stringScan = new Scanner(sizeLine);
        stringScan.next(); //Ignore the "Size: " text

        int size = stringScan.nextInt();
        this.list = new String[size];

        for(int i = 0; i < list.length; i++)
        {
            String listEntry = Parser.getNextLine(fileScanner);
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
    private void initialize(File file)
    {
        try
        {
            this.fileScanner = new Scanner(file);
        }
        catch(IOException e)
        {
            System.err.println("ListParser could not locate fileScanner: " + file.getName());
            System.exit(-1);
        }
    }

}
