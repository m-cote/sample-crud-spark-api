package app.controller;

import app.dao.UserAttributesDAO;
import app.model.UserAttribute;
import app.util.RequestUtil;
import app.util.exception.JsonPayloadParseException;
import app.util.json.JsonPayloadParser;
import app.util.to.UserAttributesUtil;
import spark.Request;
import spark.Response;

import java.util.List;

public class UserAttributesController extends AbstractController {

    private final JsonPayloadParser<UserAttributesPayload> jsonPayloadParser = new JsonPayloadParser<>(UserAttributesPayload.class);

    private UserAttributesDAO userAttributesDAO;

    public UserAttributesController(UserAttributesDAO userAttributesDAO) {
        this.userAttributesDAO = userAttributesDAO;
    }

    public UserAttributesPayload getAll(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);
        log.info("getAll with user id {}", userId);
        return UserAttributesUtil.asPayload(userAttributesDAO.findAll(userId));
    }

    public UserAttributesPayload getOne(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);
        String attributeKey = RequestUtil.getParamAttributeKey(request, log);
        log.info("getOne with user id {} and key {}", userId, attributeKey);

        return UserAttributesUtil.asPayload(userAttributesDAO.findOne(userId, attributeKey));
    }

    public String save(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);
        try {
            UserAttributesPayload userAttributesPayload = jsonPayloadParser.parse(request.body());
            log.info("save {}", userAttributesPayload);

            final List<UserAttribute> attributes = UserAttributesUtil.fromPayload(userAttributesPayload, userId);
            userAttributesDAO.save(userId, attributes);
        } catch (JsonPayloadParseException e) {
            badRequestErrorResponse("Error while deserializing create request", e);
        }

        return noContentResponse(response);
    }

    public String delete(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);
        String attributeKey = RequestUtil.getParamAttributeKey(request, log);

        log.info("delete with user id {} and key {}", userId, attributeKey);

        userAttributesDAO.delete(userId, attributeKey);

        return noContentResponse(response);
    }
}
