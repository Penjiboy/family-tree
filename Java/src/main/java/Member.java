package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
     * Constructor of a Member object using all parameters. Name is input as a Name object
     * @param name
     * @param spouse
     * @param children
     * @param parents
     * @param siblings
     * @param extraNotes
     * @param image
     */
    public Member(Name name, Date dateOfBirth, Date  dateOfDeath, Gender gender, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes, File image) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.spouse = spouse;
        this.children = children;
        this.parents = parents;
        this.siblings = siblings;
        this.extraNotes = extraNotes;
        this.image = image;
    }

    /**
     * Constructor of a Member object
     * @param name
     * @param dateOfBirth
     * @param dateOfDeath
     * @param spouse
     * @param children
     * @param parents
     * @param siblings
     * @param extraNotes
     */
    public Member(Name name, Date dateOfBirth, Date  dateOfDeath, Gender gender, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.spouse = spouse;
        this.children = children;
        this.parents = parents;
        this.siblings = siblings;
        this.extraNotes = extraNotes;
    }

    /**
     * Constructor of a Member object using all parameters. Name is input as a String object
     * @param name
     * @param spouse
     * @param children
     * @param parents
     * @param siblings
     * @param extraNotes
     * @param image
     */
    public Member(String name, Date dateOfBirth, Date  dateOfDeath, Gender gender, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes, File image) {
        Name myName = new Name(name);
        this.name = myName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.spouse = spouse;
        this.children = children;
        this.parents = parents;
        this.siblings = siblings;
        this.extraNotes = extraNotes;
        this.image = image;
    }

    /**
     * Constructor of a Member object
     * @param name
     * @param dateOfBirth
     * @param dateOfDeath
     * @param spouse
     * @param children
     * @param parents
     * @param siblings
     * @param extraNotes
     */
    public Member(String name, Date dateOfBirth, Date  dateOfDeath, Gender gender, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes) {
        Name myName = new Name(name);
        this.name = myName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.spouse = spouse;
        this.children = children;
        this.parents = parents;
        this.siblings = siblings;
        this.extraNotes = extraNotes;
    }

    /**
     * Constructor that takes in only a name object
     * @param name
     */
    public Member(Name name) {
        this.name = name;
    }

    public Member(String name) {
        Name myName = new Name(name);
        this.name = myName;
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
     * @param gender
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
        this.spouse.add(spouse);
    }

    /**
     * add child
     * @modifies the children list
     * @param child
     */
    public void addChild(Member child) {
        this.children.add(child);
    }

    /**
     * add a parent to the parents list
     * @param parent
     */
    public void addParent(Member parent) {
        this.parents.add(parent);
    }

    /**
     * add a sibling to the siblings list
     * @param sibling
     */
    public void addSibling(Member sibling) {
        this.siblings.add(sibling);
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