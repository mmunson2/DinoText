package DinoText_GUI.Util;

import java.io.File;

public class FileTests
{
    public static void main(String[] args)
    {
        File list = new File("/Users/matthewmunson/Documents/GitHub/DinoText/Resources/Information.dlist");
        File dino = new File("/Users/matthewmunson/Documents/GitHub/DinoText/Resources/0.7_Tests/Town_Interaction.dino");

        File dinoDirectory = dino.getParentFile();

        System.out.println(dinoDirectory);

        list = new File(dinoDirectory + File.separator + list.getName());

        System.out.println(list);

    }


}
