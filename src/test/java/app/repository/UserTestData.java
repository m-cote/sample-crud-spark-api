package app.repository;

import app.model.User;
import app.util.JpaUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserTestData {

    private UserTestData() {
    }

    public static final Integer id1 = 1;
    public static final Integer id2 = 2;
    public static final Integer id3 = 3;
    public static final Integer id4 = 4;

    public static final User u1 = new User(id1, "Anna", "Gutkowski");
    public static final User u2 = new User(id2, "Dashawn", "Deckow");
    public static final User u3 = new User(id3, "Joesph", "Cormier");
    public static final User u4 = new User(id4, "Missouri", "Lemke");
    public static final List<User> USERS = Arrays.asList(u1, u2, u3, u4);

    public static User created() {
        return new User("Created", "user");
    }

    public static User updated() {
        return new User(id1, "Updated", "user");
    }

    public static void populateDb(UsersRepository usersRepository) {

        if (usersRepository instanceof InMemoryUsersRepositoryImpl) {
            Map<Integer, User> inMemoryMap = ((InMemoryUsersRepositoryImpl) usersRepository).map;
            inMemoryMap.clear();
            USERS.forEach(user -> inMemoryMap.put(user.getId(), user));
        } else {

            for (User user : USERS) {

                Transaction tx = null;
                try (Session session = JpaUtil.openSession()) {
                    tx = session.beginTransaction();
                    session.save(user);
                    tx.commit();
                } catch (Exception e) {
                    tx.rollback();
                }
            }
        }

    }

}
