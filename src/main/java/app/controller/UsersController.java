package app.controller;

import app.model.User;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.json.JsonUtil;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static app.Application.usersRepository;
import static app.util.RequestUtil.clientAcceptsJson;


public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    public static Object getAll(Request request, Response response) {

        log.info("getAll");

        if (clientAcceptsJson(request)) {
            response.type("application/json");
            return usersRepository.findAll();
        }
        response.status(HttpStatus.NOT_ACCEPTABLE_406);
        return "";
    }

    public static Object getOne(Request request, Response response) {

        long userId = RequestUtil.getParamUserId(request);
        log.info("getOne with id {}", userId);

        if (clientAcceptsJson(request)) {
            response.type("application/json");

            User user = usersRepository.findOne(userId);
            return user;
        }
        response.status(HttpStatus.NOT_ACCEPTABLE_406);
        return "";
    }

    public static Object create(Request request, Response response) {

        User user = JsonUtil.readValue(request.body(), User.class);
        log.info("create {}", user);

        if (clientAcceptsJson(request)) {
            response.type("application/json");

            ValidationUtil.checkIsNew(user);
            User saved = usersRepository.save(user);
            response.status(HttpStatus.CREATED_201);
            return saved;
        }
        response.status(HttpStatus.NOT_ACCEPTABLE_406);
        return "";
    }

    public static String update(Request request, Response response) {
        User user = JsonUtil.readValue(request.body(), User.class);
        long userId = RequestUtil.getParamUserId(request);

        log.info("update {} with id {}", user, userId);

        ValidationUtil.assureIdConsistency(user, userId);
        usersRepository.save(user);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

    public static String delete(Request request, Response response) {

        long userId = RequestUtil.getParamUserId(request);
        log.info("delete with id {}", userId);

        usersRepository.delete(userId);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

}
