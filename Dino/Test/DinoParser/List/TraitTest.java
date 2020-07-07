package DinoParser.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TraitTest
{
    Trait zeroTo1;
    Trait zeroTo100;
    Trait negativeToPositive;
    Trait allNegative;

    private static final String name1 = "zeroTo1";
    private static final String name2 = "zeroTo100";
    private static final String name3 = "negativeToPositive";
    private static final String name4 = "allNegative";

    private static final double p1 = 0.1;
    private static final double p2 = 1.0;
    private static final double p3 = 10;
    private static final double p4 = 100;

    //The default probability when a trait isn't active
    private static final double inactive = 0;

    @BeforeEach
    void setup()
    {
        zeroTo1 = new Trait(name1,
                0, 1, p1);
        zeroTo100 = new Trait(name2,
                0, 100, p2);
        negativeToPositive = new Trait(name3,
                -10, 10, p3);
        allNegative = new Trait(name4,
                -100, 0, p4);
    }

    @Test
    void getNameTest()
    {
        assertEquals(name1, zeroTo1.getName());
        assertEquals(name2, zeroTo100.getName());
        assertEquals(name3, negativeToPositive.getName());
        assertEquals(name4, allNegative.getName());
    }

    @Test
    void zeroTo1Test()
    {
        Trait testTrait = zeroTo1;
        double expectedP = p1;

        double probability = testTrait.getProbability(-1);
        assertEquals(inactive,probability);

        probability = testTrait.getProbability(0);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(0.5);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(1.0);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(2.0);
        assertEquals(inactive, probability);
    }

    @Test
    void zeroTo100Test()
    {
        Trait testTrait = zeroTo100;
        double expectedP = p2;

        double probability = testTrait.getProbability(-1);
        assertEquals(inactive,probability);

        probability = testTrait.getProbability(0);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(46.7);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(100);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(102);
        assertEquals(inactive, probability);
    }

    @Test
    void negativeToPositiveTest()
    {
        Trait testTrait = negativeToPositive;
        double expectedP = p3;
        //Range is -10 to 10

        double probability = testTrait.getProbability(-11);
        assertEquals(inactive,probability);

        probability = testTrait.getProbability(-10);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(0);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(10);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(11);
        assertEquals(inactive, probability);
    }

    @Test
    void allNegativeTest()
    {
        Trait testTrait = allNegative;
        double expectedP = p4;
        //Range is -100 to 0

        double probability = testTrait.getProbability(-101);
        assertEquals(inactive,probability);

        probability = testTrait.getProbability(-100);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(-50);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(0);
        assertEquals(expectedP, probability);

        probability = testTrait.getProbability(1);
        assertEquals(inactive, probability);
    }





}