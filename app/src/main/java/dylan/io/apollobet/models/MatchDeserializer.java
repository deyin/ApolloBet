package dylan.io.apollobet.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class MatchDeserializer extends StdDeserializer<Match> {

    public MatchDeserializer() {
        this(null);
    }

    public MatchDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Match deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return null; // TODO
    }
}