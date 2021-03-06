//package main.java;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.json.*;
import javax.json.stream.JsonParser;


public class Member {
    private Name name;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private Gender gender;
    private List<Member> spouse = new ArrayList<Member>();
    private List<Member> children = new ArrayList<Member>();
    private List<Member> parents = new ArrayList<Member>();
    private List<Member> siblings = new ArrayList<Member>();
    private List<String> extraNotes = new ArrayList<String>();
    private File image;
    public Membership membershipStatus;

    public enum Membership {
        initialMembership,
        childMembership,
        spouseMembership
    };

    public enum Gender {
        male, female
    }

    /**
     * Constructor that takes in only a name string
     * @param name
     */
    public Member(String name) {
        Name myName = new Name(name);
        this.name = myName;
        Storage.addMember(this);
    }

    /**
     * Create a member object from a Json objected
     * @param jsonObject must be properly formatted
     */
    public Member(JsonObject jsonObject) {
        JsonParser parseMember = Json.createParser(new StringReader(jsonObject.toString()));
        Member member = new Member("");

        while(parseMember.hasNext()) {
            JsonParser.Event event = parseMember.next();
            if(event != JsonParser.Event.KEY_NAME)
                continue;

            switch(parseMember.getString()) {
                case "Name":
                    parseMember.next();
                    if(nameExists(parseMember.getString())) {
                        member = Storage.getIncompleteMembers().parallelStream()
                                .filter(member1 -> member1.name.fullName.contentEquals(parseMember.getString()))
                                .collect(Collectors.toList()).get(0);
                        Storage.getIncompleteMembers().remove(member);
                    }
                    member.name = new Name(parseMember.getString());
                    break;
                case "Date of Birth":
                    parseMember.next();
                    Date dob = new Date(parseMember.getString());
                    member.setDateOfBirth(dob);
                    break;
                case "Date of Death":
                    parseMember.next();
                    Date dod = new Date(parseMember.getString());
                    member.setDateOfDeath(dod);
                    break;
                case "Gender":
                    parseMember.next();
                    member.setGender(parseMember.getString());
                    break;
                case "Spouse(s)":
                    parseMember.next();
                    List<String> spouseNames = new ArrayList<String>();
                    while(parseMember.next() == JsonParser.Event.VALUE_STRING)
                        spouseNames.add(parseMember.getString());
                    for(String spouse: spouseNames) {
                        if(nameExists(spouse)) {
                            Member member1 = Storage.getAllMembers().parallelStream()
                                    .filter(member2 -> member2.name.fullName.contentEquals(spouse))
                                    .collect(Collectors.toList()).get(0);
                            member.addSpouse(member1);
                        }
                        else {
                            Member member1 = new Member(spouse);
                            member.addSpouse(member1);
                        }
                    }
                    break;
                case "Children":
                    parseMember.next();
                    List<String> childrenNames = new ArrayList<String>();
                    while(parseMember.next() == JsonParser.Event.VALUE_STRING)
                        childrenNames.add(parseMember.getString());
                    for(String child: childrenNames) {
                        if(nameExists(child)) {
                            Member member1 = Storage.getAllMembers().parallelStream()
                                    .filter(member2 -> member2.name.fullName.contentEquals(child))
                                    .collect(Collectors.toList()).get(0);
                            member.addChild(member1);
                        }
                        else {
                            Member member1 = new Member(child);
                            member.addChild(member1);
                        }
                    }
                    break;
                case "Parents":
                    parseMember.next();
                    List<String> parentNames = new ArrayList<String>();
                    while(parseMember.next() == JsonParser.Event.VALUE_STRING)
                        parentNames.add(parseMember.getString());
                    for(String parent: parentNames) {
                        if(nameExists(parent)) {
                            Member member1 = Storage.getAllMembers().parallelStream()
                                    .filter(member2 -> member2.name.fullName.contentEquals(parent))
                                    .collect(Collectors.toList()).get(0);
                            member.addParent(member1);
                        }
                        else {
                            Member member1 = new Member(parent);
                            member.addParent(member1);
                        }
                    }
                    break;
                case "Siblings":
                    parseMember.next();
                    List<String> siblingNames = new ArrayList<>();
                    while(parseMember.next() == JsonParser.Event.VALUE_STRING)
                        siblingNames.add(parseMember.getString());
                    for(String sibling: siblingNames) {
                        if(nameExists(sibling)) {
                            Member member1 = Storage.getAllMembers().parallelStream()
                                    .filter(member2 -> member2.name.fullName.contentEquals(sibling))
                                    .collect(Collectors.toList()).get(0);
                            member.addSibling(member1);
                        }
                        else {
                            Member member1 = new Member(sibling);
                            member.addSibling(member1);
                        }
                    }
                    break;
                case "Extra Notes":
                    parseMember.next();
                    while(parseMember.next() == JsonParser.Event.VALUE_STRING) {
                        member.addNote(parseMember.getString());
                    }
                    break;
                default: break;
            }
        }

        Storage.addMember(member);
    }

