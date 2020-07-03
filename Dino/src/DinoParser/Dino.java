package DinoParser;

import DinoParser.Delimiter.Reference;
import DinoParser.List.List;


/*******************************************************************************
 * Dino
 *
 * @author Matthew Munson
 * Date: 6/20/2020
 * @version 0.2-alpha
 *
 * The Dino API allows for dynamic text to be quickly generated and returned
 * for display.
 *
 * //Todo: Allow user to set lowerBounds and upperBounds
 *
 ******************************************************************************/
public class Dino
{
    private String dialogue;
    private List[] lists;

    private double[] traitVals;
    private String[] traitNames;

    private double[] lowerBounds;
    private double[] upperBounds;

    private int[][] indices;
    private String[][] staticVars;

    /***************************************************************************
     * Dino
     *
     * Constructs a Dino Object capable of generating randomized dialogue.
     * This constructor handles all file reads and parsing, so call it during
     * a load sequence if possible.
     *
     * @param path Path to the dialogue file to be processed.
     *
     **************************************************************************/
    public Dino(String path)
    {
        DialogueParser parser = new DialogueParser(path);
        this.dialogue = parser.getDialogue();
        this.lists = parser.getListArray();
        this.staticVars = parser.getStaticVars();
        this.indices = parser.getIndices();

        this.traitNames = parser.getTraitNames();
        this.traitVals = new double[this.traitNames.length];

        for(int i = 0; i < traitVals.length; i++)
        {
            this.traitVals[i] = Double.NaN;
        }

        this.upperBounds = new double[this.traitVals.length];
        this.lowerBounds = new double[this.traitVals.length];

        for(int i = 0; i < this.traitVals.length; i++)
        {
            this.upperBounds[i] = 100;
            this.lowerBounds[i] = 0;
        }

    }

    /***************************************************************************
     * getDialogue
     *
     * Generates randomized dialogue and returns it ready for display.
     *
     * @return A string containing the randomized dialogue
     **************************************************************************/
    public String getDialogue()
    {
        String retVal = this.dialogue;

        int[] listIndices = getRefIndices(Reference.LIST);
        int[] staticIndices = getRefIndices(Reference.STATIC);


        for(int i = 0; i < listIndices.length; i += 2)
        {
            String dynamicText = getRandomLine(listIndices[i]);

            retVal = insertToIndex(listIndices[i + 1],
                    retVal, dynamicText);

            updateListIndices(listIndices,
                    listIndices[i + 1], dynamicText.length());

            updateStaticIndicies(staticIndices,
                    listIndices[i + 1],
                    dynamicText.length());
        }


        for(int i = 0; i < staticIndices.length; i += 2)
        {

            String text = staticVars[staticIndices[i]][1];

            retVal = insertToIndex(staticIndices[i + 1],
                    retVal,text);

            updateStaticIndicies(staticIndices,
                    staticIndices[i + 1],
                    text.length());
        }

        return retVal;
    }


    /***************************************************************************
     * setStaticVariable
     *
     * Sets a static variable within the dialogue to a given value. The name
     * of the static variable must be known to use this setter.
     *
     * @param name The name of the static variable
     * @param value The String to set the static variable to
     *
     * @return true if set. False if no matching variable name
     **************************************************************************/
    public boolean setStaticVariable(String name, String value)
    {
        for(int i = 0; i < staticVars.length; i++)
        {
            if(name.equals(this.staticVars[i][0]))
            {
                setStaticVariable(i,value);
                return true;
            }
        }

        return false;
    }

    /***************************************************************************
     * setStaticVariable
     *
     * Sets the static variable within the dialogue to a given value. This
     * method can be used without knowing the name of the static variable.
     *
     * @param index The index of the static variable in StaticVars
     * @param value The String to set the static variable to
     *
     * @return true if set. False if the index is out of bounds
     **************************************************************************/
    public boolean setStaticVariable(int index, String value)
    {
        if(index < 0 || index > this.staticVars.length)
        {
            return false;
        }
        else
        {
            this.staticVars[index][1] = value;
            return true;
        }
    }

    /***************************************************************************
     * getStaticVariableCount
     *
     * Gets the number of static variables.
     *
     * @return The number of static variables
     **************************************************************************/
    public int getStaticVariableCount()
    {
        return this.staticVars.length;
    }

