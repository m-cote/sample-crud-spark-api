package app.repository;

import app.model.UserAttribute;
import app.util.ValidationUtil;
import app.util.exception.NotFoundException;

import java.sql.PreparedStatement;
import java.util.List;

public class UserAttributesRepositoryImpl extends AbstractRepository implements UserAttributesRepository {

    @Override
    public List<UserAttribute> findAll(int userId) {

        return doInJPA(em -> {
            return em.createQuery("SELECT ua FROM UserAttributes as ua WHERE ua.id.userId=:userId", UserAttribute.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }, true);
    }

    @Override
    public UserAttribute findOne(int userId, String key) throws NotFoundException {

        return doInJPA(em -> {

            final UserAttribute.Id id = new UserAttribute.Id(userId, key);
            final UserAttribute userAttribute = em.find(UserAttribute.class, id);
            ValidationUtil.checkNotFoundWithId(userAttribute, "Attribute with id " + id + " not found");

            return userAttribute;
        }, true);
    }


    @Override
    public void save(int userId, List<UserAttribute> attributeList) {

        doInHibernateSession(connection -> {

            String sql =
                    "INSERT INTO user_attributes " +
                    "(user_id, `key`, value) " +
                    "VALUES " +
                    "(?,?,?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "`value` = ?; ";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int i = 1;
                for(UserAttribute userAttribute : attributeList) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, userAttribute.getKey());
                    final String value = userAttribute.getValue();
                    preparedStatement.setString(3, value);
                    preparedStatement.setString(4, value);

                    preparedStatement.addBatch();
                    //Batch size: 20
                    if (i % 20 == 0) {
                        preparedStatement.executeBatch();
                    }
                    i++;
                }
                preparedStatement.executeBatch();
            }
        });

    }

    @Override
    public void delete(int userId, String key) throws NotFoundException {

        doInJPA(em -> {
            final UserAttribute.Id id = new UserAttribute.Id(userId, key);
            final int rowsAffected = em.createQuery("DELETE FROM UserAttributes as ua WHERE ua.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            ValidationUtil.checkNotFoundWithId(rowsAffected > 0, "Attribute with id " + id + " not found");
        });

    }

}
