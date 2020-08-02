package Dino;

import java.io.File;

public class FileTypes
{
    public static final String LIST_EXTENSION = ".dlist";
    public static final String DIALOGUE_EXTENSION = ".dino";

    public static boolean isList(File file)
    {
        System.err.println("Not yet implemented :/");
        return false;
    }

    public static boolean isDialogue(File file)
    {
        System.err.println("Not yet implemented :/");
        return false;
    }

    public static boolean hasDialogueExtension(String fileName)
    {
        return fileName.endsWith(DIALOGUE_EXTENSION);
    }

    public static boolean hasListExtension(String fileName)
    {
        return fileName.endsWith(LIST_EXTENSION);
    }

    public static String trimDialogueExtension(String fileName)
    {
        if(hasDialogueExtension(fileName))
        {
            fileName =  fileName.substring(0,
                    fileName.length() - DIALOGUE_EXTENSION.length());
        }

        return fileName;
    }

    public static String trimListExtension(String fileName)
    {
        if(hasListExtension(fileName))
        {
            fileName = fileName.substring(0,
                    fileName.length() - LIST_EXTENSION.length());
        }

        return fileName;
    }







}
