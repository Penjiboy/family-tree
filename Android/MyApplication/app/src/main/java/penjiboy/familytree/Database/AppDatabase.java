package penjiboy.familytree.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

@Database(entities = {Member.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "app-db";

    public abstract MemberDAO memberDAO();

    public static AppDatabase getsInstance(final Context context){
        if(sInstance == null) {
            synchronized (AppDatabase.class) {
                if(sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. 
     */
}
