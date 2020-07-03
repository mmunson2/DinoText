package DinoViewer;

import DinoParser.Dino;

import java.util.Scanner;


/*******************************************************************************
 * DinoViewer
 *
 * @author Matthew Munson
 * Date: 6/21/2020
 * @version 0.2-alpha
 *
 * A simple demonstration of the Dino API, allowing the user to specify
 * a dialogue file and view as many random dialogue combinations as they
 * please.
 *
 * Expanded in version 0.15-alpha to allow the user to pass in static variables
 * at runtime. This demonstrates how a player may enter their name and it can
 * be automatically inserted into the text.
 *
 ******************************************************************************/
public class DinoViewer
{

    /***************************************************************************
     * main
     *
     **************************************************************************/
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("_________________________________________________");
        System.out.println("Enter the name of the dialogue file " +
                "you'd like to open.");

        String file = keyboard.nextLine();

        Dino dino = new Dino(file);


        while(true)
        {
            int staticCount = dino.getStaticVariableCount();

            if(staticCount > 0)
            {
                for(int i = 0; i < staticCount; i++)
                {
                    System.out.println("__________");
                    System.out.println("Enter static variable: "
                            + dino.getStaticVariableName(i));

                    String variable = keyboard.nextLine();

                    dino.setStaticVariable(i, variable);
                }
            }

            int traitCount = dino.getTraitCount();

            if(traitCount > 0)
            {
                double[] traitVals = new double[traitCount];

                for(int i = 0; i < traitCount; i++)
                {
                    System.out.println("__________");
                    System.out.println("Enter trait value: " + dino.getTraitName(i));

                    double value = keyboard.nextDouble();

                    traitVals[i] = value;
                }

                dino.setTraitValues(traitVals);
            }


            System.out.println("_________________________________________________");
            System.out.println("Enter the number of randomly generated " +
                    "dialogue samples you'd like to view.");

            int samples = keyboard.nextInt();
            keyboard.nextLine();

            for(int i = 0; i < samples; i++)
            {
                System.out.println(dino.getDialogue());
            }


            System.out.println("Continue? (Y/N)");

            String reply = keyboard.nextLine();

            if(reply.equalsIgnoreCase("N"))
            {
                break;
            }
            //Else: continue
        }
    }
}
