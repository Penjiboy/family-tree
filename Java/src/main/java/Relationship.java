package main.java;

import java.util.*;
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
    private RelationDirection relationDirection;
    private Stack<RelationDirection> relationDirectionStack = new Stack<RelationDirection>();

    /**
     * Relation enumeration, where we define the possible relationships from person A to person B
     */
    public enum Relation {
        brother, sister, sibling, cousin, halfBrother, halfSister,
        mother, father, grandFather, grandMother, grandParent, parent,
        son, daughter, grandSon, grandDaughter, child, grandChild,
        uncle, aunt, nephew, niece, parentSibling, siblingChild,
        husband, wife, spouse,
        stepMother, stepFather, stepBrother, stepSister, stepSon, stepDaughter,
        motherInLaw, fatherInLaw, brotherInLaw, sisterInLaw, siblingInLaw,
        unrelated, samePerson
    }

    /**
     * RelationDirection, i.e. vertical would be e.g. father to son, horizontal would be e.g. brother to sister
     */
    private enum RelationDirection {
        up, down
    }


    /**
     * private constructor of a Relationship object. Takes in two members as parameters and returns a relationship
     * object that goes from memberA to memberB.
     * @param memberA
     * @param memberB
     * @return Relationship Object
     */
    private Relationship(Member memberA, Member memberB) {
        this.memberA = memberA;
        this.memberB = memberB;
    }

    /**
     * Relationship object producer. Takes in two members as parameters and returns a relationship
     * object that goes from memberA to memberB. This method also adds this relationship to our relationship storage
     * @param memberA
     * @param memberB
     * @return Relationship Object
     * @throws IllegalArgumentException if a relationship between the two members already exists
     */
    public static Relationship createRelationship(Member memberA, Member memberB) {
        Set<Relationship> result = Storage.getAllRelationships().parallelStream()
                .filter(relationship -> (relationship.memberA.equals(memberA))
                        && (relationship.memberB.equals(memberB)))
                .collect(Collectors.toSet());
        if(result.isEmpty()) {
            Relationship relationship = new Relationship(memberA, memberB);
            Storage.addRelationship(relationship);
            return relationship;
        }
        else throw new IllegalArgumentException("A relationship between these two members already exists");
    }
    /**
     * determines the relationship between memberA and memberB and returns a new relation value
     * @return relationship between memberA and memberB
     */
    public Relation determineRelationship() {
        if(memberB.equals(memberA)) {
            this.relation = Relation.samePerson;
            try {
                generateInverseRelationship();
            } catch (IllegalArgumentException iae) {
                //it means inverse relationship has already been created
            }
            return this.relation;
        }

        Stack<RelationDirection> trackingProgress = new Stack<RelationDirection>();

        if(checkCurrentRow(memberA)) {}
        else if(checkOtherRows(trackingProgress, RelationDirection.up)) {}
        else if(checkOtherRows(trackingProgress, RelationDirection.down)) {}
        else {
            this.relation = Relation.unrelated;
            try {
                generateInverseRelationship();
            } catch (IllegalArgumentException iae) {
                //it means inverse relationship has already been created
            }
            return this.relation;
        }

        Set<Member> result;

        //classify as sibling or cousin or husband/wife or sibling in law
        if (trackingProgress.isEmpty()) {
            //check if direct sibling
            result = memberA.getSiblings().parallelStream().filter(member -> member.equals(memberB))
                    .collect(Collectors.toSet());
            if (!result.isEmpty()) {
                //check if it's a male or female\
                try {
                    if (memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.brother;
                    else if (memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.sister;
                    else this.relation = Relation.sibling;

                } catch (NullPointerException npe) {
                    this.relation = Relation.sibling;
                }
            }
            //else it's a cousin
            else this.relation = Relation.cousin;

            //check if spouse
            result = memberA.getSpouse().parallelStream().filter(member -> member.equals(memberB))
                    .collect(Collectors.toSet());
            if(!result.isEmpty()) {
                //it must be a spouse
                try {
                    if(memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.husband;
                    else if(memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.wife;
                    else this.relation = Relation.spouse;
                } catch (NullPointerException npe) {
                    this.relation = Relation.spouse;
                }
            }
            //check if it's an in-law
            List<Member> spouseSiblings = new ArrayList<Member>();
            for(Member spouse: memberA.getSpouse()) spouseSiblings.addAll(spouse.getSiblings());
            result = spouseSiblings.parallelStream().filter(member -> member.equals(memberB))
                    .collect(Collectors.toSet());
            if(!result.isEmpty()) {
                //it must be a brother/sister in law
                try {
                    if(memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.sisterInLaw;
                    else if(memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.brotherInLaw;
                    else this.relation = Relation.siblingInLaw;
                } catch (NullPointerException npe) {
                    this.relation =Relation.siblingInLaw;
                }
            }
        }

        //classify as parent or uncle/aunt
        if ((trackingProgress.size() == 1) && (trackingProgress.peek().equals(RelationDirection.up))) {
            result = memberA.getParents().parallelStream().filter(member -> member.equals(memberB))
                    .collect(Collectors.toSet());
            if (!result.isEmpty()) {
                //therefore it's a parent
                try {
                    if (memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.father;
                    else if (memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.mother;
                    else this.relation = Relation.parent;
                } catch (NullPointerException npe) {
                    this.relation = Relation.parent;
                }
            } else {
                //it's an uncle/aunt
                try {
                    if (memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.uncle;
                    else if (memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.aunt;
                    else this.relation = Relation.parentSibling;
                } catch (NullPointerException npe) {
                    this.relation = Relation.parentSibling;
                }
            }
        }

        //classify as son/daughter or niece/nephew
        if ((trackingProgress.size() == 1) && (trackingProgress.peek().equals(RelationDirection.down))) {
            result = memberA.getChildren().parallelStream().filter(member -> member.equals(memberB))
                    .collect(Collectors.toSet());
            if(!result.isEmpty()) {
                //it must be a son/daughter
                try {
                    if(memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.son;
                    else if(memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.daughter;
                    else this.relation = Relation.child;
                } catch (NullPointerException npe) {
                    this.relation = Relation.child;
                }
            }
            else {
                //it must be a niece/nephew
                try {
                    if(memberB.getGender().equals(Member.Gender.male)) this.relation = Relation.nephew;
                    else if(memberB.getGender().equals(Member.Gender.female)) this.relation = Relation.niece;
                    else this.relation = Relation.siblingChild;
                } catch (NullPointerException npe) {
                    this.relation = Relation.siblingChild;
                }
            }
        }

        //Check whether grandchild or grandparent
        if(trackingProgress.size() >= 2) {
            this.greatCount = 2 - trackingProgress.size();
            try {
                //check whether grandson or grandfather
                if(memberB.getGender().equals(Member.Gender.male)) {
                    if(trackingProgress.peek().equals(RelationDirection.up)) this.relation = Relation.grandFather;
                    else if(trackingProgress.peek().equals(RelationDirection.down))
                        this.relation = Relation.grandSon;
                }
                //check whether granddaughter or grandmother
                else if(memberB.getGender().equals(Member.Gender.female)) {
                    if(trackingProgress.peek().equals(RelationDirection.up)) this.relation = Relation.grandMother;
                    else if(trackingProgress.peek().equals(RelationDirection.down))
                        this.relation = Relation.grandDaughter;
                }
                //else they must be general grandparent or grandchild
                else {
                    if(trackingProgress.peek().equals(RelationDirection.up)) this.relation = Relation.grandParent;
                    else if(trackingProgress.peek().equals(RelationDirection.down))
                        this.relation = Relation.grandChild;
                }
            } catch (NullPointerException npe) {
                if(trackingProgress.peek().equals(RelationDirection.up)) this.relation = Relation.grandParent;
                else if(trackingProgress.peek().equals(RelationDirection.down))
                    this.relation = Relation.grandChild;
            }
        }


        //Also don't forget the relationship going the other way. I.e. if A -> B is father, then B -> A is son/daughter
        try {
            generateInverseRelationship();
        } catch (IllegalArgumentException iae) {
            //it means inverse relationship has already been created
        }
        return this.relation;
    }

    /**
     * Generate inverse relationship and adds it to the relationship storage
     */
    private void generateInverseRelationship() {
        Relationship inverseRelationship = Relationship.createRelationship(memberB, memberA);
        inverseRelationship.determineRelationship();
    }

    /**
     * return the corresponding inverse relationship object
     * @return relationship object
     */
    public Relationship findInverseRelationship() {
        List<Relationship> result = Storage.getAllRelationships().parallelStream()
                .filter(relationship -> relationship.memberA.equals(this.memberB)
                        && relationship.memberB.equals(this.memberA))
                .collect(Collectors.toList());
        if(result.size() != 1)
            throw new IllegalStateException("Inverse relationship does not exist. " +
                    "Or there's more than 1 relationships");
        else {
            return result.get(0);
        }
    }

    /**
     * checks if memberB exists on the same level as the memberToConsider
     * @param memberToConsider must be a valid member
     * @return true if memberB exists on the same level as memberA, false otherwise
     */
    private boolean checkCurrentRow(Member memberToConsider) {
        //Check direct siblings
        Set<Member> results = memberToConsider.getSiblings().parallelStream().filter(member -> member.equals(this.memberB))
                .collect(Collectors.toSet());
        if(!results.isEmpty())
            return true;

        Set<Member> workingResultsSet = new HashSet<Member>();
        //check cousins. I.e. look at parents' siblings' children
        helpCurrentRow(memberToConsider, workingResultsSet);

        //check siblings' parents as well as siblings' spouses'
        for(Member sibling: memberToConsider.getSiblings()) {
            workingResultsSet.addAll(sibling.getSiblings());
            helpCurrentRow(sibling, workingResultsSet);

            //for(Member spouse: sibling.getSpouse()) workingResultsSet.add(spouse);
        }

        //check spouses' parents as well
        for(Member spouse: memberToConsider.getSpouse()) {
            workingResultsSet.addAll(spouse.getSiblings());
            helpCurrentRow(spouse, workingResultsSet);
        }

        results = workingResultsSet.parallelStream()
                .filter(member -> member.equals(memberB))
                .collect(Collectors.toSet());
        return !results.isEmpty();
    }

    /**
     * mutate a set, adding all members from the input member's parents' siblings' children. Helper method for
     * checkCurrentRow method.
     * @param member whose parents we're considering
     * @param resultsSet into which we add the members
     */
    private void helpCurrentRow(Member member, Set<Member> resultsSet) {
        for(Member parent: member.getParents()) {
            for(Member parentSibling: parent.getSiblings()) {
                resultsSet.addAll(parentSibling.getChildren());
            }
        }
    }

    /**
     * Check if memberB exists on a row above memberA
     * @param currentState of how many rows have been searched so far
     * @param direction in which we are checking, i.e. up or down
     * @return true if memberB exists on a row above/below memberA
     */
    private boolean checkOtherRows(Stack<RelationDirection> currentState, RelationDirection direction) {
        if(direction.equals(RelationDirection.up))
            currentState.push(RelationDirection.up);
        else if(direction.equals(RelationDirection.down))
            currentState.push(RelationDirection.down);
        Member memberOfCurrentState;
        try {
            if(direction.equals(RelationDirection.up))
                memberOfCurrentState = memberA.getParents().get(0);
            else
                memberOfCurrentState = memberA.getChildren().get(0);
        } catch(IndexOutOfBoundsException aioobe) { //if there's no parent or child, then stop checking
            currentState.clear();
            return false;
        }
        boolean notFound = true; //flag that checks whether a member has been found on the level or not
        boolean noMoreMembers = false; //flag that checks whether there are any more members upstream

        //while loop to check a level, if the member is not found, we continue going up, until a member is found
        //or we cannot go any further up
        while(notFound && !noMoreMembers) {
            notFound = !checkCurrentRow(memberOfCurrentState);
            if(notFound) {
                if(direction.equals(RelationDirection.up))
                    currentState.push(RelationDirection.up);
                else
                    currentState.push(RelationDirection.down);
                try {
                    if(direction.equals(RelationDirection.up))
                        memberOfCurrentState = memberOfCurrentState.getParents().get(0);
                    else
                        memberOfCurrentState = memberOfCurrentState.getChildren().get(0);
                } catch (IndexOutOfBoundsException aioobe) {
                    noMoreMembers = true;
                }
            }
        }

        //perhaps some redundant code in here
        if((!notFound) && (!noMoreMembers))
            return true;
        else if((notFound) && (noMoreMembers)) {
            currentState.clear();
            return false;
        }
        else { //might want to change this
            currentState.clear();
            return false;
        }
    }

}
