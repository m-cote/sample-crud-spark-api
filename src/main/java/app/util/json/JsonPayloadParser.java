package app.util.json;

import app.util.exception.JsonPayloadParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;

public class JsonPayloadParser<PayloadT> {

    private final ObjectMapper objectMapper;
    private final Class<PayloadT> type;

    public JsonPayloadParser(Class<PayloadT> type) {
        this.objectMapper = new ObjectMapper()
                .registerModule(new Jdk8Module());
        this.type = type;
    }

    public PayloadT parse(String text) throws JsonPayloadParseException {
        try {
            return objectMapper.readValue(text, type);
        } catch (IOException | IllegalArgumentException e) {
            throw new JsonPayloadParseException(e);
        }
    }

}
