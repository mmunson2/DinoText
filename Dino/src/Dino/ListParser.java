package Dino;

import Dino.List.ListEntry;
import Dino.List.Trait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/*******************************************************************************
 * ListParser
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.25-alpha
 *
 * This class is used to extract information from List files. Data is then
 * accessed by the DialogueParser class.
 *
 ******************************************************************************/
public class ListParser
{
    private Scanner fileScanner;
    private String name;

    private ListEntry[] list;
    private Set<String> traitNames;

    /***************************************************************************
     * ListParser
     *
     * @since 0.25-alpha
     **************************************************************************/
    public ListParser(File file)
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
        this.list = new ListEntry[size];

        for(int i = 0; i < list.length; i++)
        {
            String listEntry = Parser.getNextLine(fileScanner);
            stringScan = new Scanner(listEntry);
            String entryNumber = stringScan.next(); //Ignore the entry number
            String entryName = stringScan.nextLine();
            entryName = entryName.substring(1);

            String dataEntry = Parser.getNextLine(fileScanner);
            stringScan = new Scanner(dataEntry);

            double baseProbability = stringScan.nextDouble();

            ArrayList<Trait> traitList = new ArrayList<>();

            while(stringScan.hasNext())
            {
                String traitLine = stringScan.next();

                Scanner traitScanner = new Scanner(traitLine);
                traitScanner.useDelimiter(":");

                String traitName = traitScanner.next();
                double lowerBound = traitScanner.nextDouble();
                double upperBound = traitScanner.nextDouble();
                double traitProbability = traitScanner.nextDouble();

                Trait nextTrait = new Trait(traitName,lowerBound,
                        upperBound,traitProbability);

                traitList.add(nextTrait);
            }

            Trait[] traits = null;

            if(traitList.size() > 0)
            {
                traits = new Trait[traitList.size()];

                for(int j = 0; j < traits.length; j++)
                {
                    traits[j] = traitList.get(j);
                }
            }


            list[i] = new ListEntry(entryName, baseProbability, traits);
        }

        this.setTraitNames();
    }

    /***************************************************************************
     * setTraitNames
     *
     * @since 0.25-alpha
     **************************************************************************/
    private void setTraitNames()
    {
        this.traitNames = new LinkedHashSet<>();

        for(int i = 0; i < this.list.length; i++)
        {
            Trait[] traits = this.list[i].getTraits();

            if(traits == null)
                continue;

            for(int j = 0; j < traits.length; j++)
            {
                this.traitNames.add(traits[j].getName());
            }
        }
    }

    public Set<String> getTraitNames()
    {
        return this.traitNames;
    }


    /***************************************************************************
     * getName
     *
     * @since 0.25-alpha
     **************************************************************************/
     public String getName()
    {
        return this.name;
    }

    /***************************************************************************
     * getList
     *
     * @since 0.25-alpha
     **************************************************************************/
     public ListEntry[] getList()
    {
        return this.list;
    }


    /***************************************************************************
     * initialize
     *
     * @since 0.25-alpha
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
