package app.repository;

import app.model.UserAttribute;
import app.util.exception.NotFoundException;

import java.util.List;

public interface UserAttributesRepository {

    List<UserAttribute> findAll(int userId);

    UserAttribute findOne(int userId, String key) throws NotFoundException;

    void save(int userId, List<UserAttribute> attributes);

    void delete(int userId, String key) throws NotFoundException;

}
