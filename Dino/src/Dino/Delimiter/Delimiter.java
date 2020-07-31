package Dino.Delimiter;

import java.util.ArrayList;

/*******************************************************************************
 * Delimiter
 *
 * This class contains static methods to assist in decoding references.
 * All reference delimiters are stored here to make changing the file format
 * easy.
 *
 * _____________________________________________________________________________
 * Example reference format (as of 0.25-alpha)
 *
 * \L[NAME] : A list reference with name "NAME"
 * \S[player] : A static reference with name "player"
 *
 * _____________________________________________________________________________
 * When to set the STRICT variable
 *
 * • When STRICT is true, a verification fail in a method such as getName or
 * getReference will print the stack trace and crash. The verify() method will
 * NOT crash, it simply returns false.
 * • When STRICT is false, a verification fail will return null for Strings,
 * or Reference.NONE for Reference enums.
 * • In general, call the verify method on your own prior to the getName or
 * getReference to make sure the Reference is correctly formatted!
 * _____________________________________________________________________________
 * How to add a new Delimiter Type:
 *
 * One of the objectives of this class was to make the creation of new
 * delimiters a bit easier. It's not quite perfect, but this is how to start:
 *
 * • Add the delimiter type to the Reference enum. Keep NONE at the very end
 * • Create a static final String here with the reference identifier
 * • Add the String you created to the REFERENCES String array
 * • Go find any switch statements using References and add your own logic!
 * _____________________________________________________________________________
 *
 *
 * @author Matthew Munson
 * Date: 6/25/2020
 * @version 0.25-alpha
 ******************************************************************************/
public class Delimiter
{
    //This is the String that begins an escape sequence
    public static final String ESCAPE_STRING = "\\";

    //These are the possible reference identifiers
    public static final String LIST = "L";
    public static final String STATIC = "S";

    //Add any new references identifiers to the array initializer
    static final String[] REFERENCES = {LIST, STATIC};

    //The Strings before and after the reference name can be customized
    public static final String START_NAME = "[";
    public static final String END_NAME = "]";

    //Whether or not the system should exit on a in-method verification fail
    private static final boolean STRICT = true;


