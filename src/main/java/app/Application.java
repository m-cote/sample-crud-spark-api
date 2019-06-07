package app;

import app.controller.UsersController;
import app.dao.MockUsersRepositoryImpl;
import app.dao.UsersRepository;
import app.model.Settings;
import app.model.User;

import static spark.Spark.*;

public class Application {

    public static UsersRepository usersRepository;

    public static void main(String[] args) {

        usersRepository = new MockUsersRepositoryImpl();
        usersRepository.save(new User(new Settings(true,false)));
        usersRepository.save(new User(new Settings(false,true)));


        port(8080);

        path("/api", () -> {
            path("/users", () -> {
                get("", UsersController::getAll);
                get("/:userId", UsersController::getOne);
                get("/:userId/attributes", (request, response) -> "user attributes");
                get("/:userId/settings", (request, response) -> "user settings");
                post("", UsersController::create);
                put("/:userId", UsersController::update);
                delete("/:userId", UsersController::delete);

            });
        });
    }

}
