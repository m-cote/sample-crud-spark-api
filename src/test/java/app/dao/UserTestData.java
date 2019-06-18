package app.dao;

import app.model.User;
import com.sun.tools.javac.util.List;

public class UserTestData {

    public static final List<User> USERS =  List.of(new User("Anna", "Gutkowski"),
                                                    new User("Dashawn", "Deckow"),
                                                    new User("Joesph", "Cormier"),
                                                    new User("Missouri", "Lemke")
                                            );

}
