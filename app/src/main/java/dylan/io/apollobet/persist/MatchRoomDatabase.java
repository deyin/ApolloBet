package dylan.io.apollobet.persist;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import dylan.io.apollobet.models.Match;

@Database(entities = {Match.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class MatchRoomDatabase extends RoomDatabase {

    private static volatile MatchRoomDatabase instance;

    public static MatchRoomDatabase getInstance(@NonNull final Context context) {
        if (instance == null) {
            synchronized (MatchRoomDatabase.class) {
                if (instance == null) { // double check
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            MatchRoomDatabase.class, "match_database")
                            .addCallback(new RoomDatabase.Callback() {

                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    // download and insert into db
                                    new DownloadMatchAsyncTask(context, instance).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract MatchDao matchDao();


}
