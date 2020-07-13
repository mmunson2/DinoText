package DinoText_GUI.MODEL.CommandLine;

import DinoParser.List.Trait;
import DinoText_GUI.MODEL.DinoList;

import static DinoText_GUI.MODEL.CommandLine.DinoText_Util.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*******************************************************************************
 * DinoText_Traits
 *
 * @author Matthew Munson
 * Date: 7/2/2020
 * @version 0.25-alpha
 *
 * Methods for entering trait information from the command line.
 *
 ******************************************************************************/
public class DinoText_Traits
{
    private static Scanner keyboard = new Scanner(System.in);

    /***************************************************************************
     * populateTraits
     *
     * @since 0.25-alpha
     **************************************************************************/
    static void populateTraits(DinoList list)
    {
        ArrayList<ArrayList<Trait>> traits = new ArrayList<>();

        System.out.println("\n________________________________________");
        System.out.println("Populate Traits: ");
        System.out.println();

        for(int i = 0; i < list.size(); i++)
        {
            traits.add(i, new ArrayList<>());
        }

        while(true)
        {
            printList(list, traits);

            System.out.println("Select the List entry to apply a trait");

            int listChoice = promptNumberMenu(list.size());
            listChoice -= 1; //Convert to array indexing

            Trait nextTrait = promptTrait();

            traits.get(listChoice).add(nextTrait);


            boolean moreTraits = promptYesNo("Add more traits?");

            if(!moreTraits)
            {
                break;
            }

        }

        //Set each list entry's trait array
        for(int i = 0; i < list.size(); i++)
        {
            int nextSize = traits.get(i).size();

            if(nextSize > 0)
            {
                Trait[] traitArray = new Trait[nextSize];

                for(int j = 0; j < nextSize; j++)
                {
                    traitArray[j] = traits.get(i).get(j);
                }

                list.getEntry(i).setTraits(traitArray);
            }
        }
    }

    /***************************************************************************
     * printTraitLine
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static void printTraitLine(ArrayList<Trait> traitList)
    {
        for(Trait trait : traitList)
        {
            System.out.print(" [   ");
            System.out.print(trait);
            System.out.print("  ]   ");
        }

        System.out.println();
    }

    /***************************************************************************
     * promptTrait
     *
     * Note that min and max are hard coded to 0-100 to make command line
     * entry less tedious.
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static Trait promptTrait()
    {

        while(true)
        {
            try
            {
                System.out.println("Enter the Trait Name");

                String name = keyboard.nextLine();

                System.out.println("Enter the lowest possible value on your " +
                        "trait's scale");

                //double min = keyboard.nextDouble();
                double min = 0;
                System.out.println(min);

                System.out.println("Enter the largest possible value on your " +
                        "trait's scale");

                //double max = keyboard.nextDouble();
                double max = 100;
                System.out.println(max);

                if(max <= min)
                {
                    System.out.println("Error: Max must be greater than min");
                    continue;
                }

                System.out.println("Enter the trait's lower bound " +
                        "for activation");

                double lowerBound = keyboard.nextDouble();

                System.out.println("Enter the trait's upper bound");

                double upperBound = keyboard.nextDouble();

                if(upperBound <= lowerBound)
                {
                    System.out.println("Error: UpperBound must be greater " +
                            "than lowerBound");
                    continue;
                }


                System.out.println("Enter the trait's probability " +
                        "(Overrides base probability)");

                double probability = keyboard.nextDouble();
                keyboard.nextLine();



                double range = max - min;

                Trait retVal = new Trait(name,
                        lowerBound/range,
                        upperBound/range,
                        probability);

                System.out.println("Trait: " + name + " successfully constructed");

                return retVal;

            }
            catch(InputMismatchException e)
            {
                System.out.println("Error: Invalid character detected.");

                keyboard.nextLine();
            }
        }
    }

    /***************************************************************************
     * printList
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static void printList(DinoList list,
                                  ArrayList<ArrayList<Trait>> traits)
    {
        System.out.println("Current List Contents: ");
        System.out.println("Name: " + list.getName());
        System.out.println();

        for(int i = 0; i < list.size(); i++)
        {
            System.out.print( (i+1) + ": " );
            System.out.print(list.getEntry(i));
            printTraitLine(traits.get(i));
        }

        System.out.println();
    }



}
