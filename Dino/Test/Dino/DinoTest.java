package Dino;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DinoTest {

    private Dino testFile = null;
    private static final String expected = "This is a test\n";

    @BeforeEach
    void setUp() {

    }

    @Test
    void T1_DialogueOnly()
    {
        testFile = new Dino("Test_Resources/T1_dialogueOnly.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue only test - output does not match");
    }

    @Test
    void T2_listOnly()
    {
        testFile = new Dino("Test_Resources/T2_listOnly.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "List Only Test - output does not match");
    }

    @Test
    void T3_dialogue_List()
    {
        testFile = new Dino("Test_Resources/T3_dialogue&List.txt");
        String retVal = testFile.getDialogue();
        assertEquals(expected, retVal,
                "Dialogue, list Test - output does not match");
    }



}