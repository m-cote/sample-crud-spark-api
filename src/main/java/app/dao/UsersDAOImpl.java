package app.dao;

import app.model.User;

import java.util.List;

public class UsersDAOImpl extends AbstractDao implements UsersDAO {

    @Override
    public List<User> findAll() {

        return doInJPA(em -> {
            setTransactionReadOnly(em);
            return em.createQuery("SELECT u FROM User as u", User.class).getResultList();
        });
    }

    @Override
    public User findOne(int id) {

        return doInJPA(em -> {
            setTransactionReadOnly(em);
            return em.find(User.class, id);
        });
    }

    @Override
    public User save(User user) {

        return doInJPA(em -> {

            if (user.isNew()) {
                em.persist(user);
                return user;
            } else {
                return em.merge(user);
            }

        });
    }

    @Override
    public boolean delete(int id) {

        return doInJPA(em -> {
            return em.createQuery("DELETE FROM User u WHERE u.id=:id")
                    .setParameter("id", id)
                    .executeUpdate() > 0;
        });

    }

}
