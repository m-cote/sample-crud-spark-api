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

import static app.Application.usersDAO;


public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    public static Object getAll(Request request, Response response) {

        log.info("getAll");
        return usersDAO.findAll();
    }

    public static Object getOne(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request);
        log.info("getOne with id {}", userId);

        User user = usersDAO.findOne(userId);
        return user;
    }

    public static Object create(Request request, Response response) {

        User user = JsonUtil.readValue(request.body(), User.class);
        log.info("create {}", user);


        ValidationUtil.checkIsNew(user);
        User saved = usersDAO.save(user);
        response.status(HttpStatus.CREATED_201);
        return saved;
    }

    public static String update(Request request, Response response) {
        User user = JsonUtil.readValue(request.body(), User.class);
        int userId = RequestUtil.getParamUserId(request);

        log.info("update {} with id {}", user, userId);

        ValidationUtil.assureIdConsistency(user, userId);
        usersDAO.save(user);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

    public static String delete(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request);
        log.info("delete with id {}", userId);

        usersDAO.delete(userId);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

}
