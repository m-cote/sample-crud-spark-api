package app.controller;

import app.model.UserSettings;
import app.repository.UserSettingsRepository;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.exception.IllegalPayloadException;
import app.util.exception.JsonPayloadParseException;
import app.util.json.JsonPayloadParser;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserSettingsController extends AbstractController {

    private final JsonPayloadParser<UserSettings> jsonPayloadParser = new JsonPayloadParser<>(UserSettings.class);
    static final String ALLOWED_METHODS = "GET, HEAD, PUT";

    private UserSettingsRepository userSettingsRepository;

    public UserSettingsController(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings getOne(Request request, Response response) {
        int userId = RequestUtil.getParamUserId(request, log);
        log.info("getOne with id {}", userId);

        return userSettingsRepository.findOne(userId);
    }

    public String update(Request request, Response response) {

        int id = RequestUtil.getParamUserId(request, log);

        try {
            UserSettings userSettings = jsonPayloadParser.parse(request.body());
            log.info("update {} with id {}", userSettings, id);
            ValidationUtil.setEntityId(userSettings, id);
            userSettingsRepository.save(userSettings);
        } catch (IllegalPayloadException e) {
            badRequestErrorResponse("User id is inconsistent with user path", e);
        } catch (JsonPayloadParseException e) {
            badRequestErrorResponse("Error while deserializing update request", e);
        }

        return noContentResponse(response);
    }

    public Route methodNotAllowed() {
        return (request, response) -> {
            response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
            response.header("Allow", ALLOWED_METHODS);
            return "";
        };
    }

}
