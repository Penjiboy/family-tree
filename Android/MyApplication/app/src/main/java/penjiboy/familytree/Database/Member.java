package penjiboy.familytree.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Calendar;
import java.util.List;


/**
 * Defining the Member table
 */
@Entity(tableName = "Members")
public class Member {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String dateOfBirth;
    public String dateOfDeath;
    public String gender;
//    public List<Member> spouse;
//    public List<Member> children;
//    public List<Member> parents;
//    public List<Member> siblings;
//    public List<String> extraNotes;

    @Ignore
    Bitmap picture;

    public enum Gender {
        male, female
    }
}
