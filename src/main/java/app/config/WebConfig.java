package app.config;

import app.controller.UsersController;
import app.util.json.JsonTransformer;

import static spark.Spark.*;

public class WebConfig {

    private UsersController usersController;

    public WebConfig(UsersController usersController) {
        this.usersController = usersController;
        setupRoutes();
    }

    private void setupRoutes() {

        port(8080);

        before((request, response) -> response.type("application/json"));

        defaultResponseTransformer(new JsonTransformer());

        path("/api", () -> {
            path("/users", () -> {
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
