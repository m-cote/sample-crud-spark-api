package app;

import app.controller.UserSettingsController;
import app.controller.UsersController;
import app.dao.UsersDAO;
import app.dao.UsersDAOImpl;
import app.util.json.JsonTransformer;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class Application {

    private static ResponseTransformer jsonTransformer = new JsonTransformer();

    public static void main(String[] args) {

/*
        usersDAO = new MockUsersDAOImpl();
        usersDAO.save(new User("Ivan", "", new UserSettings(true,false), null));
        usersDAO.save(new User("Sidor", "", new UserSettings(true,false), null));
*/
        UsersController usersController = new UsersController(new UsersDAOImpl());
//        UserSettingsController userSettingsController = new UserSettingsController()

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
