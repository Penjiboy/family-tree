//package test.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameTest {
    //Test constructor
    @Test
    public void test0() {
        String myName = "Penjani Middle Name Chavula";
        Name name = new Name(myName);

        assertEquals("Penjani Middle Name Chavula", name.fullName);
        assertEquals("Penjani", name.first);
        assertEquals("Chavula", name.last);
        assertEquals("Middle Name", name.middle.get(0) + " " + name.middle.get(1));
    }
}