    /***************************************************************************
     * getName
     *
     * This method expects that the first character(s) of the input to match
     * the ESCAPE_STRING. Most implementations use indexOf() to find the next
     * instance of the escape character, cut the String down to the beginning
     * of the reference, and pass it into this method.
     *
     * @param input A dialogue String that may contain a reference
     * @return The name of the reference. null if STRICT is false and
     *         verification fails.
     *
     * Note: This method assumes you want to verify that the input is
     * well-formatted. If this verification fails, behavior depends on the
     * STRICT static variable. If STRICT is true, the method will print stack
     * trace and exit. If STRICT is false, the method will return null.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static String getName(String input)
    {
        return getName(
                input, true);
    }

    /***************************************************************************
     * getName
     *
     * This method expects that the first character(s) of the input to match
     * the ESCAPE_STRING. Most implementations use indexOf() to find the next
     * instance of the escape character, cut the String down to the beginning
     * of the reference, and pass it into this method.
     *
     * Overload of getName allowing verify to be toggled.
     *
     * @param input A dialogue String that may contain a reference
     * @param verify Whether or not to check if the reference is well-formatted
     * @return The name of the reference. null if STRICT is false and
     *         verification fails.
     *
     * Note: If STRICT is true and the verification fails, the method
     * will print stack trace and exit.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static String getName(String input, boolean verify)
    {
        if(verify)
        {
            boolean passed = verify(input);

            if(!passed && STRICT)
            {
                Exception e = new Exception(
                        "Verification fail in getName");
                e.printStackTrace();

                System.exit(-1);
            }
            else if(!passed)
            {
                return null;
            }
        }

        Reference ref = getReference(input, false);

        //Start index is the character after the START_NAME String
        int startIndex = ESCAPE_STRING.length() +
                REFERENCES[ref.ordinal()].length()
                + START_NAME.length();

        //End index is wherever we find the END_NAME String
        // : If you crash here, it's because verify is set to false and we can't
        // : find an END_STRING. Use verify!
        int endIndex = startIndex +
                input.substring(startIndex).indexOf(END_NAME);

        return input.substring(startIndex, endIndex);
    }

    /***************************************************************************
     * getCombinedDelimiterLength
     *
     * This method gets you the length of all of our delimiter characters. It's
     * used when skipping past a reference in a larger string, since the
     * length of the reference is its name + all the delimiter characters.
     *
     * Reference Strings ("L", "S") should really be single letters, but just
     * in case some weirdo decides to make them larger, this method supports
     * variable reference length. Yay.
     *
     * @param ref The reference type
     * @return The sum of the delimiter lengths.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static int getCombinedDelimiterLength(Reference ref)
    {
        return ESCAPE_STRING.length() + REFERENCES[ref.ordinal()].length()
                + START_NAME.length() + END_NAME.length();
    }

    /***************************************************************************
     * getReference
     *
     * This method expects that the first character(s) of the input to match
     * the ESCAPE_STRING. Most implementations use indexOf() to find the next
     * instance of the escape character, cut the String down to the beginning
     * of the reference, and pass it into this method.
     *
     * Gets a Reference in Enum form given a String.
     *
     * @param input A dialogue String that may contain a reference
     * @return The corresponding Reference enum. NONE if STRICT is false and
     *         verification fails.
     *
     * Note: This method assumes you want to verify that the input is
     * well-formatted. If this verification fails, behavior depends on the
     * STRICT static variable. If STRICT is true, the method will throw an
     * exception and exit. If STRICT is false, the method will return null.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static Reference getReference(String input)
    {
        return getReference(input, true);
    }

    /***************************************************************************
     * getReference
     *
     * This method expects that the first character(s) of the input to match
     * the ESCAPE_STRING. Most implementations use indexOf() to find the next
     * instance of the escape character, cut the String down to the beginning
     * of the reference, and pass it into this method.
     *
     * Gets a Reference in Enum form given a String. This overload allows verify
     * to be toggled.
     *
     * @param input A dialogue String that may contain a reference
     * @param verify Whether or not to check if the reference is well-formatted
     * @return The corresponding Reference enum. NONE if STRICT is false and
     *         verification fails or no match is found.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static Reference getReference(String input, boolean verify)
    {
        if(verify)
        {
            boolean passed = verify(input);

            if(!passed && STRICT)
            {
                Exception e = new Exception(
                        "Verification fail in getReference");
                e.printStackTrace();

                System.exit(-1);
            }
            else if(!passed)
            {
                return Reference.NONE;
            }
        }

        //Index starts after the ESCAPE_STRING
        int index = ESCAPE_STRING.length();

        for(int i = 0; i < REFERENCES.length; i++)
        {
            if(input.substring(index).length() < REFERENCES[i].length())
            {
                continue;
            }

            String referenceString = input.substring(index,
                    index + REFERENCES[i].length());

            if(referenceString.equals(REFERENCES[i]))
            {
                return Reference.values()[i];
            }
        }

        return Reference.NONE;
    }

    /***************************************************************************
     * referenceCounter
     *
     * Counts how many references are present in a piece of dialogue. Unlike
     * the other methods in this class, the segment String doesn't need to be
     * formatted in any particular way. Each potential reference is verified.
     *
     * @param segment The String in which References will be counted
     * @param countDuplicates Whether or not duplicates should increase the
     *                        count
     * @return An int array representing the count of each Reference type. The
     * indices of the array correspond to the REFERENCES array, and can be
     * safely retrieved using Reference.ordinal(). Note that there is no index
     * allotted for NONE as this isn't useful.
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static int[] referenceCounter(String segment,
                                         boolean countDuplicates)
    {
        int[] counts = new int[REFERENCES.length];

        if(countDuplicates)
        {
            return referenceCounter(segment, counts);
        }
        else
        {
            ArrayList<ArrayList<String>> names = new ArrayList<>();

            for(int i = 0; i < counts.length; i++)
            {
                names.add(new ArrayList<>());
            }

            return referenceCounter(segment, counts, names);
        }
    }

    /***************************************************************************
     * getString
     *
     * Intended for debug purposes. Retrieves the name of a reference, which
     * is stored in the REFERENCES String array. Note that NONE is not stored
     * in this array, so this method will return "NO_REFERENCE" if NONE is
     * entered.
     *
     * @param reference The reference for which a corresponding String will be
     *                  returned
     * @return A string representing the name of the reference
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static String getString(Reference reference)
    {
        if(reference == Reference.NONE)
        {
            return "NO_REFERENCE";
        }
        else
        {
            return REFERENCES[reference.ordinal()];
        }
    }

    /***************************************************************************
     * verify
     *
     * This method expects that the first character(s) of the input to match
     * the ESCAPE_STRING. Most implementations use indexOf() to find the next
     * instance of the escape character, cut the String down to the beginning
     * of the reference, and pass it into this method.
     *
     * Check if a String contains a correctly formatted reference
     *
     * @param input A dialogue String that may contain a reference
     * @return true if the reference is correctly formatted. False if not.
     *
     *__________________________________________________________________________
     * Expected Format:
     *
     * (v0.25-alpha example)
     * \L[NAME]
     *
     * In Variable Form:
     * ESCAPE_STRING, REFERENCES[any index], START_NAME, "NAME", END_NAME
     *__________________________________________________________________________
     * Summary of Verification Checks:
     *
     * 1) Checks that the input starts with the ESCAPE_STRING
     *
     * 2) Checks that the input contains a valid reference String
     *
     * 3) Checks that the reference is followed by the START_NAME String
     *
     * 4) Checks that the name is at least 1 character long
     *
     * 5) Checks that the name is followed by the START_NAME String
     *
     * @since 0.25-alpha
     **************************************************************************/
    public static boolean verify(String input)
    {
        int index = 0;
        int escapeLength = ESCAPE_STRING.length();

        //Check that the escape String is the same as the delimiter
        if(input.length() < escapeLength ||
                !input.substring(index,escapeLength).equals(ESCAPE_STRING))
        {
            return false;
        }

        index += escapeLength;

        //Check that the reference matches one of the valid reference Strings
        boolean matchFound = false;
        int referenceLength = 0;
        for(int i = 0; i < REFERENCES.length; i++)
        {
            if(input.substring(index).length() < REFERENCES[i].length())
            {
                continue;
            }

            String referenceString = input.substring(index,
                    index + REFERENCES[i].length());

            if(referenceString.equals(REFERENCES[i]))
            {
                matchFound = true;
                referenceLength = REFERENCES[i].length();
                break;
            }
        }

        if(!matchFound)
            return false;

        index += referenceLength;

        //Check that the reference contains the START_NAME String
        if(input.substring(index).length() < START_NAME.length()
                ||!input.startsWith(START_NAME, index))
        {
            return false;
        }

        index += START_NAME.length();

        String remainder = input.substring(index);
        int endNameIndex = remainder.indexOf(END_NAME);

        //Check if there's no end name String
        if(endNameIndex == -1)
            return false;
            //Check if there's no name String
        else if(endNameIndex == 0)
            return false;

        return true;
    }


