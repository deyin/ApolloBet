package dylan.io.apollobet.persist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import dylan.io.apollobet.models.Match;

public class MatchViewModel extends AndroidViewModel {

    private MatchRepository mMatchRepository;

    public MatchViewModel(@NonNull Application application) {
        super(application);
        mMatchRepository = new MatchRepository(application);
    }

    public void insert(Match... matches) {
        mMatchRepository.insert(matches);
    }

    public LiveData<List<Match>> getMatchesByDate(Date start, Date end) {
        LiveData<List<Match>> matchesByDate = mMatchRepository.getMatchesByDate(start, end);
        return matchesByDate;
    }

}
