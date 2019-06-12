package app.util;

import spark.Request;

public class RequestUtil {

    private RequestUtil() {
    }

    public static int getParamUserId(Request request) {
        String paramId = request.params("userId");
        return paramId == null ? 0 : Integer.parseInt(paramId);
    }

}
