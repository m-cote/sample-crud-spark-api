package app.dao;

import app.model.UserSettings;

import static app.dao.UserTestData.id1;

public class UserSettingsTestData {

    private UserSettingsTestData() {
    }

    public static final UserSettings us1 = new UserSettings(id1, null, true, true);

    public static UserSettings updated() {
        return new UserSettings(false, true);
    }

}
