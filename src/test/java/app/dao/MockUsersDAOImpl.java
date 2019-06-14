package app.dao;

import app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MockUsersDAOImpl implements UsersDAO {

    private static AtomicInteger counter = new AtomicInteger(0);

    Map<Integer, User> map = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public User findOne(int id) {
        return map.get(id);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            map.put(user.getId(), user);
            return user;
        }
        return map.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        return map.remove(id) != null;
    }
}
