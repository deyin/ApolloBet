package dylan.io.apollobet.models;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dylan.io.apollobet.utils.MatchDeserializer;

@JsonDeserialize(using = MatchDeserializer.class)
public class Match implements Comparable<Match> {

    private int parentPosition = -1;

    private String id;

    private String number;

    private Date matchTime;

    private Date deadline;

    private Boolean onSale;
    private Boolean onSaleNoneSpread;
    private Boolean onSaleSpread;
    private Boolean onSaleScore;
    private Boolean onSaleTotalGoals;
    private Boolean onSaleHalfFull;

    private Boolean hot;

    private String leagueId;
    private String leagueName;
    private String leagueShortName;


    private String hostTeamId;
    private String hostTeamName;
    private String hostTeamShortName;
    private String hostLeagueOrder;

    private String awayTeamId;
    private String awayTeamName;
    private String awayTeamShortName;
    private String awayLeagueOrder;

    private int spread = 0; // default

    private Map<OddsType, Odds> oddsMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
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
}
