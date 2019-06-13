package app.controller;

import app.dao.UsersDAO;
import app.model.User;
import app.util.RequestUtil;
import app.util.ValidationUtil;
import app.util.json.JsonUtil;
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

    @Autowired
    private UsersDAO usersDAO;

    public List<User> getAll(Request request, Response response) {

        log.info("getAll");
        return usersDAO.findAll();
    }

    public User getOne(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request);
        log.info("getOne with id {}", userId);

        return usersDAO.findOne(userId);
    }

    public User create(Request request, Response response) {

        User user = JsonUtil.readValue(request.body(), User.class);
        log.info("create {}", user);


        ValidationUtil.checkIsNew(user);
        User saved = usersDAO.save(user);
        response.status(HttpStatus.CREATED_201);
        return saved;
    }

    public String update(Request request, Response response) {
        User user = JsonUtil.readValue(request.body(), User.class);
        int userId = RequestUtil.getParamUserId(request);

        log.info("update {} with id {}", user, userId);

        ValidationUtil.assureIdConsistency(user, userId);
        usersDAO.save(user);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

    public String delete(Request request, Response response) {

        int userId = RequestUtil.getParamUserId(request);
        log.info("delete with id {}", userId);

        usersDAO.delete(userId);
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

}
