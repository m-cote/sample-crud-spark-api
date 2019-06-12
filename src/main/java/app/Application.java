package app;

import app.controller.UserSettingsController;
import app.controller.UsersController;
import app.dao.UsersDAO;
import app.dao.UsersDAOImpl;
import app.util.json.JsonTransformer;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class Application {

    public static UsersDAO usersDAO;

    private static ResponseTransformer jsonTransformer = new JsonTransformer();

    public static void main(String[] args) {

/*
        usersDAO = new MockUsersDAOImpl();
        usersDAO.save(new User("Ivan", "", new UserSettings(true,false), null));
        usersDAO.save(new User("Sidor", "", new UserSettings(true,false), null));
*/
        usersDAO = new UsersDAOImpl();


        port(8080);

        before((request, response) -> response.type("application/json"));

        path("/api", () -> {
            path("/users", () -> {
                get("", UsersController::getAll, jsonTransformer);
                get("/:userId", UsersController::getOne, jsonTransformer);
                post("", "application/json", UsersController::create, jsonTransformer);
                put("/:userId", "application/json", UsersController::update);
                delete("/:userId", UsersController::delete);

                get("/:userId/settings", UserSettingsController::getOne, jsonTransformer);
            });
        });
    }

}
