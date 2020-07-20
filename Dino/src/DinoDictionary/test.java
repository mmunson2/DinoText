// test.java
// Camden Brewster
// Testing DinoDictionary.java

package DinoDictionary;

public class test extends DinoDictionary
{
    public static void main(String[] args)
    {
        System.out.print("typeOf : truck -> ");
        printList(getTypeOf("truck"));
        System.out.println();

        System.out.print("hasTypes : red -> ");
        printList(getHasTypes("red"));
        System.out.println();

        System.out.print("partOf : muffler -> ");
        printList(getPartOf("muffler"));
        System.out.println();

        System.out.print("hasParts : car -> ");
        printList(getHasParts("car"));
        System.out.println();

        System.out.print("instanceOf : einstein -> ");
        printList(getInstanceOf("einstein"));
        System.out.println();

        System.out.print("hasInstances : president -> ");
        printList(getHasInstances("president"));
        System.out.println();

        System.out.print("similarTo : bloody -> ");
        printList(getSimilarTo("bloody"));
        System.out.println();

        System.out.print("also : bump -> ");
        printList(getAlso("bump"));
        System.out.println();

        System.out.print("entails : rub -> ");
        printList(getEntails("rub"));
        System.out.println();

        System.out.print("memberOf : star -> ");
        printList(getMemberOf("star"));
        System.out.println();

        System.out.print("hasMembers : constellation -> ");
        printList(getHasMembers("constellation"));
        System.out.println();

        System.out.print("substanceOf : water -> ");
        printList(getSubstanceOf("water"));
        System.out.println();

        System.out.print("hasSubstances : water -> ");
        printList(getHasSubstances("water"));
        System.out.println();

        System.out.print("inCategory : neutral -> ");
        printList(getInCategory("neutral"));
        System.out.println();

        System.out.print("hasCategories : math -> ");
        printList(getHasCategories("math"));
        System.out.println();

        System.out.print("usageOf : advil -> ");
        printList(getUsageOf("advil"));
        System.out.println();

        System.out.print("hasUsages : trade name -> ");
        printList(getHasUsages("trade name"));
        System.out.println();

        System.out.print("inRegion : chips -> ");
        printList(getInRegion("chips"));
        System.out.println();

        System.out.print("regionOf : america -> ");
        printList(getRegionOf("america"));
        System.out.println();

        System.out.print("pertainsTo : .22-caliber -> ");
        printList(getPertainsTo(".22-caliber"));
        System.out.println();

        System.out.print("synonyms : happy -> ");
        printList(getSynonyms("happy"));
        System.out.println();

        System.out.print("antonyms : happy -> ");
        printList(getAntonyms("happy"));
        System.out.println();

        System.out.print("partOfSpeech : quick -> ");
        printList(getPartOfSpeech("quick"));
        System.out.println();
    }
}
