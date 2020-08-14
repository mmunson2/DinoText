// DinoDictionary.java
// Camden Brewster
// Class that provides methods to interact with the WordsAPI

package DinoDictionary;

import DinoText_GUI.Util.DinoConfig;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class DinoDictionary
{
    public static void main(String[] args)
    {
        DinoDictionary.openSettings();
    }

    public static void openSettings()
    {
        JOptionPane.showConfirmDialog(null, new APIsettings().getPanel(), "API Settings", JOptionPane.DEFAULT_OPTION);
    }

    //************//
    // MAIN CALLS //
    //************//

    public static ArrayList<String> getTypeOf(String word)
    {
        return queryArrayList(word, "typeOf");
    }

    public static ArrayList<String> getHasTypes(String word)
    {
        return queryArrayList(word, "hasTypes");
    }

    public static ArrayList<String> getPartOf(String word)
    {
        return queryArrayList(word, "partOf");
    }

    public static ArrayList<String> getHasParts(String word)
    {
        return queryArrayList(word, "hasParts");
    }

    public static ArrayList<String> getInstanceOf(String word)
    {
        return queryArrayList(word, "instanceOf");
    }

    public static ArrayList<String> getHasInstances(String word)
    {
        return queryArrayList(word, "hasInstances");
    }

    public static ArrayList<String> getSimilarTo(String word)
    {
        return queryArrayList(word, "similarTo");
    }

    public static ArrayList<String> getAlso(String word)
    {
        return queryArrayList(word, "also");
    }

    public static ArrayList<String> getEntails(String word)
    {
        return queryArrayList(word, "entails");
    }

    public static ArrayList<String> getMemberOf(String word)
    {
        return queryArrayList(word, "memberOf");
    }

    public static ArrayList<String> getHasMembers(String word)
    {
        return queryArrayList(word, "hasMembers");
    }

    public static ArrayList<String> getSubstanceOf(String word)
    {
        return queryArrayList(word, "substanceOf");
    }

    public static ArrayList<String> getHasSubstances(String word)
    {
        return queryArrayList(word, "hasSubstances");
    }

    public static ArrayList<String> getInCategory(String word)
    {
        return queryArrayList(word, "inCategory");
    }

    public static ArrayList<String> getHasCategories(String word)
    {
        return queryArrayList(word, "hasCategories");
    }

    public static ArrayList<String> getUsageOf(String word)
    {
        return queryArrayList(word, "usageOf");
    }

    public static ArrayList<String> getHasUsages(String word)
    {
        return queryArrayList(word, "hasUsages");
    }

    public static ArrayList<String> getInRegion(String word)
    {
        return queryArrayList(word, "inRegion");
    }

    public static ArrayList<String> getRegionOf(String word)
    {
        return queryArrayList(word, "regionOf");
    }

    public static ArrayList<String> getPertainsTo(String word)
    {
        return queryArrayList(word, "pertainsTo");
    }

    public static ArrayList<String> getSynonyms(String word)
    {
        return queryArrayList(word, "synonyms");
    }

    public static ArrayList<String> getAntonyms(String word)
    {
        return queryArrayList(word, "antonyms");
    }

    public static ArrayList<String> getPartOfSpeech(String word)
    {
        return partOfSpeech(word);
    }

    public static boolean isListCandidate(String word) { return listCandidate(word); }

    //*******************//
    // PRIVATE FUNCTIONS //
    //*******************//

    // helper function to make the main calls easier to write
    private static ArrayList<String> queryArrayList(String word, String mode)
    {
        DinoConfig config = DinoConfig.loadConfig();
        if (!config.isAPI_enabled()) return testArray(word, mode);

        return toArrayList(query(word, mode, config.getAPI_key()), mode);
    }

    // formats and sends a request to the API
    private static HttpResponse<String> query(String word, String mode, String API_KEY)
    {
        HttpResponse<String> response = null;
        try
        {
            Unirest.config().enableCookieManagement(false);
            response = Unirest.get("https://wordsapiv1.p.rapidapi.com/words/" + word + "/" + mode)
                    .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                    .header("x-rapidapi-key", API_KEY)
                    .asString();
            Unirest.config().reset();
        }
        catch (Exception e)
        {
            System.out.println("*** ERROR: " + word);
            e.printStackTrace();
        }
        return response;
    }

    // converts the API response to an ArrayList
    private static ArrayList<String> toArrayList(HttpResponse<String> response, String mode)
    {
        ArrayList<String> list = new ArrayList<>();

        if (response.getBody().equals("{\"success\":false,\"message\":\"word not found\"}")) return list;

        try
        {
            JSONObject obj = new JSONObject(response.getBody());
            JSONArray arr = obj.getJSONArray(mode);
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i));
            }
        }
        catch (Exception e) {e.printStackTrace();}
        return list;
    }

    // special method to get the partOfSpeech part of a definition API call
    private static ArrayList<String> partOfSpeech(String word)
    {
        DinoConfig config = DinoConfig.loadConfig();
        if (!config.isAPI_enabled()) return testPartOfSpeech();

        HttpResponse<String> response = query(word, "definitions", config.getAPI_key());
        ArrayList<String> list = new ArrayList<>();

        if (response.getBody().equals("{\"success\":false,\"message\":\"word not found\"}")) return list;

        try
        {
            JSONObject obj = new JSONObject(response.getBody());
            JSONArray arr = obj.getJSONArray("definitions");
            for (int i = 0; i < arr.length(); i++)
            {
                if (arr.getJSONObject(i).isNull("partOfSpeech")) continue;
                String POS = arr.getJSONObject(i).getString("partOfSpeech");
                if (!list.contains(POS))
                {
                    list.add(POS);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("*** ERROR: " + word);
            e.printStackTrace();
        }
        return list;
    }

    // gets the parts of speech and checks for certain ones
    private static boolean listCandidate(String word)
    {
        ArrayList<String> results = partOfSpeech(word);

        if (results.size() == 0) return false;

        //if (results.contains("adjective") || results.contains("verb") || results.contains("adverb"))
        if (results.get(0).equals("adjective") ||
                results.get(0).equals("verb") ||
                results.get(0).equals("adverb"))
        {
            return true;
        }
        return false;
    }

    //*******************//
    // TESTING FUNCTIONS //
    //*******************//

    // prints an ArrayList like | word1 | word2 | word3 |
    public static void printList(ArrayList<String> list)
    {
        for (String s : list)
        {
            System.out.print("| " + s + " ");
        }
        System.out.println("|");
    }

    // returns a test array for most functions
    private static ArrayList<String> testArray(String word, String mode)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(mode);
        list.add(word);
        list.add("TEST_MODE");
        return list;
    }

    // returns a test array for parts of speech
    private static ArrayList<String> testPartOfSpeech()
    {
        ArrayList<String> list = new ArrayList<>();
        if (Math.random() > 0.5) list.add("noun");
        if (Math.random() > 0.5) list.add("adjective");
        if (Math.random() > 0.5) list.add("verb");
        if (Math.random() > 0.5) list.add("adverb");
        list.add("TEST_MODE");
        return list;
    }
}
