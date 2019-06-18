package app.dao;

import app.model.UserSettings;

public interface UserSettingsDAO {

    UserSettings findOne(int id);

    UserSettings save(UserSettings settings);

    boolean delete(int id);

}
