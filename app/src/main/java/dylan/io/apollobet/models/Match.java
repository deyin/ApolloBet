package dylan.io.apollobet.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = MatchDeserializer.class)
public class Match {

    private String id;

    private String number;

    private Date matchTime;

    private Date deadline;

    private Boolean onSale;

    private Boolean hot;

    private String leagueId;
    private String leagueName;

    private String hostTeamId;
    private String hostTeamName;

    private String awayTeamId;
    private String awayTeamName;

    private int spread = 0; // default

    private Map<OddsType, Double> oddsMap = new HashMap<>();

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

    public String getDisplayDeadline() {
        return new SimpleDateFormat("HH:mm").format(getDeadline());
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

    public Map<OddsType, Double> getOddsMap() {
        return oddsMap;
    }

    public void setOddsMap(Map<OddsType, Double> oddsMap) {
        this.oddsMap = oddsMap;
    }
}
