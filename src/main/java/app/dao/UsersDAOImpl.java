package app.dao;

import app.model.User;
import app.model.UserSettings;
import app.util.ValidationUtil;
import app.util.exception.NotFoundException;

import java.util.List;

public class UsersDAOImpl extends AbstractDao implements UsersDAO {

    @Override
    public List<User> findAll() {

        return doInJPA(em -> {
            setTransactionReadOnly(em);
            return em.createQuery("SELECT u FROM User as u LEFT JOIN FETCH u.settings", User.class).getResultList();
        });
    }

    @Override
    public User findOne(int id) throws NotFoundException {

        return doInJPA(em -> {
            setTransactionReadOnly(em);
            final User user = em.find(User.class, id);
            ValidationUtil.checkNotFoundWithId(user, "User with id " + id + " not found");
            return user;
        });
    }

    @Override
    public User save(User user) {

        return doInJPA(em -> {

            if (user.isNew()) {
                if (user.getSettings() == null) {
                    user.setSettings(UserSettings.getDefault());
                }
                em.persist(user);
                return user;
            } else {
                return em.merge(user);
            }

        });
    }

    @Override
    public void delete(int id) throws NotFoundException {

        doInJPA(em -> {
            final int rowsAffected = em.createQuery("DELETE FROM User u WHERE u.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            ValidationUtil.checkNotFoundWithId(rowsAffected > 0, "User with id " + id + " not found");
        });

    }

}
