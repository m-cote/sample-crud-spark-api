package app.dao;

import app.model.UserAttribute;

import java.util.Arrays;
import java.util.List;

public class UserAttributesTestData {

    private UserAttributesTestData() {
    }

    private static final Integer userId = UserTestData.id1;

    public static final UserAttribute ua1 = new UserAttribute(userId, "email", "test@email.com");
    public static final UserAttribute ua2 = new UserAttribute(userId, "phone", "+380990001111");

    public static final List<UserAttribute> USER_ATTRIBUTES = Arrays.asList(ua1, ua2);

    public static UserAttribute created() {
        return new UserAttribute(userId, "comment", "just created");
    }

    public static UserAttribute updated() {
        return new UserAttribute(userId, "email", "just updated");
    }

}
