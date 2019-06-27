package app.repository;

import app.model.User;
import app.util.exception.NotFoundException;

import java.util.List;

public interface UsersRepository {

    List<User> findAll();

    User findOne(int id) throws NotFoundException;

    User save(User user);

    void delete(int id) throws NotFoundException;

}