    /**
     * Generate a Json formatted string representation of this member
     * @return Json formatted string
     */
    public String jsonString() {
        JsonArrayBuilder spousesArray = Json.createArrayBuilder();
        JsonArrayBuilder childrenArray = Json.createArrayBuilder();
        JsonArrayBuilder siblingsArray = Json.createArrayBuilder();
        JsonArrayBuilder parentsArray = Json.createArrayBuilder();
        JsonArrayBuilder extraNotesArray = Json.createArrayBuilder();

        for (Member spouse : this.getSpouse()) spousesArray.add(spouse.name.fullName);
        for (Member child : this.getChildren()) childrenArray.add(child.name.fullName);
        for(Member sibling: this.getSiblings()) siblingsArray.add(sibling.name.fullName);
        for(Member parent: this.getParents()) parentsArray.add(parent.name.fullName);
        for(String note: this.getExtraNotes()) extraNotesArray.add(note);

        JsonArrayBuilder myJsonArray = Json.createArrayBuilder();
        myJsonArray.add(Json.createObjectBuilder()
                .add("Name", this.name.fullName)
                .add("Date of Birth", this.dateOfBirth.toString())
                .add("Date of Death", this.dateOfDeath.toString())
                .add("Gender", this.getGender().toString())
                .add("Spouse(s)", spousesArray)
                .add("Children", childrenArray)
                .add("Parents", parentsArray)
                .add("Siblings", siblingsArray)
                .add("Extra Notes", extraNotesArray)
        );

        return myJsonArray.build().toString();
    }

    /**
     * Checks if a member already exists in the databbse with just the name filled in.
     * @return true if they have the name or more details filled in, false
     * if they do not exist in the database
     */
    private boolean nameExists(String name) {
        Set<Member> results = Storage.getAllMembers().parallelStream()
                .filter(member -> member.name.fullName.contentEquals(name)).collect(Collectors.toSet());
        return !results.isEmpty();
    }

    /**
     * Compares this member to other, returns true if the two members are the same based on name, date of birth, and
     * gender
     * @param other
     * @return true if the two members are the same, false otherwise
     */
    public boolean equals(Object other) {
        if(!(other instanceof Member))
            return false;
        else {
            if(!(this.name.equals(((Member) other).name)))
                return false;
            try {
                if (!(this.dateOfBirth.equals(((Member) other).dateOfBirth)))
                    return false;
                if (!(this.gender.equals(((Member) other).gender)))
                    return false;
            } catch (Exception e) {}
            return true;
        }
    }

    /**
     * Add the date of birth
     * @param dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Add the date of death
     * @param dateOfDeath
     */
    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    /**
     * Set gender
     * @param gender as a string or a gender object
     */
    public void setGender(Object gender) {
        if(gender instanceof String) {
            if ( gender.toString().contentEquals("female") || gender.toString().contentEquals("girl") ||
                    gender.toString().contentEquals("woman"))
                this.gender = Gender.female;
            else if (gender.toString().contentEquals("male") || gender.toString().contentEquals("boy") ||
                    gender.toString().contentEquals("man"))
                this.gender = Gender.male;
        }
        else if(gender instanceof Gender)
            this.gender = (Gender) gender;
        else throw new IllegalArgumentException();
    }

    /**
     * Add spouse
     * @modifies the spouse list
     * @param spouse
     */
    public void addSpouse(Member spouse) {
        if(!this.getSpouse().contains(spouse)) {
            this.spouse.add(spouse);
            spouse.addSpouse(this);
        }
    }

    /**
     * add child
     * @modifies the children list
     * @param child
     */
    public void addChild(Member child) {
        if(!this.getChildren().contains(child)) {
            this.children.add(child);
            child.addParent(this);
        }
    }

    /**
     * add a parent to the parents list
     * @param parent
     */
    public void addParent(Member parent) {
        if(!this.getParents().contains(parent)) {
            this.parents.add(parent);
            parent.addChild(this);
        }
    }

    /**
     * add a sibling to the siblings list
     * @param sibling
     */
    public void addSibling(Member sibling) {
        if(!this.getSiblings().contains(sibling)) {
            this.siblings.add(sibling);
            sibling.addSibling(this);
        }
    }

    /**
     * Add a note to the extraNotes list
     * @param note
     */
    public void addNote(String note) {
        this.extraNotes.add(note);
    }

    /**
     * Set the image for this Member
     * @param image
     */
    public void setImage(File image) {
        this.image = image;
    }

    /**
     * Get name
     * @return name of this member
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Get date of birth
     * @return date of birth
     */
    public Date getDateOfBirth() { return this.dateOfBirth; }

    /**
     * Get date of death
     * @return date of death
     */
    public Date getDateOfDeath() {return this.dateOfDeath;}

    /**
     * Get the gender
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Get a list of spouses
     * @return list of spouses
     */
    public List<Member> getSpouse() {
        return spouse;
    }

    /**
     * Get list of children
     * @return children
     */
    public List<Member> getChildren() {
        return children;
    }

    /**
     * Get list of parents
     * @return list of parents
     */
    public List<Member> getParents() {
        return parents;
    }

    /**
     * Get list of siblings
     * @return list of siblings
     */
    public List<Member> getSiblings() {
        return siblings;
    }

    /**
     * Get list of extranotes
     * @return list of extranotes
     */
    public List<String> getExtraNotes() {
        return extraNotes;
    }

    /**
     * get image file
     * @return image file
     */
    public File getImage() {
        return image;
    }
}