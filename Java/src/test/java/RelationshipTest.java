//package test.java;

//import main.java.Member;
//import main.java.Relationship;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
//import static Relationship.Relation.*;
//import static main.java.Relationship.Relation.*;
import static org.junit.Assert.*;


public class RelationshipTest {
    /**
     * Generate some members
     * @return a set containing the generated members
     */
    private Set<Member> generateMembers() {
        String male = "male"; String female = "female";
        Member me = new Member("Me Fam"); me.setGender(male);
        Member mom = new Member("Mom Fam"); mom.setGender(female);
        Member dad = new Member("Dad Fam"); dad.setGender(male);
        Member brother = new Member("Brother Fam"); brother.setGender(male);
        Member sister = new Member("Sister Fam"); sister.setGender(female);
        Member momsBrother = new Member("MomsBrother Fam"); momsBrother.setGender(male);
        Member momsBrothersSon = new Member("MomsBrothersSon Fam"); momsBrothersSon.setGender(male);
        Member momsSister = new Member("MomsSister Fam"); momsSister.setGender(female);
        Member momsDad = new Member("MomsDad Fam"); momsDad.setGender(male);
        Member momsMom = new Member("MomsMom Fam"); momsMom.setGender(female);
        Member dadsMom = new Member("DadsMom Fam"); dadsMom.setGender(female);
        Member dadsDad = new Member("DadsDad Fam"); dadsDad.setGender(male);

        //build relationships
        me.addParent(mom);
        me.addParent(dad);
        me.addSibling(brother);
        me.addSibling(sister);
        brother.addParent(mom);
        sister.addParent(mom);
        brother.addParent(dad);
        sister.addParent(dad);
        brother.addSibling(sister);
        mom.addSibling(momsBrother);
        mom.addSibling(momsSister);
        momsBrother.addChild(momsBrothersSon);
        momsBrother.addSibling(momsSister);
        momsBrother.addParent(momsDad);
        momsBrother.addParent(momsMom);
        momsSister.addParent(momsDad);
        momsSister.addParent(momsMom);
        mom.addParent(momsDad);
        mom.addParent(momsMom);
        mom.addSpouse(dad);
        momsDad.addSpouse(momsMom);
        dad.addParent(dadsDad);
        dad.addParent(dadsMom);
        dadsDad.addSpouse(dadsMom);

        //Add these members to the set
        Set<Member> family = new HashSet<Member>();
        family.add(me);
        family.add(mom);
        family.add(dad);
        family.add(brother);
        family.add(sister);
        family.add(momsBrother);
        family.add(momsBrothersSon);
        family.add(momsSister);
        family.add(momsDad);
        family.add(momsMom);
        family.add(dadsDad);
        family.add(dadsMom);

        return family;
    }

    @Test
    public void checkCurrentRowTest1() {
        //Create members
        Set<Member> family = generateMembers();
        Set<Member> temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("Me Fam"))
                .collect(Collectors.toSet());
        Member me = null;
        for(Member member: temp) me = member;

        temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("MomsBrothersSon Fam"))
                .collect(Collectors.toSet());
        Member momsBrothersSon = null;
        for(Member member: temp) momsBrothersSon = member;

        Relationship relationship = Relationship.createRelationship(me, momsBrothersSon);
        assertEquals(Relationship.Relation.cousin, relationship.determineRelationship());

        //check inverse relationship
        assertEquals(Relationship.Relation.cousin, relationship.findInverseRelationship().relation);
    }

    @Test
    public void checkCurrentRowTest2() {
        Set<Member> family = generateMembers();
        Set<Member> temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("Dad Fam"))
                .collect(Collectors.toSet());
        Member dad = null;
        for(Member member: temp) dad = member;

        temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("MomsBrother Fam"))
                .collect(Collectors.toSet());
        Member momsBrother = null;
        for(Member member: temp) momsBrother = member;

        Relationship relationship = Relationship.createRelationship(dad, momsBrother);
        assertEquals(Relationship.Relation.brotherInLaw, relationship.determineRelationship());

        //check inverse relationship
        assertEquals(Relationship.Relation.brotherInLaw, relationship.findInverseRelationship().relation);
    }

    @Test
    public void checkRowAboveTest1() {
        Set<Member> family = generateMembers();
        Set<Member> temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("Me Fam"))
                .collect(Collectors.toSet());
        Member me = null;
        for(Member member: temp) me = member;

        temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("MomsBrother Fam"))
                .collect(Collectors.toSet());
        Member momsBrother = null;
        for(Member member: temp) momsBrother = member;

        Relationship relationship = Relationship.createRelationship(me, momsBrother);
        assertEquals(Relationship.Relation.uncle, relationship.determineRelationship());

        //check for inverse relationship
        assertEquals(Relationship.Relation.nephew, relationship.findInverseRelationship().relation);
    }

    @Test
    public void checkRowBelowTest1() {
        Set<Member> family = generateMembers();
        Set<Member> temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("DadsDad Fam"))
                .collect(Collectors.toSet());
        Member dadsDad = null;
        for(Member member: temp) dadsDad = member;

        temp = family.parallelStream()
                .filter(member -> member.getName().fullName.contentEquals("MomsBrothersSon Fam"))
                .collect(Collectors.toSet());
        Member momsBrothersSon = null;
        for(Member member: temp) momsBrothersSon = member;

        Relationship relationship = Relationship.createRelationship(dadsDad, momsBrothersSon);
        assertEquals(Relationship.Relation.unrelated, relationship.determineRelationship());

        //check inverse relationship
        assertEquals(Relationship.Relation.unrelated, relationship.findInverseRelationship().relation);
    }


}
