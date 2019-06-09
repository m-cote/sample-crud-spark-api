package app.util.json;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) throws Exception {
        return JsonUtil.writeValue(model);
    }
}
