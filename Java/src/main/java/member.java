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
    public void addDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Add the date of death
     * @param dateOfDeath
     */
    public void addDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    
}