    /***************************************************************************
     * referenceCounter
     *
     * This overload of referenceCounter does not track duplicates.
     *
     * Recursive implementation. Cuts down the segment String until there's no
     * ESCAPE_STRINGs remaining. Uses verify to determine is an escape String
     * is actually a valid reference.
     *
     * @param segment The remainder of the segment where references are to be
     *                counted
     * @param counts The array of counts to be returned.
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static int[] referenceCounter(String segment, int[] counts)
    {
        int escapeIndex = segment.indexOf(ESCAPE_STRING);

        //Base case, no additional escape characters.
        if(escapeIndex == -1)
        {
            return counts;
        }

        String reducedString = segment.substring(escapeIndex);

        boolean verified = Delimiter.verify(reducedString);

        if(verified)
        {
            Reference nextRef = Delimiter.getReference(
                    reducedString, false);

            counts[nextRef.ordinal()]++;
        }

        segment = segment.substring(escapeIndex + 1);

        return referenceCounter(segment, counts);
    }

    /***************************************************************************
     * referenceCounter
     *
     * This overload of referenceCounter tracks duplicates.
     *
     * Similar recursive implementation to the other overload. In this case
     * an ArrayList of names is passed down in addition to the segment String
     * and the count int array. When a valid reference is found, its name is
     * put into the names ArrayList. If a future reference's name matches the
     * ArrayList, count isn't updated.
     *
     * //Todo: This implementation could be simplified a bit
     * Because the counts array is either 0 or 1, we shouldn't need the names
     * array. If the count is greater than zero, you aren't allowed to update
     * it!
     *
     * @since 0.25-alpha
     **************************************************************************/
    private static int[] referenceCounter(String segment,
                                          int[] counts,
                                          ArrayList<ArrayList<String>> names)
    {
        int escapeIndex = segment.indexOf(ESCAPE_STRING);

        //Base case, no additional escape characters.
        if(escapeIndex == -1)
        {
            return counts;
        }

        String reducedString = segment.substring(escapeIndex);

        boolean verified = Delimiter.verify(reducedString);

        if(verified)
        {
            String name = Delimiter.getName(reducedString, false);

            Reference nextRef = Delimiter.getReference(
                    reducedString, false);

            ArrayList<String> list = names.get(nextRef.ordinal());

            if(!list.contains(name))
            {
                list.add(name);
                counts[nextRef.ordinal()]++;
            }
        }

        segment = segment.substring(escapeIndex + 1);

        if(verified)
        {
            return referenceCounter(segment, counts, names);
        }
        else
        {
            return referenceCounter(segment, counts, names);
        }
    }
}
