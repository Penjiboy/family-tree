package penjiboy.familytree.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Defining the Member table
 */
@Entity(tableName = "Members")
public class Member {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int treeId;
    public String name;
    public Date dateOfBirth;
    public Date dateOfDeath;
    public String gender;
    public List<Integer> spouseIds;
    public List<Integer> children;
    public List<Integer> parents;
    public List<Integer> siblings;
//    public List<String> extraNotes;

    @Ignore
    Bitmap picture;

    @Ignore
    public Tree tree;

    public enum Gender {
        male, female
    }
}
