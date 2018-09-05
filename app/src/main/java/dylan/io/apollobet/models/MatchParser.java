package dylan.io.apollobet.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MatchParser {

    public static Match parse(String json) throws IOException {
        return new ObjectMapper().readValue(json, Match.class);
    }
}
