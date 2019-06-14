package app.controller;

import app.dao.UsersDAO;
import app.model.User;
import app.util.ErrorResponder;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.exception.IllegalPayloadException;
import app.util.exception.JsonPayloadParseException;
import app.util.json.JsonPayloadParser;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;

import java.util.List;

@Component
public class UsersController {
    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final JsonPayloadParser<User> jsonPayloadParser = new JsonPayloadParser<>(User.class);

    @Autowired
    private UsersDAO usersDAO;

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
            log.info("Error while processing create request data", e);
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.BAD_REQUEST_400)
                    .message("Error while processing create request data")
                    .cause(e)
                    .haltError();
        } catch (JsonPayloadParseException e) {
            log.info("Error while deserializing update request", e);
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.BAD_REQUEST_400)
                    .message("Error while deserializing update request")
                    .cause(e)
                    .haltError();
        }

        response.status(HttpStatus.CREATED_201);
        return saved;
    }

    public String update(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);

        try {
            User user = jsonPayloadParser.parse(request.body());
            log.info("update {} with id {}", user, userId);
            ValidationUtil.assureIdConsistency(user, userId);
            usersDAO.save(user);
        } catch (IllegalPayloadException e) {
            log.info("User id is inconsistent with user path", e);
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.BAD_REQUEST_400)
                    .message("User id is inconsistent with user path")
                    .cause(e)
                    .haltError();
        } catch (JsonPayloadParseException e) {
            log.info("Error while deserializing update request", e);
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.BAD_REQUEST_400)
                    .message("Error while deserializing update request")
                    .cause(e)
                    .haltError();
        }

        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

    public String delete(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request, log);

        log.info("delete with id {}", userId);

        if (!usersDAO.delete(userId)) {
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.NOT_FOUND_404)
                    .message("User with id " + userId + " not found")
                    .haltError();
        }

        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

}
