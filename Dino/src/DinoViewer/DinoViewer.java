package DinoViewer;

import DinoParser.Dino;

import java.util.Scanner;


/*******************************************************************************
 * DinoViewer
 *
 * @author Matthew Munson
 * Date: 6/21/2020
 * @version 0.15-alpha
 *
 * A simple demonstration of the Dino API, allowing the user to specify
 * a dialogue file and view as many random dialogue combinations as they
 * please.
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

        System.out.println("_________________________________________________");
        System.out.println("Enter the number of randomly generated " +
                "dialogue samples you'd like to view.");

        int samples = keyboard.nextInt();

        for(int i = 0; i < samples; i++)
        {
            System.out.println(dino.getDialogue());
        }
    }
}
