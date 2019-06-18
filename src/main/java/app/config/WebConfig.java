package app.config;

import app.controller.UsersController;
import app.dao.UsersDAO;
import app.dao.UsersDAOImpl;
import app.util.json.JsonTransformer;
import lombok.Getter;
import lombok.Setter;

import static spark.Spark.*;

@Getter
@Setter
public class WebConfig {

    public static final String API_URL = "/api";
    public static final String USERS_URL = "/users";

    private UsersDAO usersDAO;
    private UsersController usersController;

    public void init() {
        if (usersDAO == null) {
            usersDAO = new UsersDAOImpl();
        }
        if (usersController == null) {
            usersController = new UsersController(usersDAO);
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

                //get("/:userId/settings", UserSettingsController::getOne, jsonTransformer);
            });
        });

    }
}
