package test.java;

import main.java.Member;
import main.java.Relationship;
import org.junit.Test;

public class RelationshipTest {
    @Test
    public void checkCurrentRowTest1() {
        //Create members
        Member me = new Member("Me Fam");
        Member mom = new Member("Mom Fam");
        Member dad = new Member("Dad Fam");
        Member momsBrother = new Member("MomsBrother Fam");
        Member momsBrothersSon = new Member("MomsBrothersSon Fam");

        //build relationships
        me.addParent(dad);
        me.addParent(mom);
        mom.addSibling(momsBrother);
        momsBrother.addChild(momsBrothersSon);

        Relationship sameLevel = new Relationship(me, momsBrothersSon);
        sameLevel.determineRelationship();
    }
}
