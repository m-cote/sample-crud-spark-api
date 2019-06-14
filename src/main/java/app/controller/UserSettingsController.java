package app.controller;

import app.model.UserSettings;
import app.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class UserSettingsController {
    private final Logger log = LoggerFactory.getLogger(UserSettingsController.class);

    public UserSettings getOne(Request request, Response response) {
        int userId = RequestUtil.getParamUserId(request, log);
        log.info("getOne with id {}", userId);

        return null;
    }

}
