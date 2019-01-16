package penjiboy.familytree.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

/**
 * All the operations we want to be able to be able to perform on the Member table
 */

@Dao
public interface MemberDAO {
    @Insert
    public void insertMembers(Member... members);

    @Update
    public void updateMembers(Member... members);

    @Delete
    public void deleteMembers(Member... members);

    @Query("SELECT * FROM Members WHERE id IS :memberId")
    public Member getMemberFromId(int memberId);

    @Query("SELECT * FROM Members")
    public List<Member> getAllMembers();

    @Query("SELECT * FROM Members WHERE name LIKE :search")
    public List<Member> findUserByName(String search);
}
