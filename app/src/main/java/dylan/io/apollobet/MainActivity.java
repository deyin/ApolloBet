package dylan.io.apollobet;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import dylan.io.apollobet.adapters.DividerItemDecoration;
import dylan.io.apollobet.adapters.MatchAdapter;
import dylan.io.apollobet.listeners.OnOddsSelectedListener;
import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.MatchParent;
import dylan.io.apollobet.models.Odds;
import dylan.io.apollobet.models.OddsType;
import dylan.io.apollobet.persist.DownloadMatchAsyncTask;
import dylan.io.apollobet.persist.MatchRoomDatabase;
import dylan.io.apollobet.persist.MatchViewModel;
import dylan.io.apollobet.utils.DateUtils;

import static java.util.stream.Collectors.groupingBy;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        CompoundButton.OnCheckedChangeListener,
        OnOddsSelectedListener {

    /// UI
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MatchAdapter mAdapter;

    /// Timer
    private Timer mTimer;
    private TimerTask mTimerTask;
    final Handler mTimerHandler = new Handler();
    private static long MAX_TARDINESS = 3 * 60 * 1000; // minutes


    private CheckBox mAutoSelectByOdds;
    private CheckBox mAutoSelectByHandicap;
    private CheckBox mAutoSelectByProfitAndLoss;

    /// MatchViewModel
    private MatchViewModel mMatchViewModel;

    /// Key = Match Id,Value=Match
    private Map<String, Match> selectedMatches = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        initMatchViewModel();
    }

    private void initUi() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.rv_matches);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.recycler_view_divider));
        mAdapter = new MatchAdapter(this, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        mAutoSelectByOdds = findViewById(R.id.cb_auto_select_by_odds);
        mAutoSelectByOdds.setOnCheckedChangeListener(this);

        mAutoSelectByHandicap = findViewById(R.id.cb_auto_select_by_handicap);
        mAutoSelectByHandicap.setOnCheckedChangeListener(this);

        mAutoSelectByProfitAndLoss = findViewById(R.id.cb_auto_select_by_profit_and_loss);
        mAutoSelectByProfitAndLoss.setOnCheckedChangeListener(this);
    }

    private void initMatchViewModel() {
        mMatchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);

        Calendar calendar = Calendar.getInstance(); // today

        Date todayStart = DateUtils.getStartOfDate(calendar);
        Date todayEnd = DateUtils.getEndOfDate(calendar);

        // yesterday matches
        Date yesterday = DateUtils.getDateOfBeforeHours(todayStart, 12);

        // after days matches
        Date afterDays = DateUtils.getDateOfAfterDays(todayEnd, 2);

        mMatchViewModel.getMatchesByDate(yesterday, afterDays).observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {

                List<MatchParent> matchParents = generateMatchParents(matches);

                if (matchParents == null || matchParents.isEmpty()) {
                    return;
                }

                mAdapter.setMatchParents(matchParents, true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - scheduledExecutionTime() >= MAX_TARDINESS) {
                    return;  // Too late; skip this execution.
                }
                mTimerHandler.post(() -> refreshMatches());
            }
        };
        mTimer.schedule(mTimerTask, 0, MAX_TARDINESS);
    }

    private void refreshMatches() {
        Context context = getApplicationContext();
        new DownloadMatchAsyncTask(context, MatchRoomDatabase.getInstance(context)).execute();
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onRefresh() {
        refreshMatches();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private List<MatchParent> generateMatchParents(List<Match> matches) {
        List<MatchParent> matchParents = new ArrayList<>();

        Map<Date, List<Match>> matchMap = groupByDate(matches);

        Set<Map.Entry<Date, List<Match>>> entries = matchMap.entrySet();

        for (Map.Entry<Date, List<Match>> entry : entries) {
            Date date = entry.getKey();
            List<Match> matchList = entry.getValue();

            String strFormattedDate = DateUtils.toString("yyyy/MM/dd", date);
            int dayOfWeek = DateUtils.getDayOfWeek(date) - 1;
            String strFormattedTitle = strFormattedDate + "\t周" + (dayOfWeek == 0 ? "日" : String.valueOf(dayOfWeek)) + "\t共" + matchList.size() +  "场比赛";
            Collections.sort(matchList);
            MatchParent matchParent = new MatchParent(strFormattedTitle, matchList);
            matchParent.setDate(date);
            matchParents.add(matchParent);
        }

        Collections.sort(matchParents);

        return matchParents;
    }

    private Map<Date, List<Match>> groupByDate(List<Match> matches) {
        Map<Date, List<Match>> map = new HashMap<>();
        for (Match m : matches) {
            Date matchTime = m.getMatchTime();
            Date date = DateUtils.getDateOfBeforeHours(matchTime, 12); // taken the next day(before 12:00) matches as today's
            String strKey = DateUtils.toString("yyyy-MM-dd", date);
            try {
                Date dateKey = DateUtils.toDate("yyyy-MM-dd", strKey);
                List<Match> matchList = map.get(dateKey);
                if (matchList == null) {
                    matchList = new ArrayList<>();
                }
                matchList.add(m);
                map.put(dateKey, matchList);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.cb_auto_select_by_odds:
                onAutoSelectByOdds(buttonView.isChecked());
                break;
            case R.id.cb_auto_select_by_handicap:
                onAutoSelectByHandicap(buttonView.isChecked());
                break;
            case R.id.cb_auto_select_by_profit_and_loss:
                onAutoSelectByProfitAndLoss(buttonView.isChecked());
                break;
            default:
                break;
        }
    }

    private void onAutoSelectByOdds(boolean checked) {
        if (checked) {

        } else {

        }
    }

    private void onAutoSelectByHandicap(boolean checked) { // TODO
        if (checked) {

        } else {

        }
    }

    private void onAutoSelectByProfitAndLoss(boolean checked) { // TODO
        if (checked) {

        } else {

        }
    }

    @Override
    public void onOddsSelected(@NonNull Match match, @NonNull OddsType oddsType, boolean selected) {
        final Map<OddsType, Odds> oddsMap = match.getOddsMap();
        oddsMap.get(oddsType).setSelected(selected);

        if (selected) {
            selectedMatches.putIfAbsent(match.getId(), match);
        } else if (selectedMatches.containsKey(match.getId()) && noneOfOddsSelected(oddsMap)) {
            selectedMatches.remove(match.getId());
        }
    }

    private boolean noneOfOddsSelected(Map<OddsType, Odds> oddsMap) {
        Collection<Odds> oddsList = oddsMap.values();
        for (Odds odds : oddsList) {
            if (odds.isSelected()) {
                return false;
            }
        }
        return true;
    }
}
