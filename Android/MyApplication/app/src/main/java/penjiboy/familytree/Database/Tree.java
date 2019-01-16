package penjiboy.familytree.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Trees")
public class Tree {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
}
