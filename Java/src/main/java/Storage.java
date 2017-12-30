//package main.java;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.json.*;


/**
 * Storage facility. Acts like a database, initialized using a file name, reads a file and creates the members,
 * stores the members in a set(?),  handles the adding member option(?).
 * In addition, the storage will keep track of the relationships (also probably in a set) and it will determine these
 * on demand, i.e. We will determine and store a relationship on the go, rather than all of them at once.
 * Storage format, i.e. reading from and writing to a file is to be done via JSON
 */
public class Storage {
    private static Set<Member> allMembers = new HashSet<Member>();
    private static List<Member> incompleteMembers = new ArrayList<Member>();
    private static Set<Relationship> allRelationships = new HashSet<Relationship>();

    /**
     * initialize the storage, i.e. read the input file and create member objects and store them in the set
     * @param file
     */
    public static void initializeMembers(File file) throws IOException {
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNext()) {
            JsonObject jsonObject = Json.createReader(new StringReader(fileReader.nextLine())).readObject();
            Member member = new Member(jsonObject);
        }

    }

    /**
     * get a set of all members
     * @return all members
     */
    public static Set<Member> getAllMembers() {
        return new HashSet<Member>(allMembers);
    }

    public static List<Member> getIncompleteMembers() {return new ArrayList<Member>(incompleteMembers); }

    /**
     * get a set of all relationships
     * @return all relationships
     */
    public static Set<Relationship> getAllRelationships() {
        return new HashSet<Relationship>(allRelationships);
    }

    /**
     * add a member to the database of members. Warning, this does not update the file. If the file is not updated, any
     * new members that have been added will be lost when the application is quit.
     * @param member
     */
    public static void addMember(Member member) {
        boolean isComplete = true;
        if(member.getGender() == null) isComplete = false;
        if(member.getDateOfBirth() == null) isComplete = false;
        if(member.getDateOfDeath() == null) isComplete = false;
        if(!isComplete)
            incompleteMembers.add(member);
        allMembers.add(member);
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
     * @param file to which we are writing changes. The file must be closed to allow for a write operation to occur
     * @return true if the write operation was successful, false otherwise.
     */
    private static boolean updateFile(File file) {
        //TODO: Implement this method
        if(!file.canWrite())
            return false;
    }
}
