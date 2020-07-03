package DinoText;

import DinoParser.List.Trait;
import static DinoText.DinoText_Util.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*******************************************************************************
 * DinoText_Traits
 *
 * @author Matthew Munson
 * Date: 7/2/2020
 * @version 0.2-alpha
 *
 ******************************************************************************/
public class DinoText_Traits
{
    private static Scanner keyboard = new Scanner(System.in);

    /***************************************************************************
     * populateTraits
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

            boolean useExisting = false;
            int traitCount = getTraitCount(traits);

            if(traitCount > 0)
            {
                useExisting = promptYesNo("Use existing trait?");
            }

            if(useExisting) // Use existing Trait
            {
                System.out.println("Created Traits:");
                printCreatedTraits(traits);

                System.out.println("Enter trait number to apply to \""
                        + list.getEntry(listChoice).getListEntry() + "\"");

                int userChoice = promptNumberMenu(traitCount);
                userChoice -= 1; //Convert to array indexing

                Trait nextTrait = getTraitFromNumber(traits, userChoice);

                traits.get(listChoice).add(nextTrait);
            }
            else //Create new Trait
            {
                Trait nextTrait = promptTrait();

                traits.get(listChoice).add(nextTrait);
            }


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
     * getTraitCount
     **************************************************************************/
    private static int getTraitCount(ArrayList<ArrayList<Trait>> traits)
    {
        int sum = 0;

        for(int i = 0; i < traits.size(); i++)
        {
            sum += traits.get(i).size();
        }

        return sum;
    }

    /***************************************************************************
     * printCreatedTraits
     **************************************************************************/
    private static void printCreatedTraits(ArrayList<ArrayList<Trait>> traits)
    {
        int counter = 1;

        for(int i = 0; i < traits.size(); i++)
        {
            for (int j = 0; j < traits.get(i).size(); j++)
            {
                System.out.print(counter + ": ");
                System.out.println(traits.get(i).get(j).toString());

                counter++;
            }
        }
    }

    /***************************************************************************
     * printTraitLine
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
     * getTraitFromNumber
     **************************************************************************/
    private static Trait getTraitFromNumber(ArrayList<ArrayList<Trait>> traits,
                                            int index)
    {
        int counter = 0;


        for(int i = 0; i < traits.size(); i++)
        {
            if(counter + traits.get(i).size() > index)
            {
                return traits.get(i).get(index - i);
            }
        }

        return null;
    }

    /***************************************************************************
     * promptTrait
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

                double min = keyboard.nextDouble();

                System.out.println("Enter the largest possible value on your " +
                        "trait's scale");

                double max = keyboard.nextDouble();

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
