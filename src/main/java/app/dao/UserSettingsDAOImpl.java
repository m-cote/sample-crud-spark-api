package app.dao;

import app.model.UserSettings;

public class UserSettingsDAOImpl extends AbstractDao implements UserSettingsDAO {
    @Override
    public UserSettings findOne(int id) {
        return doInJPA(em -> {
            setTransactionReadOnly(em);
            return em.find(UserSettings.class, id);
        });
    }

    @Override
    public UserSettings save(UserSettings settings) {

        return doInJPA(em -> {

            if (settings.isNew()) {
                em.persist(settings);
                return settings;
            } else {
                return em.merge(settings);
            }

        });

    }

    @Override
    public boolean delete(int id) {
        return doInJPA(em -> {
            return em.createQuery("DELETE FROM UserSettings us WHERE us.id=:id")
                    .setParameter("id", id)
                    .executeUpdate() > 0;
        });
    }
}
