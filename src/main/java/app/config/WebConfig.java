package app.config;

import app.controller.UserSettingsController;
import app.controller.UsersController;
import app.dao.UserSettingsDAO;
import app.dao.UserSettingsDAOImpl;
import app.dao.UsersDAO;
import app.dao.UsersDAOImpl;
import app.util.ErrorResponder;
import app.util.json.JsonTransformer;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Spark;

import static spark.Spark.*;

@Getter
@Setter
public class WebConfig {

    public static final String API_URL = "/api";
    public static final String USERS_URL = "/users";
    public static final String SETTINGS_URL = "/settings";

    private UsersDAO usersDAO;
    private UsersController usersController;
    private UserSettingsDAO userSettingsDAO;
    private UserSettingsController userSettingsController;

    public void init() {
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
        setupRoutes();
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
                });
            });
        });

        Spark.internalServerError((Request request, Response response) -> ErrorResponder.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .message("Internal Server Error")
                .asString());

    }
}
