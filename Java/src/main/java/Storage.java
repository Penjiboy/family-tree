package main.java;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Storage facility. Acts like a database, initialized using a file name, reads a file and creates the members,
 * stores the members in a set(?),  handles the adding member option(?).
 * In addition, the storage will keep track of the relationships (also probably in a set) and it will determine these
 * on demand, i.e. We will determine and store a relationship on the go, rather than all of them at once.
 * Storage format, i.e. reading from and writing to a file is to be done via JSON
 */
public class Storage {
    private static Set<Member> allMembers = new HashSet<Member>();
    private static Set<Relationship> allRelationships = new HashSet<Relationship>();

    /**
     * initialize the storage, i.e. read the input file and create member objects and store them in the set
     * @param file
     */
    public static void initializeMembers(File file) {
        //TODO: Implement this method
    }

    /**
     * get a set of all members
     * @return all members
     */
    public static Set<Member> getAllMembers() {
        return new HashSet<Member>(allMembers);
    }

    /**
     * get a set of all relationships
     * @return all relationships
     */
    public static Set<Relationship> getAllRelationships() {
        return new HashSet<Relationship>(allRelationships);
    }

    /**
     * add a member to the set of members
     * @param member
     */
    public static void addMember(Member member) {
        allMembers.add(member);
        updateFile();
    }

    /**
     * add a relationship to the set of relationships
     * @param relationship
     */
    public static void addRelationship(Relationship relationship) {
        allRelationships.add(relationship);
    }

    /**
     * write new changes to the actual file so that changes are saved
     */
    private static void updateFile() {
        //TODO: Implement this method
    }
}
