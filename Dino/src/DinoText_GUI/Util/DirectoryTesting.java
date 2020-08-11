package DinoText_GUI.Util;

import Dino.Dino;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class DirectoryTesting
{
    public static void main(String[] args)
    {
        DinoWriter writer = new DinoWriter();

        DinoList list1 = new DinoList("list1");
        list1.add("This is a ");

        File testDirectory = new File("/Users/matthewmunson/Documents/GitHub/DinoText/Resources/DirectoryTests/1/lists");
        System.out.println("isDirectory: " + testDirectory.isDirectory());

        list1.setDirectory(testDirectory);

        Set<DinoList> set = new LinkedHashSet<>();
        set.add(list1);

        Set<String> staticVars = new LinkedHashSet<>();


        writer.writeDialogueToFile("/Users/matthewmunson/Documents/GitHub/DinoText/Resources/DirectoryTests/testDino", "\\L[list1] test", set, staticVars);

    }


}
