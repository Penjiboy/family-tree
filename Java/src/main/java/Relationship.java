package main.java;

import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Relationship class that ties one member to another. There is a relationship from member A to member B. Once this is
 * determined, it will be stored in the Storage.java in a collection separate from the members. A relationship will
 * also have a field that says whether it is a vertical or horizontal relationship, to help make it easier in sorting
 * through relationships.
 */
public class Relationship {

    private int greatCount = 0; //i.e. if we want to describe a great-great-great grandfather, this value would be 3
    public Member memberA, memberB; //the two members involved in the relationship. The relationship is always from A to B
    public Relation relation;
    public String relationString;
    private RelationDirection relationDirection;
    private Stack<RelationDirection> relationDirectionStack = new Stack<RelationDirection>();

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
    private enum RelationDirection {
        horizontal, vertical
    }


    /**
     * constructor of a Relationship object. Takes in two members as parameters
     * @param memberA
     * @param memberB
     */
    public Relationship(Member memberA, Member memberB) {
        this.memberA = memberA;
        this.memberB = memberB;
    }

    /**
     * determines the relationship between memberA and memberB and returns a new relation value
     * @param memberA
     * @param memberB
     * @return relationship between memberA and memberB
     */
    public Relation determineRelationship(Member memberA, Member memberB) {
        //Current idea is to use a breadthFirst search type algorithm, get a linked list setup, check each member in the
        //direct connections, then check the next level of members until either we find the member or we run out of
        //members
        //Still need to find a way to trace back the connection, and then to identify what type of relationship it is
        //How about

        this.memberA = memberA;
        this.memberB = memberB;

        return null; //change this
    }

    /**
     * checks if memberB exists on the same level as member A
     * @return true if memberB exists on the same level as memberA, false otherwise
     */
    private boolean checkCurrentRow(Stack<RelationDirection> currentState) {
        //Check direct siblings
        Set<Member> results = memberA.getSiblings().parallelStream().filter(member -> member.equals(this.memberB))
                .collect(Collectors.toSet());
        if(!results.isEmpty())
            return true;

        //check cousins. I.e. look at parents' siblings' children
        results = memberA.getParents().parallelStream().filter(member -> member.getSiblings()
                .parallelStream().filter(member1 -> !member1.getChildren()
                        .parallelStream().filter(member2 -> !member2.equals(memberB)).collect(Collectors.toSet()).isEmpty())
                .collect(Collectors.toSet()).isEmpty())
                .collect(Collectors.toSet());
        return !results.isEmpty();

    }

    /**
     * Check if memberB exists on a row above memberA
     * @param currentState of how many rows have been searched so far
     * @return true if memberB exists on a row above memberA
     */
    private boolean checkUpstream(Stack<RelationDirection> currentState) {

    }

    /**
     * Check if memberB exists on a row below memberA
     * @param currentState of how many rows have been searched so far
     * @return true if memberB exists on a row below memberA
     */
    private boolean checkDownstream(Stack<RelationDirection> currentState) {

    }

}
