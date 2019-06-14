package app.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import spark.ResponseTransformer;

import static app.util.json.JacksonObjectMapper.getMapper;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) throws JsonProcessingException {
        return getMapper().writeValueAsString(model);
    }
}
