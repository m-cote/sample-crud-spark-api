package app.util;

import spark.Request;

public class RequestUtil {

    private RequestUtil() {
    }

    public static long getParamUserId(Request request) {
        String paramId = request.params("userId");
        return paramId == null ? 0 : Long.parseLong(paramId);
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && (accept.contains("application/json") || accept.contains("*/*"));
    }

}
