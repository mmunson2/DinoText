package DinoParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*******************************************************************************
 * Test Cases:
 *
 * - No list references
 * - No dialogue, just a list reference
 * - dialogue then list reference
 * - list reference then dialogue
 * - dialogue, list reference, dialogue
 * - Two list references pointing to the same list
 * - Two list references pointing to different lists
 ******************************************************************************/
class DinoTest_Lists {

    private Dino testFile = null;
    private static final String expected = "This is a test\n";

    @BeforeEach
    void setUp() {

    }

    @Test
    void T1_DialogueOnly()
    {
        testFile = new Dino("Test_Resources/List_Tests/T1_dialogueOnly.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue only test - output does not match");
    }

    @Test
    void T2_listOnly()
    {
        testFile = new Dino("Test_Resources/List_Tests/T2_listOnly.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "List Only Test - output does not match");
    }

    @Test
    void T3_dialogue_List()
    {
        testFile = new Dino("Test_Resources/List_Tests/T3_dialogue&List.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, list Test - output does not match");
    }

    @Test
    void T4_list_Dialogue()
    {
        testFile = new Dino("Test_Resources/List_Tests/T4_list&Dialogue.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "List, dialogue - output does not match");
    }

    @Test
    void T5_dialogue_List_Dialogue()
    {
        testFile = new Dino("Test_Resources/List_Tests/T5_dialogue&List&Dialogue.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, list, dialogue - output does not match");
    }

    //Note that expected changes here
    @Test
    void T6_2ListsSameRef()
    {
        testFile = new Dino("Test_Resources/List_Tests/T6_2ListsSameRef.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected + expected, retVal,
                "Two lists same reference - output does not match");
    }

    @Test
    void T7_2Lists2Ref()
    {
        testFile = new Dino("Test_Resources/List_Tests/T7_2Lists2Ref.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Two lists different references - output " +
                        "does not match");
    }

    @Test
    void T8_2ListsNoSpace()
    {
        testFile = new Dino("Test_Resources/List_Tests/T8_2ListsNoSpace.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Two lists no spaces - output " +
                        "does not match");
    }
}