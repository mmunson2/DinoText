package Dino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DinoTest_Static
{
    private Dino testFile = null;
    private static final String expected = "This is a test\n";


    @Test
    void T1_StaticOnly()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T1_staticOnly.txt");
        boolean result = testFile.setStaticVariable("T1_Static1", "This is a test");

        assertTrue(result, "Static only test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Static only test - output does not match");
    }

    @Test
    void T2_dialogue_Static()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T2_dialogue&Static.txt");
        boolean result = testFile.setStaticVariable("T2_Static1", "This");

        assertTrue(result, "Dialogue, Static test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, Static test - output does not match");
    }

    @Test
    void T3_static_Dialogue()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T3_static&Dialogue.txt");
        boolean result = testFile.setStaticVariable("T3_Static1", "test");

        assertTrue(result, "Static, Dialogue test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Static, Dialogue test - output does not match");
    }

    @Test
    void T4_dialogue_Static_Dialogue()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T4_dialogue&Static&Dialogue.txt");
        boolean result = testFile.setStaticVariable("T4_Static1", "a");

        assertTrue(result, "Dialogue, Static, Dialogue test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, Static, Dialogue test - output does not match");
    }

    //Note modified expected
    @Test
    void T5_2StaticSameRef()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T5_2StaticSameRef.txt");
        boolean result = testFile.setStaticVariable("T5_Static1", "This is a test");

        assertTrue(result, "2StaticSameRef - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected + expected, retVal,
                "2StaticSameRef - output does not match");
    }

    @Test
    void T6_2Static2Ref()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T6_2Static2Ref.txt");
        boolean result1 = testFile.setStaticVariable("T6_Static1", "This is");
        boolean result2 = testFile.setStaticVariable("T6_Static2", "a test");

        assertTrue(result1, "Dialogue, Static, Dialogue test - static set failed");
        assertTrue(result2, "Dialogue, Static, Dialogue test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, Static, Dialogue test - output does not match");
    }

    @Test
    void T7_2StaticNoSpace()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T7_2StaticNoSpace.txt");
        boolean result1 = testFile.setStaticVariable("T7_Static1", "This is");
        boolean result2 = testFile.setStaticVariable("T7_Static2", " a test");

        assertTrue(result1, "Dialogue, Static, Dialogue test - static set failed");
        assertTrue(result2, "Dialogue, Static, Dialogue test - static set failed");

        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, Static, Dialogue test - output does not match");
    }

    @Test
    void T8_static_List()
    {
        testFile = new Dino("Test_Resources/Static_Tests/T8_static&List.txt");
        boolean result1 = testFile.setStaticVariable("T8_Static1", "This is");

        assertTrue(result1, "Dialogue, Static, Dialogue test - static set failed");


        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, Static, Dialogue test - output does not match");
    }


}
