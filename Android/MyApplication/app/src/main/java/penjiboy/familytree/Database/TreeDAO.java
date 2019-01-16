package penjiboy.familytree.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TreeDAO {
    @Insert
    public void insertTrees(Tree... trees);

    @Update
    public void updateTrees(Tree... trees);

    @Delete
    public void deleteTrees(Tree... trees);

    @Query("SELECT * FROM Trees WHERE id IS :treeId")
    public Tree getTreeFromId(int treeId);

    @Query("SELECT * FROM Trees")
    public List<Tree> getAllTrees();
}
