package app.config;

import app.controller.UsersController;
import app.util.json.JsonTransformer;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class WebConfig {

    private UsersController usersController;

    public WebConfig(UsersController usersController) {
        this.usersController = usersController;
        setupRoutes();
    }

    private void setupRoutes() {

        ResponseTransformer jsonTransformer = new JsonTransformer();

        port(8080);

        before((request, response) -> response.type("application/json"));

        path("/api", () -> {
            path("/users", () -> {
                get("", usersController::getAll, jsonTransformer);
                get("/:userId", usersController::getOne, jsonTransformer);
                post("", "application/json", usersController::create, jsonTransformer);
                put("/:userId", "application/json", usersController::update);
                delete("/:userId", usersController::delete);

                //get("/:userId/settings", UserSettingsController::getOne, jsonTransformer);
            });
        });

    }
}
