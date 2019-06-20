package app.dao;

import app.model.UserSettings;
import app.util.exception.NotFoundException;

public interface UserSettingsDAO {

    UserSettings findOne(int id) throws NotFoundException;

    UserSettings save(UserSettings settings);

    void delete(int id) throws NotFoundException;

}
