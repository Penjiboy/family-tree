package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Member {
    public Name name;
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
}