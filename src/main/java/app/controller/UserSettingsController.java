package app.controller;

import app.dao.UserSettingsDAO;
import app.model.UserSettings;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.exception.IllegalPayloadException;
import app.util.exception.JsonPayloadParseException;
import app.util.json.JsonPayloadParser;
import spark.Request;
import spark.Response;

public class UserSettingsController extends AbstractController {

    private final JsonPayloadParser<UserSettings> jsonPayloadParser = new JsonPayloadParser<>(UserSettings.class);

    private UserSettingsDAO userSettingsDAO;

    public UserSettingsController(UserSettingsDAO userSettingsDAO) {
        this.userSettingsDAO = userSettingsDAO;
    }

    public UserSettings getOne(Request request, Response response) {
        int userId = RequestUtil.getParamUserId(request, log);
        log.info("getOne with id {}", userId);

        final UserSettings settings = userSettingsDAO.findOne(userId);
        return settings;
    }

    public String update(Request request, Response response) {

        int id = RequestUtil.getParamUserId(request, log);

        try {
            UserSettings userSettings = jsonPayloadParser.parse(request.body());
            log.info("update {} with id {}", userSettings, id);
            ValidationUtil.setEntityId(userSettings, id);
            userSettingsDAO.save(userSettings);
        } catch (IllegalPayloadException e) {
            badRequestResponse("User id is inconsistent with user path", e);
        } catch (JsonPayloadParseException e) {
            badRequestResponse("Error while deserializing update request", e);
        }

        return noContentResponse(response);
    }

}
