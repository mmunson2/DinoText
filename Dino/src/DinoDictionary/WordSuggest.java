// WordSuggest.java
// Camden Brewster
// Checks a sentence for certain word types that can be changed

package DinoDictionary;

import java.util.Scanner;

public class WordSuggest extends DinoDictionary
{
    public static void main(String[] args)
    {
        try
        {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter a sentence: ");
            String sentence = in.nextLine();
            sentence = sentence.replace(".", " ");

            String[] arr = sentence.split(" ");

            for (int i = 0; i < arr.length; i++)
            {
                if (isListCandidate(arr[i]))
                {
                    arr[i] = "[" + arr[i] + "]";
                }
            }

            for (int i = 0; i < arr.length; i++)
            {
                System.out.print(arr[i] + " ");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
