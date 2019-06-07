package app.dao;

import app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MockUsersRepositoryImpl implements UsersRepository{

    private static AtomicLong counter = new AtomicLong(0);

    Map<Long, User> map = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public User findOne(long id) {
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
    public void delete(long id) {
        map.remove(id);
    }
}
