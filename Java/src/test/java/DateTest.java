package test.java;

import main.java.Date;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    @Test
    public void constructors0() {
        Date myDate = new Date(17,8,1998);
        assertEquals(17, myDate.day);
        assertEquals(8, myDate.monthInt);
        assertEquals(1998, myDate.year);
        assertEquals("August", myDate.monthText);
    }

    @Test
    public void constructors1() {
        Date myDate = new Date(17, "August", 1998);
        assertEquals(17, myDate.day);
        assertEquals(8, myDate.monthInt);
        assertEquals(1998, myDate.year);
        assertEquals("August", myDate.monthText);
    }

    @Test
    public void constructors2() {
        Date myDate = new Date("17/8/1998");
        assertEquals(17, myDate.day);
        assertEquals(8, myDate.monthInt);
        assertEquals(1998, myDate.year);
        assertEquals("August", myDate.monthText);
    }

    @Test
    public void comparer0() {
        Date myDate = new Date("27/06/2002");
        Date myOtherDate = new Date(17,8,1998);
        assertTrue(myDate.isYoungerThan(myOtherDate));
        assertFalse(myDate.isOlderThan(myOtherDate));
        assertFalse(myDate.equals(myOtherDate));
        assertFalse(myOtherDate.isYoungerThan(myDate));
        assertTrue(myOtherDate.isOlderThan(myDate));
        assertFalse(myOtherDate.equals(myDate));
        assertTrue((myDate.equals(myDate)));
    }

}
