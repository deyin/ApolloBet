package dylan.io.apollobet.persist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import dylan.io.apollobet.models.Match;

@Dao
public interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Match... match);

    @Update
    void update(Match... matches);

    @Query("SELECT * FROM t_match WHERE match_time BETWEEN :start AND :end")
    LiveData<List<Match>> getMatchesByDate(Date start, Date end);

}
