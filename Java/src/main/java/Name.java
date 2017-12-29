//package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Name {
    public final String first;
    public final String last;
    public final List<String> middle = new ArrayList<String>();
    public final String fullName;

    /**
     * Construct for the Name object
     * @param name
     */
    public Name(String name) throws IllegalArgumentException {
        //TODO: Implement this method
        Scanner nameScanner = new Scanner(name);
        this.fullName = name;
        List<String> names = new ArrayList<String>();

        while(nameScanner.hasNext()) {
            names.add(nameScanner.next());
        }

        switch(names.size()) {
            case 0: throw new IllegalArgumentException("Error! Name entered is empty");
            case 1: throw new IllegalArgumentException("Must enter at least a first and last name");
            case 2:
                this.first = names.get(0);
                this.last = names.get(1);
                break;
            default:
                this.first = names.get(0);
                this.last = names.get(names.size() - 1);

                for(int count = 1; count < (names.size() -1); count++) {
                    this.middle.add(names.get(count));
                }
        }
    }

    /**
     * checks to see if two names are equal based on first name and last name
     * @param other
     * @return true if the names are equal, false otherwise
     */
    public boolean equals(Object other) {
        if(!(other instanceof Name))
            return false;
        else {
            boolean success = true;
            if(!(this.first.contentEquals(((Name) other).first)))
                success = false;
            if(!(this.last.contentEquals(((Name) other).last)))
                success = false;
            return success;
        }
    }

    /**
     * return a hashCode
     * @return
     */
    public int hashCode() {
        //TODO: perhaps this could be better?
        return first.hashCode() + last.hashCode() + last.hashCode();
    }
}
