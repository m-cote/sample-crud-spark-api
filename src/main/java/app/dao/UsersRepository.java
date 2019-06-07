package app.dao;

import app.model.User;

import java.util.List;

public interface UsersRepository {

    List<User> findAll();

    User findOne(long id);

    User save(User user);

    void delete(long id);
}
