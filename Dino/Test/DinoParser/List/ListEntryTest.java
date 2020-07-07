package DinoParser.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListEntryTest
{
    Trait zeroTo1;
    Trait zeroTo100;
    Trait negativeToPositive;
    Trait allNegative;

    ListEntry noTraits;
    ListEntry oneTrait;
    ListEntry twoTraits;
    ListEntry fourTraits_allSame;
    ListEntry fourTraits_allDiff;

    private static final String tName1 = "zeroTo1";
    private static final String tName2 = "zeroTo100";
    private static final String tName3 = "negativeToPositive";
    private static final String tName4 = "allNegative";

    private static final double t1In = -1;
    private static final double t1Ac = 0.1;
    private static final double t2In = -1;
    private static final double t2Ac = 10;
    private static final double t3In = 100;
    private static final double t3Ac = 0;
    private static final double t4In = 10;
    private static final double t4Ac = -50;

    private static final String[] allTraits1 =
            {tName1, tName2, tName3, tName4};
    private static final String[] allTraits2 =
            {tName4, tName3, tName2, tName1};
    private static final String[] allTraits3 =
            {"foo", "bar", tName2, tName3, "foo", tName4, tName1, "bar"};

    private static final double[] traitVals1_inactive =
        {t1In,t2In,t3In, t4In};
    private static final double[] traitVals1_1Active =
            {t1Ac,t2In,t3In, t4In};
    private static final double[] traitVals1_2Active =
            {t1In,t2Ac,t3In, t4In};
    private static final double[] traitVals1_3Active =
            {t1In,t2In,t3Ac, t4In};
    private static final double[] traitVals1_4Active =
            {t1In,t2In,t3In, t4Ac};
    private static final double[] traitVals1_1u2Active =
            {t1Ac,t2Ac,t3In, t4In};
    private static final double[] traitVals1_2u4Active =
            {t1In,t2In,t3Ac, t4Ac};
    private static final double[] traitVals1_allActive =
            {t1Ac,t2Ac,t3Ac, t4Ac};

    private static final double[] traitVals2_inactive =
            {t4In,t3In,t2In, t1In};
    private static final double[] traitVals2_1Active =
            {t4Ac,t3In,t2In, t1In};
    private static final double[] traitVals2_2Active =
            {t4In,t3Ac,t2In, t1In};
    private static final double[] traitVals2_3Active =
            {t4In,t3In,t2Ac, t1In};
    private static final double[] traitVals2_4Active =
            {t4In,t3In,t2In, t1Ac};
    private static final double[] traitVals2_1u2Active =
            {t4Ac,t3Ac,t2In, t1In};
    private static final double[] traitVals2_2u4Active =
            {t4In,t3In,t2Ac, t1Ac};
    private static final double[] traitVals2_allActive =
            {t4Ac,t3Ac,t2Ac, t1Ac};

    private static final double[] traitVals3_inactive =
            {0, 1, t2In,t3In,1,t4In, t1In,0};
    private static final double[] traitVals3_1Active =
            {0, 1, t2Ac,t3In,1,t4In, t1In,0};
    private static final double[] traitVals3_2Active =
            {0, 1, t2In,t3Ac,1,t4In, t1In,0};
    private static final double[] traitVals3_3Active =
            {0, 1, t2In,t3In,1,t4Ac, t1In,0};
    private static final double[] traitVals3_4Active =
            {0, 1, t2In,t3In,1,t4In, t1Ac,0};
    private static final double[] traitVals3_1u2Active =
            {0, 1, t2Ac,t3Ac,1,t4In, t1In,0};
    private static final double[] traitVals3_2u4Active =
            {0, 1, t2In,t3In,1,t4Ac, t1Ac,0};
    private static final double[] traitVals3_allActive =
            {0, 1, t2Ac,t3Ac,1,t4Ac, t1Ac,0};


    private static final String entry1 = "one \n one";
    private static final String entry2 = "two \n two";
    private static final String entry3 = "three \n three";
    private static final String entry4 = "four \n four";
    private static final String entry5 = "five \n five";

    //Trait probabilities
    private static final double p0 = 0;
    private static final double p1 = 0.1;
    private static final double p2 = 1.0;
    private static final double p3 = 10;
    private static final double p4 = 100;

    //Base probabilities
    private static final double b0 = 0;
    private static final double b1 = 100;
    private static final double b2 = 10;
    private static final double b3 = 1;
    private static final double b4 = 0.1;

    //The default probability when a trait isn't active
    private static final double inactive = 0;

    @BeforeEach
    void setup()
    {
        zeroTo1 = new Trait(tName1,
                0, 1, p1);
        zeroTo100 = new Trait(tName2,
                0, 100, p2);
        negativeToPositive = new Trait(tName3,
                -10, 10, p3);
        allNegative = new Trait(tName4,
                -100, 0, p4);

        Trait[] one = {zeroTo1};
        Trait[] two = {zeroTo100, negativeToPositive};
        Trait[] same = {allNegative, allNegative, allNegative, allNegative};
        Trait[] diff = {zeroTo1, zeroTo100, negativeToPositive, allNegative};

        noTraits = new ListEntry(entry1, b1);

        oneTrait = new ListEntry(entry2, b2, one);

        twoTraits = new ListEntry(entry3, b3);
        twoTraits.setTraits(two);

        fourTraits_allSame = new ListEntry(entry4, b4, same);

        fourTraits_allDiff = new ListEntry(entry5, b0);
        fourTraits_allDiff.setTraits(diff);
    }

    //Brute force check, no probability verification, just looking for crashes
    void initializeAll(ListEntry entry)
    {
        entry.initializeTraits(allTraits1);

        entry.getUpdatedProbability(traitVals1_inactive);
        entry.getUpdatedProbability(traitVals1_1Active);
        entry.getUpdatedProbability(traitVals1_2Active);
        entry.getUpdatedProbability(traitVals1_3Active);
        entry.getUpdatedProbability(traitVals1_4Active);
        entry.getUpdatedProbability(traitVals1_1u2Active);
        entry.getUpdatedProbability(traitVals1_2u4Active);
        entry.getUpdatedProbability(traitVals1_allActive);

        entry.initializeTraits(allTraits2);

        entry.getUpdatedProbability(traitVals2_inactive);
        entry.getUpdatedProbability(traitVals2_1Active);
        entry.getUpdatedProbability(traitVals2_2Active);
        entry.getUpdatedProbability(traitVals2_3Active);
        entry.getUpdatedProbability(traitVals2_4Active);
        entry.getUpdatedProbability(traitVals2_1u2Active);
        entry.getUpdatedProbability(traitVals2_2u4Active);
        entry.getUpdatedProbability(traitVals2_allActive);

        entry.initializeTraits(allTraits3);

        entry.getUpdatedProbability(traitVals3_inactive);
        entry.getUpdatedProbability(traitVals3_1Active);
        entry.getUpdatedProbability(traitVals3_2Active);
        entry.getUpdatedProbability(traitVals3_3Active);
        entry.getUpdatedProbability(traitVals3_4Active);
        entry.getUpdatedProbability(traitVals3_1u2Active);
        entry.getUpdatedProbability(traitVals3_2u4Active);
        entry.getUpdatedProbability(traitVals3_allActive);
    }

    @Test
    void noTraitsInitAll()
    {
        initializeAll(noTraits);
    }

    @Test
    void oneTraitInitAll()
    {
        initializeAll(oneTrait);
    }

    @Test
    void twoTraitsInitAll()
    {
        initializeAll(twoTraits);
    }

    @Test
    void fourTraits_allSameInitAll()
    {
        initializeAll(fourTraits_allSame);
    }

    @Test
    void fourTraits_allDiffInitAll()
    {
        initializeAll(fourTraits_allDiff);
    }








}