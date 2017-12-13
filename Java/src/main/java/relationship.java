package main.java;

/**
 * Relationship class that ties one member to another. There is a relationship from member A to member B. Once this is
 * determined, it will be stored in the Storage.java in a collection separate from the members. A relationship will
 * also have a field that says whether it is a vertical or horizontal relationship, to help make it easier in sorting
 * through relationships.
 */
public class Relationship {
    /**
     * Relation enumeration, where we define the possible relationships from person A to person B
     */
    public enum Relation {
        brother, sister, sibling, cousin, halfBrother, halfSister,
        mother, father, grandFather, grandMother, son, daughter, grandSon, grandDaughter,
        uncle, aunt, nephew, niece,
        stepMother, stepFather, stepBrother, stepSister, stepSon, stepDaughter,
        unrelated
    }
    int greatCount = 0; //i.e. if we want to describe a great-great-great grandfather, this value would be 3
    Member memberA, memberB; //the two members involved in the relationship. The relationship is always from A to B
    Relation relation;



}
