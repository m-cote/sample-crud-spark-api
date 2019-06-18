package app.dao;

import app.model.User;
import com.sun.tools.javac.util.List;

public class UserTestData {

    public static final Integer id1 = 1;
    public static final Integer id2 = 2;
    public static final Integer id3 = 3;
    public static final Integer id4 = 4;

    public static final User u1 = new User(id1, "Anna", "Gutkowski");
    public static final User u2 = new User(id2, "Dashawn", "Deckow");
    public static final User u3 = new User(id3, "Joesph", "Cormier");
    public static final User u4 = new User(id4, "Missouri", "Lemke");
    public static final List<User> USERS = List.of(u1, u2, u3, u4);

    public static User created() {
        return new User("Created", "user");
    }

    public static User updated() {
        return new User(id1, "Updated", "user");
    }


}
