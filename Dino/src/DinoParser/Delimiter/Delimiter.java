package DinoParser.Delimiter;



import java.util.ArrayList;

//Todo: Make an exception or String returning version of verify for user input
/*******************************************************************************
 * Delimiter
 *
 * @author Matthew Munson
 * Date: 6/25/2020
 * @version 0.2-alpha
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
    static final boolean STRICT = true;


    /***************************************************************************
     * getName
     **************************************************************************/
    public static String getName(String input)
    {
        return getName(input, true);
    }

    /***************************************************************************
     * getName
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
        int value = ref.ordinal();

        int startIndex = ESCAPE_STRING.length() + REFERENCES[ref.ordinal()].length()
                + START_NAME.length();

        int endIndex = startIndex + input.substring(startIndex).indexOf(END_NAME);

        return input.substring(startIndex, endIndex);
    }

    /***************************************************************************
     * getCombinedDelimiterLength
     **************************************************************************/
    public static int getCombinedDelimiterLength(Reference ref)
    {
        return ESCAPE_STRING.length() + REFERENCES[ref.ordinal()].length()
                + START_NAME.length() + END_NAME.length();
    }

    /***************************************************************************
     * getReference
     **************************************************************************/
    public static Reference getReference(String input)
    {
        return getReference(input, true);
    }

    /***************************************************************************
     * getReference
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
     **************************************************************************/
    public static int[] referenceCounter(String segment, boolean countDuplicates)
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
     * referenceCounter
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

        if(verified)
        {
            return referenceCounter(segment, counts);
        }
        else
        {
            return referenceCounter(segment, counts);
        }
    }

    /***************************************************************************
     * referenceCounter
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

    /***************************************************************************
     * getString
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

        if(input.substring(index).length() < START_NAME.length()
            ||!input.substring(index,index + START_NAME.length()).equals(START_NAME))
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

}
