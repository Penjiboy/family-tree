package test.java;

import main.java.Member;
import main.java.Relationship;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RelationshipTest {
    /**
     * Generate some members
     * @return a set containing the generated members
     */
    private Set<Member> generateMembers() {
        Member me = new Member("Me Fam");
        Member mom = new Member("Mom Fam");
        Member dad = new Member("Dad Fam");
        Member brother = new Member("Brother Fam");
        Member sister = new Member("Sister Fam");
        Member momsBrother = new Member("MomsBrother Fam");
        Member momsBrothersSon = new Member("MomsBrothersSon Fam");
        Member momsSister = new Member("MomsSister Fam");
        Member momsDad = new Member("MomsDad Fam");
        Member momsMom = new Member("MomsMom Fam");
        Member dadsMom = new Member("DadsMom Fam");
        Member dadsDad = new Member("DadsDad Fam");

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

        Relationship relationship = new Relationship(me, momsBrothersSon);
        relationship.determineRelationship();
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

        Relationship relationship = new Relationship(dad, momsBrother);
        relationship.determineRelationship();
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

        Relationship relationship = new Relationship(me, momsBrother);
        relationship.determineRelationship();
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

        Relationship relationship = new Relationship(dadsDad, momsBrothersSon);
        relationship.determineRelationship();
    }


}
