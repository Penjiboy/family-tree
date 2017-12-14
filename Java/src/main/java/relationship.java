package main.java;

/**
 * Relationship class that ties one member to another. There is a relationship from member A to member B. Once this is
 * determined, it will be stored in the Storage.java in a collection separate from the members. A relationship will
 * also have a field that says whether it is a vertical or horizontal relationship, to help make it easier in sorting
 * through relationships.
 */
public class Relationship {

    int greatCount = 0; //i.e. if we want to describe a great-great-great grandfather, this value would be 3
    Member memberA, memberB; //the two members involved in the relationship. The relationship is always from A to B
    Relation relation;
    RelationDirection relationDirection;

    /**
     * Relation enumeration, where we define the possible relationships from person A to person B
     */
    public enum Relation {
        brother, sister, sibling, cousin, halfBrother, halfSister,
        mother, father, grandFather, grandMother, son, daughter, grandSon, grandDaughter, grandParent,
        uncle, aunt, nephew, niece,
        husband, wife,
        stepMother, stepFather, stepBrother, stepSister, stepSon, stepDaughter,
        motherInLaw, fatherInLaw, brotherInLaw, sisterInLaw,
        unrelated, samePerson
    }

    /**
     * RelationDirection, i.e. vertical would be e.g. father to son, horizontal would be e.g. brother to sister
     */
    public enum RelationDirection {
        horizontal, vertical
    }


    /**
     * private constructor that is used only by the determineRelationship method
     * @param relation
     * @param relationDirection
     * @param memberA
     * @param memberB
     * @param greatCount
     */
    private Relationship(Relation relation, RelationDirection relationDirection,
                         Member memberA, Member memberB, int greatCount) {
        this.relation = relation;
        this.relationDirection = relationDirection;
        this.memberA = memberA;
        this.memberB = memberB;
        this.greatCount = greatCount;
    }

    /**
     * determines the relationship between memberA and memberB and returns a new relationship object
     * @param memberA
     * @param memberB
     * @return relationship between memberA and memberB
     */
    public static Relationship determineRelationship(Member memberA, Member memberB) {
        //Current idea is to use a breadthFirst search type algorithm, get a linked list setup, check each member in the
        //direct connections, then check the next level of members until either we find the member or we run out of
        //members

        //Still need to find a way to trace back the connection, and then to identify what type of relationship it is

        return null;
    }

}
