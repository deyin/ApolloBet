package dylan.io.apollobet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import dylan.io.apollobet.models.Match;

public class MatchParser {

    public static Match parse(String json) throws IOException {
        return new ObjectMapper().readValue(json, Match.class);
    }
}
