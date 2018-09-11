package dylan.io.apollobet.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.Odds;
import dylan.io.apollobet.models.OddsType;
import dylan.io.apollobet.utils.DateUtils;

public class MatchDeserializer extends StdDeserializer<Match> {

    private static Pattern PATTERN_NUM = Pattern.compile("周([一二三四五六日])(.*)");

    public MatchDeserializer() {
        this(null);
    }

    public MatchDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Match deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        Match match = new Match();

        JsonNode root = jp.getCodec().readTree(jp);

        // parse base info of the match
        parseBaseInfo(match, root);

        // none spread odds
        parseWDLOdds(match, root);

        // spread odds
        parseSpreadWDLOdds(match, root);

        // score odds
        parseScoreOdds(match, root);

        // total goals odds
        parseTotalGoalsOdds(match, root);

        // total half/full odds
        parseHalfFullOdds(match, root);

        return match;
    }

    private void parseBaseInfo(Match match, JsonNode node) {
        // id
        match.setId(node.get("id").asText());

        // number
        String num = node.get("num").asText();
        Matcher matcher = PATTERN_NUM.matcher(num);
        if (matcher.find()) {
            String strDayOfWeek = matcher.group(1);
            int numberDayOfWeek = DateUtils.getNumberDayOfWeek(strDayOfWeek);
            match.setNumber(numberDayOfWeek + matcher.group(2));
        }

        // matchTime
        try {
            String date = node.get("date").asText();
            String time = node.get("time").asText();
            Date dateAndTime = DateUtils.toDate("yyyy-MM-ddHH:mm:ss", date + time);
            match.setMatchTime(dateAndTime);
        } catch (ParseException e) {
            Log.w("parseMatchTime", e.getMessage());
        }

        // deadline
        try {
            Date date = DateUtils.toDate("yyyy-MM-dd", node.get("b_date").asText());
            match.setDeadline(date);
        } catch (ParseException e) {
            Log.w("parseDeadline", e.getMessage());
        }

        // onSale
        match.setOnSale("Selling".equals(node.get("status").asText()));

        // hot
        match.setHot("1".equals(node.get("hot").asText()));

        // leagueId
        match.setLeagueId(node.get("l_id").asText());
        // leagueName
        match.setLeagueName(node.get("l_cn").asText().trim());
        // leagueShortName
        match.setLeagueShortName(node.get("l_cn_abbr").asText().trim());

        // hostTeamId
        match.setHostTeamId(node.get("h_id").asText());
        // hostTeamName
        match.setHostTeamName(node.get("h_cn").asText());
        // hostTeamShortName
        match.setHostTeamShortName(node.get("h_cn_abbr").asText());
        // host league order
        match.setHostLeagueOrder(node.get("h_order").asText());

        // awayTeamId
        match.setAwayTeamId(node.get("a_id").asText());
        // awayTeamName
        match.setAwayTeamName(node.get("a_cn").asText());
        // awayTeamShortName
        match.setAwayTeamShortName(node.get("a_cn_abbr").asText());
        // away league order
        match.setAwayLeagueOrder(node.get("a_order").asText());
    }

    private void parseHalfFullOdds(Match match, JsonNode node) {
        JsonNode hafuNode = node.get("hafu");
        match.setOnSaleHalfFull(hafuNode != null && "Selling".equals(hafuNode.get("p_status").asText()));
        if (match.getOnSaleHalfFull()) {
            List<OddsType> halfFullTypes = OddsType.getHalfFullTypes();
            for (OddsType halfFullType : halfFullTypes) {
                JsonNode jsonNode = hafuNode.get(halfFullType.getCode());
                if (jsonNode == null || jsonNode.asText().isEmpty()) {
                    Log.w("parseHalfFullOdds", halfFullType.getCode() + " value is empty");
                    continue;
                }
                match.put(halfFullType, new Odds(halfFullType, jsonNode.asDouble()));
            }
        }
    }

    private void parseTotalGoalsOdds(Match match, JsonNode node) {
        JsonNode ttgNode = node.get("ttg");
        match.setOnSaleTotalGoals(ttgNode != null && "Selling".equals(ttgNode.get("p_status").asText()));
        if (match.getOnSaleTotalGoals()) {
            List<OddsType> totalGoalsTypes = OddsType.getTotalGoalsTypes();
            for (OddsType totalGoalsType : totalGoalsTypes) {
                JsonNode jsonNode = ttgNode.get(totalGoalsType.getCode());
                if (jsonNode == null || jsonNode.asText().isEmpty()) {
                    Log.w("parseTotalGoalsOdds", totalGoalsType.getCode() + " value is empty");
                    continue;
                }
                match.put(totalGoalsType, new Odds(totalGoalsType, jsonNode.asDouble()));
            }
        }
    }

    private void parseScoreOdds(Match match, JsonNode node) {
        JsonNode crsNode = node.get("crs");
        match.setOnSaleScore(crsNode != null && "Selling".equals(crsNode.get("p_status").asText()));
        if (match.getOnSaleScore()) {
            List<OddsType> scoreTypes = OddsType.getScoreTypes();
            for (OddsType scoreType : scoreTypes) {
                JsonNode jsonNode = crsNode.get(scoreType.getCode());
                if (jsonNode == null || jsonNode.asText().isEmpty()) {
                    Log.w("parseScoreOdds", scoreType.getCode() + " value is empty");
                    continue;
                }
                match.put(scoreType, new Odds(scoreType, jsonNode.asDouble()));
            }
        }
    }

    private void parseSpreadWDLOdds(Match match, JsonNode node) {
        JsonNode hhadNode = node.get("hhad");
        match.setOnSaleSpread(hhadNode != null && "Selling".equals(hhadNode.get("p_status").asText()));
        if (match.getOnSaleSpread()) {
            match.setSpread(hhadNode.get("fixedodds").asInt());
            List<OddsType> spreadWDLTypes = OddsType.getSpreadWDLTypes();
            for (OddsType spreadWDLType : spreadWDLTypes) {
                JsonNode jsonNode = hhadNode.get(spreadWDLType.getCode().replace("s", ""));
                if (jsonNode == null || jsonNode.asText().isEmpty()) {
                    Log.w("parseSpreadWDLOdds", spreadWDLType.getCode() + " value is empty");
                    continue;
                }
                match.put(spreadWDLType, new Odds(spreadWDLType, jsonNode.asDouble()));
            }
        }
    }

    private void parseWDLOdds(Match match, JsonNode node) {
        JsonNode hadNode = node.get("had");
        match.setOnSaleNoneSpread(hadNode != null && "Selling".equals(hadNode.get("p_status").asText()));
        if (match.getOnSaleNoneSpread()) {
            List<OddsType> noneSpreadWDLTypes = OddsType.getNoneSpreadWDLTypes();
            for (OddsType noneSpreadWDLType : noneSpreadWDLTypes) {
                JsonNode jsonNode = hadNode.get(noneSpreadWDLType.getCode());
                if (jsonNode == null || jsonNode.asText().isEmpty()) {
                    Log.w("parseWDLOdds", noneSpreadWDLType.getCode() + " value is empty");
                    continue;
                }
                match.put(noneSpreadWDLType, new Odds(noneSpreadWDLType, jsonNode.asDouble()));
            }
        }
    }
}