package dylan.io.apollobet.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dylan.io.apollobet.utils.DateUtils;
import dylan.io.apollobet.utils.MatchDeserializer;

@JsonDeserialize(using = MatchDeserializer.class)
@Entity(tableName = "t_match")
public class Match implements Comparable<Match> {

    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey
    private String id = "N/A";

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "match_time")
    private Date matchTime;

    @ColumnInfo(name = "deadline")
    private Date deadline;

    @ColumnInfo(name = "b_on_sale")
    private Boolean onSale;

    @ColumnInfo(name = "b_on_sale_none_spread")
    private Boolean onSaleNoneSpread;

    @ColumnInfo(name = "b_on_sale_spread")
    private Boolean onSaleSpread;

    @ColumnInfo(name = "b_on_sale_score")
    private Boolean onSaleScore;

    @ColumnInfo(name = "b_on_sale_total_goals")
    private Boolean onSaleTotalGoals;

    @ColumnInfo(name = "b_on_sale_half_full")
    private Boolean onSaleHalfFull;

    @ColumnInfo(name = "b_hot_match")
    private Boolean hot;

    @ColumnInfo(name = "league_id")
    private String leagueId;

    @ColumnInfo(name = "league_name")
    private String leagueName;

    @ColumnInfo(name = "league_short_name")
    private String leagueShortName;

    @ColumnInfo(name = "host_team_id")
    private String hostTeamId;

    @ColumnInfo(name = "host_team_name")
    private String hostTeamName;

    @ColumnInfo(name = "host_team_short_name")
    private String hostTeamShortName;

    @ColumnInfo(name = "host_league_order")
    private String hostLeagueOrder;

    @ColumnInfo(name = "away_team_id")
    private String awayTeamId;

    @ColumnInfo(name = "away_team_name")
    private String awayTeamName;

    @ColumnInfo(name = "away_team_short_name")
    private String awayTeamShortName;

    @ColumnInfo(name = "away_league_order")
    private String awayLeagueOrder;

    @ColumnInfo(name = "spread")
    private int spread = 0; // default

    @ColumnInfo(name = "odds_map")
    @TypeConverters({OddsMapConverter.class})
    private Map<OddsType, Odds> oddsMap = new HashMap<>();

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getHostTeamId() {
        return hostTeamId;
    }

    public void setHostTeamId(String hostTeamId) {
        this.hostTeamId = hostTeamId;
    }

    public String getHostTeamName() {
        return hostTeamName;
    }

    public void setHostTeamName(String hostTeamName) {
        this.hostTeamName = hostTeamName;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getSpread() {
        return spread;
    }

    public void setSpread(int spread) {
        this.spread = spread;
    }

    public Map<OddsType, Odds> getOddsMap() {
        return oddsMap;
    }

    public void setOddsMap(Map<OddsType, Odds> oddsMap) {
        this.oddsMap = oddsMap;
    }

    public Boolean getOnSaleSpread() {
        return onSaleSpread;
    }

    public void setOnSaleSpread(Boolean onSaleSpread) {
        this.onSaleSpread = onSaleSpread;
    }

    public Boolean getOnSaleNoneSpread() {
        return onSaleNoneSpread;
    }

    public void setOnSaleNoneSpread(Boolean onSaleNoneSpread) {
        this.onSaleNoneSpread = onSaleNoneSpread;
    }

    public Boolean getOnSaleScore() {
        return onSaleScore;
    }

    public void setOnSaleScore(Boolean onSaleScore) {
        this.onSaleScore = onSaleScore;
    }

    public Boolean getOnSaleTotalGoals() {
        return onSaleTotalGoals;
    }

    public void setOnSaleTotalGoals(Boolean onSaleTotalGoals) {
        this.onSaleTotalGoals = onSaleTotalGoals;
    }

    public Boolean getOnSaleHalfFull() {
        return onSaleHalfFull;
    }

    public void setOnSaleHalfFull(Boolean onSaleHalfFull) {
        this.onSaleHalfFull = onSaleHalfFull;
    }

    public String getLeagueShortName() {
        return leagueShortName;
    }

    public void setLeagueShortName(String leagueShortName) {
        this.leagueShortName = leagueShortName;
    }

    public String getHostTeamShortName() {
        return hostTeamShortName;
    }

    public void setHostTeamShortName(String hostTeamShortName) {
        this.hostTeamShortName = hostTeamShortName;
    }

    public String getHostLeagueOrder() {
        return hostLeagueOrder;
    }

    public void setHostLeagueOrder(String hostLeagueOrder) {
        this.hostLeagueOrder = hostLeagueOrder;
    }

    public String getAwayTeamShortName() {
        return awayTeamShortName;
    }

    public void setAwayTeamShortName(String awayTeamShortName) {
        this.awayTeamShortName = awayTeamShortName;
    }

    public String getAwayLeagueOrder() {
        return awayLeagueOrder;
    }

    public void setAwayLeagueOrder(String awayLeagueOrder) {
        this.awayLeagueOrder = awayLeagueOrder;
    }


    public void put(OddsType type, Odds odds) {
        oddsMap.put(type, odds);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", matchTime=" + matchTime +
                ", deadline=" + deadline +
                ", onSale=" + onSale +
                ", onSaleNoneSpread=" + onSaleNoneSpread +
                ", onSaleSpread=" + onSaleSpread +
                ", onSaleScore=" + onSaleScore +
                ", onSaleTotalGoals=" + onSaleTotalGoals +
                ", onSaleHalfFull=" + onSaleHalfFull +
                ", hot=" + hot +
                ", leagueId='" + leagueId + '\'' +
                ", leagueName='" + leagueName + '\'' +
                ", hostTeamId='" + hostTeamId + '\'' +
                ", hostTeamName='" + hostTeamName + '\'' +
                ", awayTeamId='" + awayTeamId + '\'' +
                ", awayTeamName='" + awayTeamName + '\'' +
                ", spread=" + spread +
                ", oddsMap=" + oddsMap +
                '}';
    }

    @Override
    public int compareTo(@NonNull Match o) {
        return this.matchTime.compareTo(o.matchTime);
    }


    public boolean expired() {
        return new Date().after(matchTime);
    }

    public boolean ongoing() {
        Date now = new Date();
        Date endTime = DateUtils.getDateOfAfterMinutes(matchTime, 90 + 30 + 30 + 10);
        return now.after(matchTime) &&  now.before(endTime);
    }

    public static class OddsMapConverter {

        private static ObjectMapper objectMapper;

        static {
            objectMapper = new ObjectMapper();
        }

        @TypeConverter
        public static Map<OddsType, Odds> jsonToMap(String json) {
            try {
                return objectMapper.readValue(json, new TypeReference<Map<OddsType, Odds>>() {
                });
            } catch (IOException e) {
                Log.e("jsonToMap", e.getMessage() );
            }

            return new HashMap<>();
        }

        @TypeConverter
        public static String mapToJson(Map<OddsType, Odds> map) {
            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                Log.e("mapToJson", e.getMessage() );
            }
            return "{}";
        }
    }

}
