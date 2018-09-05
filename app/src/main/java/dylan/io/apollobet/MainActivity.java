package dylan.io.apollobet;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dylan.io.apollobet.adapters.MatchAdapter;
import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.MatchParent;
import dylan.io.apollobet.models.MatchParser;
import dylan.io.apollobet.utils.DateUtils;
import dylan.io.apollobet.utils.SSLUtils;
import dylan.io.apollobet.utils.VolleySingleton;

import static java.util.stream.Collectors.groupingBy;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    /// Volley
    private RequestQueue mRequestQueue;

    /// UI
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MatchAdapter mAdapter;

    /// Timer
    private Timer mTimer;
    private TimerTask mTimerTask;
    final Handler mTimerHandler = new Handler();
    private static long MAX_TARDINESS = 5 * 1000; // 5 minutes


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
                mTimerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshMatchGroups();
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, 0, MAX_TARDINESS);
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
    }

    private void initUi() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.rv_matches);
        mAdapter = new MatchAdapter(this, new ArrayList<MatchParent>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        refreshMatchGroups();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private List<MatchParent> groupByDate(List<Match> matches) {
        List<MatchParent> matchParents = new ArrayList<>();

        Map<Date, List<Match>> matchMap = matches.stream().collect(groupingBy(Match::getDeadline));

        Set<Map.Entry<Date, List<Match>>> entries = matchMap.entrySet();

        for(Map.Entry<Date, List<Match>> entry : entries) {
            Date key = entry.getKey();
            List<Match> value = entry.getValue();

            String strFormattedDate = DateUtils.toString("yyyy/MM/dd", key);
            StringBuilder title = new StringBuilder();
            title.append(strFormattedDate).append("\t").append(value.size());
            MatchParent matchParent = new MatchParent(title.toString(), value);

            matchParents.add(matchParent);
        }

        return matchParents;
    }

    /// curl 'http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode\[\]=hhad&poolcode\[\]=had&poolcode\[\]=crs&poolcode\[\]=ttg&poolcode\[\]=hafu&_=1536046934264'
    /// -H 'Accept-Language: en-US,en;q=0.9'
    /// -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36'
    /// -H 'Accept: */*'
    /// -H 'Referer: http://www.sporttery.cn/'
    /// -H 'Connection: keep-alive'
    private void refreshMatchGroups() {
        String url = "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode=hhad&poolcode=had";
        final List<MatchParent> matchParents = new ArrayList<>(128);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.d("volley.onResponse", response);

                String json = "{}";

                Pattern PATTERN_GET_DATA = Pattern.compile("getData\\((.*)\\);");
                Matcher matcher = PATTERN_GET_DATA.matcher(response);
                if(matcher.find()) {
                    json = matcher.group(1);
                }
                VolleyLog.d("volley.onResponse.json", json);

                List<Match> matches = new ArrayList<>(128);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray ids = data.names();
                    for (int i = 0; i< ids.length(); i++) {
                        String _id = ids.get(i).toString();
                        String jsonMatch = data.getString(_id);
                        Match match = MatchParser.parse(jsonMatch);
                        matches.add(match);
                    }
                } catch (Exception e) {
                    Log.e("JSONObject", e.getMessage());
                }

                List<MatchParent> matchParents = groupByDate(matches);
                mAdapter.updateMatchGroups(matchParents);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("volley.onErrorResponse", error);
            }
        }) {

            /// -H 'Accept-Language: en-US,en;q=0.9'
            /// -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36'
            /// -H 'Accept: */*'
            /// -H 'Referer: http://www.sporttery.cn/'
            /// -H 'Connection: keep-alive'
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept-Language", "en-US,en;q=0.9");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
                headers.put("Accept", "*/*");
                headers.put("Referer", "http://info.sporttery.cn/football/hhad_list.php");
                headers.put("Connection", "keep-alive");
                headers.put("Cookie", "BIGipServerPool_apache_web=1241579786.20480.0000; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221653109b9b94cf-03e4c0ed07ead3-9393265-1764000-1653109b9babcb%22%2C%22%24device_id%22%3A%221653109b9b94cf-03e4c0ed07ead3-9393265-1764000-1653109b9babcb%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E9%90%A9%E5%AD%98%E5%B8%B4%E5%A8%B4%E4%BE%80%E5%99%BA%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E9%8F%88%EE%81%84%E5%BD%87%E9%8D%92%E6%9D%BF%E2%82%AC%E7%B3%AD%E9%90%A9%E5%AD%98%E5%B8%B4%E9%8E%B5%E6%92%B3%E7%B4%91%22%2C%22platForm%22%3A%22information%22%2C%22%24ip%22%3A%22152.62.44.202%22%2C%22source%22%3A%22pc%E7%AB%AF%22%2C%22browser_name%22%3A%22chrome%22%2C%22browser_version%22%3A%2268.0.3440.106%22%2C%22user_gent%22%3A%22Mozilla%2F5.0%20(Windows%20NT%2010.0%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F68.0.3440.106%20Safari%2F537.36%22%2C%22cname%22%3A%22UNITED%20STATES%22%7D%2C%22data_from%22%3A%22information%22%7D; Hm_lvt_860f3361e3ed9c994816101d37900758=1535446849,1535953137,1535954722,1535965595; Hm_lpvt_860f3361e3ed9c994816101d37900758=1536063057");
                return headers;
            }
        };
        SSLUtils.disableSslCertificateValidate(); // disable ssl certificate
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
