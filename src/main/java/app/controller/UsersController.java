package app.controller;

import app.dao.UsersDAO;
import app.model.User;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.exception.IllegalPayloadException;
import app.util.exception.JsonPayloadParseException;
import app.util.json.JsonPayloadParser;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.List;

public class UsersController extends AbstractController {

    private final JsonPayloadParser<User> jsonPayloadParser = new JsonPayloadParser<>(User.class);

    private UsersDAO usersDAO;

    public UsersController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public List<User> getAll(Request request, Response response) {

        log.info("getAll");
        return usersDAO.findAll();
    }

    public User getOne(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);
        log.info("getOne with id {}", userId);

        return usersDAO.findOne(userId);
    }

    public User create(Request request, Response response) {

        User saved = null;
        try {
            User user = jsonPayloadParser.parse(request.body());
            log.info("create {}", user);

            ValidationUtil.checkIsNew(user);
            saved = usersDAO.save(user);
        } catch (IllegalPayloadException e) {
            badRequestErrorResponse("Error while processing create request data", e);
        } catch (JsonPayloadParseException e) {
            badRequestErrorResponse("Error while deserializing create request", e);
        }

        response.status(HttpStatus.CREATED_201);
        return saved;
    }

    public String update(Request request, Response response) {

        int id = RequestUtil.getParamUserId(request, log);

        try {
            User user = jsonPayloadParser.parse(request.body());
            log.info("update {} with id {}", user, id);
            ValidationUtil.setEntityId(user, id);
            usersDAO.save(user);
        } catch (IllegalPayloadException e) {
            badRequestErrorResponse("User id is inconsistent with user path", e);
        } catch (JsonPayloadParseException e) {
            badRequestErrorResponse("Error while deserializing update request", e);
        }

        return noContentResponse(response);
    }

    public String delete(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);

        log.info("delete with id {}", userId);

        usersDAO.delete(userId);

        return noContentResponse(response);
    }

}
