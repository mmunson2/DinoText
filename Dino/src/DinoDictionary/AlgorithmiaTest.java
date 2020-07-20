// Algorithmia.java
// Camden Brewster
// Testing Algorithmia

package DinoDictionary;

import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;
import kong.unirest.json.JSONArray;

import java.util.ArrayList;
import java.util.Scanner;

public class AlgorithmiaTest
{
    public static void main(String[] args)
    {
        String input = "";
        String formatted = "";
        try
        {
            Scanner in = new Scanner(System.in);
            AlgorithmiaClient client = Algorithmia.client("simBYi1g7jd2i+5to1GdOtx4I831");
            Algorithm algo = client.algo("PetiteProgrammer/AutoComplete/0.1.2");
            algo.setTimeout(300L, java.util.concurrent.TimeUnit.SECONDS);

            System.out.println("This program helps type a sentence, a period will finish the sentence.");
            System.out.print("Enter part of a sentence: ");
            input = in.nextLine();

            while (true)
            {
                if (input.charAt(input.length() - 1) == '.') break;
                formatted = "{\"sentence\": \"" + input + "\"}";
                AlgoResponse result = algo.pipeJson(formatted);
                System.out.println("\nSuggested Words: ");

                System.out.println(result.asJsonString());

                // Converting JSON to an ArrayList
                JSONArray arr = new JSONArray(result.asJsonString());
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < arr.length(); i++)
                {
                    list.add(arr.getJSONObject(i).getString("word"));
                }
                for (int i = 0; i < list.size(); i++)
                {
                    System.out.print("| " + list.get(i) + " ");
                }
                System.out.println("|");

                System.out.print("Continue The Sentence: ");
                System.out.print(input + " ");
                input += " " + in.nextLine();
            }

            String finish = "";
            if (input.charAt(input.length() - 2) == ' ')
            {
                finish = input.substring(0, input.length() - 2) + ".";
            }
            else
            {
                finish = input;
            }
            System.out.print("\nFinal Sentence: ");
            System.out.println(finish);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
