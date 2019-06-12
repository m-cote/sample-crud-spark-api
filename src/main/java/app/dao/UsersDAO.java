package app.dao;

import app.model.User;

import java.util.List;

public interface UsersDAO {

    List<User> findAll();

    User findOne(int id);

    User save(User user);

    void delete(int id);

}
