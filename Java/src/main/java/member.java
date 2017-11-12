package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Member {
    public Name name;
    public Date dateOfBirth;
    public Date dateOfDeath;
    public List<Member> spouse = new ArrayList<Member>();
    public List<Member> children = new ArrayList<Member>();
    public List<Member> parents = new ArrayList<Member>();
    public List<Member> siblings = new ArrayList<Member>();
    public List<String> extraNotes = new ArrayList<String>();
    public File image;
    public membership membershipStatus;

    public enum membership {
        initialMembership,
        childMembership,
        spouseMembership
    };

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
    public Member(Name name, Date dateOfBirth, Date  dateOfDeath, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes, File image) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
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
    public Member(Name name, Date dateOfBirth, Date  dateOfDeath, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
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
    public Member(String name, Date dateOfBirth, Date  dateOfDeath, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes, File image) {
        Name myName = new Name(name);
        this.name = myName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
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
    public Member(String name, Date dateOfBirth, Date  dateOfDeath, List<Member> spouse, List<Member> children,
                  List<Member> parents, List<Member> siblings, List<String> extraNotes) {
        Name myName = new Name(name);
        this.name = myName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
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
}