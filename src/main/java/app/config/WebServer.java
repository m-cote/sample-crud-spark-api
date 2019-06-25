package app.config;

import app.controller.UserAttributesController;
import app.controller.UserSettingsController;
import app.controller.UsersController;
import app.dao.*;
import app.util.ErrorResponder;
import app.util.exception.NotFoundException;
import app.util.json.JsonTransformer;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static spark.Spark.*;

@Getter
@Setter
public class WebServer {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);

    public static final String API_URL = "/api";
    public static final String USERS_URL = "/users";
    public static final String SETTINGS_URL = "/settings";
    public static final String ATTRIBUTES_URL = "/attributes";

    private UsersDAO usersDAO;
    private UsersController usersController;
    private UserSettingsDAO userSettingsDAO;
    private UserSettingsController userSettingsController;
    private UserAttributesDAO userAttributesDAO;
    private UserAttributesController userAttributesController;

    public void start() {
        initBeans();
        setupRoutes();
        configureExceptionHandling();
    }

    private void initBeans() {
        if (usersDAO == null) {
            usersDAO = new UsersDAOImpl();
        }
        if (usersController == null) {
            usersController = new UsersController(usersDAO);
        }
        if (userSettingsDAO == null) {
            userSettingsDAO = new UserSettingsDAOImpl();
        }
        if (userSettingsController == null) {
            userSettingsController = new UserSettingsController(userSettingsDAO);
        }
        if (userAttributesDAO == null) {
            userAttributesDAO = new UserAttributesDAOImpl();
        }
        if (userAttributesController == null) {
            userAttributesController = new UserAttributesController(userAttributesDAO);
        }
    }

    private void setupRoutes() {

        port(8080);

        before((request, response) -> response.type("application/json"));

        defaultResponseTransformer(new JsonTransformer());

        path(API_URL, () -> {
            path(USERS_URL, () -> {
                get("", usersController::getAll);
                get("/:userId", usersController::getOne);
                post("", "application/json", usersController::create);
                put("/:userId", "application/json", usersController::update);
                delete("/:userId", usersController::delete);

                path("/:userId" + SETTINGS_URL, () -> {
                    get("", userSettingsController::getOne);
                    put("", "application/json", userSettingsController::update);
                    post("", userSettingsController.methodNotAllowed());
                    delete("", userSettingsController.methodNotAllowed());
                });

                path("/:userId" + ATTRIBUTES_URL, () -> {
                    get("", userAttributesController::getAll);
                    get("/:attributeKey", userAttributesController::getOne);
                    post("", userAttributesController::save);
                    delete("/:attributeKey", userAttributesController::delete);
                });
            });
        });

    }

    private void configureExceptionHandling() {
        internalServerError((request, response) -> ErrorResponder.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .message("Internal Server Error")
                .asString());

        notFound((req, res) -> ErrorResponder.builder()
                .statusCode(NOT_FOUND_404)
                .message(String.format("%s %s can not be found", req.requestMethod(), req.pathInfo()))
                .asString());

        exception(NotFoundException.class, (ex, req, res) -> {
            log.info(ex.getMessage(), ex);
            res.status(NOT_FOUND_404);
            res.body(ErrorResponder.builder()
                    .statusCode(NOT_FOUND_404)
                    .message("Entity not found")
                    .cause(ex)
                    .asString());
        });

    }

}
