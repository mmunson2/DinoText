// WordSuggest.java
// Camden Brewster
// Checks a sentence for certain word types that can be changed

package DinoDictionary;

import java.util.ArrayList;
import java.util.Scanner;

public class WordSuggest extends DinoDictionary
{
    public static String[] suggestWord(String sentence)
    {
        try
        {
            String[] arr = sentence.split(" ");
            ArrayList<String> candidates = new ArrayList<String>();
            String result = "";

            for (int i = 0; i < arr.length; i++)
            {
                if (isListCandidate(arr[i]))
                {
                    candidates.add(arr[i]);
                    arr[i] = "[" + arr[i] + "]";
                }
            }

            for (int i = 0; i < arr.length; i++)
            {
                result += arr[i] + " ";
            }

            candidates.add(0, result);
            return candidates.toArray(new String[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
