package app.util.json;

import app.model.Validable;
import app.util.exception.JsonPayloadParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonPayloadParser<PayloadT extends Validable> {

    private final ObjectMapper objectMapper;
    private final Class<PayloadT> type;

    public JsonPayloadParser(Class<PayloadT> type) {
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    public PayloadT parse(String text) throws JsonPayloadParseException {
        try {
            PayloadT payload = objectMapper.readValue(text, type);
            if (payload != null
                    && !payload.isValid()) {
                throw new JsonPayloadParseException("Invalid json payload: " + text);
            }
            return payload;
        } catch (IOException | IllegalArgumentException e) {
            throw new JsonPayloadParseException(e);
        }
    }

}
