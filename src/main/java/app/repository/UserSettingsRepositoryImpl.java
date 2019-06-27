package app.repository;

import app.model.UserSettings;
import app.util.ValidationUtil;
import app.util.exception.NotFoundException;

public class UserSettingsRepositoryImpl extends AbstractRepository implements UserSettingsRepository {
    @Override
    public UserSettings findOne(int id) throws NotFoundException {
        return doInJPA(em -> {
            setTransactionReadOnly(em);
            final UserSettings userSettings = em.find(UserSettings.class, id);
            ValidationUtil.checkNotFoundWithId(userSettings, "User with id " + id + " not found");
            return userSettings;
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
    public void delete(int id) throws NotFoundException {
        doInJPA(em -> {
            final int rowsAffected = em.createQuery("DELETE FROM UserSettings us WHERE us.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            ValidationUtil.checkNotFoundWithId(rowsAffected > 0, "User with id " + id + " not found");
        });
    }
}
