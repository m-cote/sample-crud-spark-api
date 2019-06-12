package app.controller;

import app.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class UserSettingsController {
    private static final Logger log = LoggerFactory.getLogger(UserSettingsController.class);

    public static Object getOne(Request request, Response response) {
        int userId = RequestUtil.getParamUserId(request);
        log.info("getOne with id {}", userId);

        return "";
    }

}
