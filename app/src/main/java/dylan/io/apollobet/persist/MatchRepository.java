package dylan.io.apollobet.persist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import dylan.io.apollobet.models.Match;

public class MatchRepository {
    private MatchDao mMatchDao;

    MatchRepository(Application application) {
        MatchRoomDatabase database = MatchRoomDatabase.getInstance(application);
        mMatchDao = database.matchDao();
    }

    public MatchDao getMatchDao() {
        return mMatchDao;
    }

    public LiveData<List<Match>> getMatchesByDate(Date start, Date end) {
        return mMatchDao.getMatchesByDate(start, end);
    }

    public void insert(Match ... matches) {
        new InsertAsyncTask(mMatchDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Match, Void, Void> {

        final MatchDao matchDao;

        public InsertAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.insert(matches);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Match, Void, Void> {

        final MatchDao matchDao;

        public UpdateAsyncTask(MatchDao matchDao) {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.update(matches);
            return null;
        }
    }

}