    /***************************************************************************
     * getStaticVariableName
     *
     * Gets the name of a static variable given its index
     *
     * @param index The index of the static variable.
     *
     * @return The name of the static variable if in range. "OUT_OF_BOUNDS" if
     * the index is out of range.
     **************************************************************************/
    public String getStaticVariableName(int index)
    {
        if(index < 0 || index >= this.staticVars.length)
        {
            return "OUT_OF_BOUNDS";
        }
        else
        {
            return this.staticVars[index][0];
        }
    }

    /***************************************************************************
     * setTraitValue
     *
     * Finds a trait with a matching name and sets its value to what's given.
     *
     * @return true if successfully set, false otherwise.
     **************************************************************************/
    public boolean setTraitValue(String name, double value)
    {
        for(int i = 0; i < this.traitNames.length; i++)
        {
            if(this.traitNames[i].equals(name))
            {
                setAbsoluteTraitVal(i, value);
                updateTraits();
                return true;
            }
        }

        return false;
    }

    /***************************************************************************
     * setTraitValue
     *
     * Finds a trait with a matching index and sets its value to what's given.
     *
     * @return true if successfully set, false otherwise.
     **************************************************************************/
    public boolean setTraitValue(int index, double value)
    {
        if(index < 0 || index > this.traitVals.length)
        {
            return false;
        }
        else
        {
            setAbsoluteTraitVal(index, value);
            updateTraits();
            return true;
        }
    }

    /***************************************************************************
     * setTraitValues
     *
     * Sets all trait values at once - much more efficient!!
     *
     * @return true if successfully set, false otherwise.
     **************************************************************************/
    public boolean setTraitValues(double[] values)
    {
        if(values == null || values.length != this.traitVals.length)
        {
            return false;
        }
        else
        {
            for(int i = 0; i < this.traitVals.length; i++)
            {
                setAbsoluteTraitVal(i, values[i]);
            }

            updateTraits();
            return true;
        }
    }

    /***************************************************************************
     * getTraitName
     *
     * Gets the name of the trait at the given index
     *
     * @return The trait name, OUT_OF_BOUNDS for invalid index
     **************************************************************************/
    public String getTraitName(int index)
    {
        if(index < 0 || index >= this.staticVars.length)
        {
            return "OUT_OF_BOUNDS";
        }
        else
        {
            return this.traitNames[index];
        }
    }

    /***************************************************************************
     * getTraitCount
     *
     * gets the number of traits
     *
     * @return the number of traits in the dialogue
     **************************************************************************/
    public int getTraitCount()
    {
        return this.traitNames.length;
    }


    /***************************************************************************
     * updateTraits
     **************************************************************************/
    private void updateTraits()
    {
        for(List list : this.lists)
        {
            list.updateTraits(this.traitVals);
        }
    }

    /***************************************************************************
     * setAbsoluteTraitVal
     **************************************************************************/
    private void setAbsoluteTraitVal(int index, double newValue)
    {
        double range = this.upperBounds[index] - this.lowerBounds[index];

        this.traitVals[index] = newValue / range;
    }


    /***************************************************************************
     * updateListIndices
     **************************************************************************/
    private void updateListIndices(int[] indices, int insert, int length)
    {
        for(int i = 0; i < indices.length; i += 2)
        {
            if(indices[i + 1] > insert)
            {
                indices[i + 1] += length - 1;
            }
        }
    }

    /***************************************************************************
     * updateStaticIndices
     **************************************************************************/
    private void updateStaticIndicies(int[] indices, int insert, int length)
    {
        for(int i = 0; i < indices.length; i += 2)
        {
            if(indices[i + 1] > insert)
            {
                indices[i + 1] += length - 1;
            }
        }
    }

    /***************************************************************************
     * getRefIndices
     **************************************************************************/
    private int[] getRefIndices(Reference ref)
    {
        int[] instanceRef = this.indices[ref.ordinal()];

        int[] retVal = new int[instanceRef.length];

        System.arraycopy(instanceRef, 0,retVal,0,retVal.length);

        return retVal;
    }

    /***************************************************************************
     * insertToIndex
     **************************************************************************/
    private String insertToIndex(int index,
                                 String dialogue, String dynamicText)
    {
        String retVal = dialogue.substring(0,index);

        retVal += dynamicText;

        retVal += dialogue.substring(index + 1);

        return retVal;
    }

    /***************************************************************************
     * getRandomLine
     **************************************************************************/
    private String getRandomLine(int listNumber)
    {
        return this.lists[listNumber].getEntry();
    }

}
