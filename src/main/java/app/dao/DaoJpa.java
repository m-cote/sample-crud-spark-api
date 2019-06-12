package app.dao;

import app.util.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DaoJpa {

    private static final Logger log = LoggerFactory.getLogger(DaoJpa.class);

    protected void setTransactionReadOnly(EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);
        session.setDefaultReadOnly(true);
    }

    protected void doInJPA(Consumer<EntityManager> function) {

        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            try {
                entityManager = session.getEntityManagerFactory().createEntityManager();
                txn = entityManager.getTransaction();
                txn.begin();
                function.accept(entityManager);
                if (!txn.getRollbackOnly()) {
                    txn.commit();
                } else {
                    try {
                        txn.rollback();
                    } catch (Exception e) {
                        log.error("Rollback failure", e);
                    }
                }
            } catch (Throwable t) {
                if (txn != null && txn.isActive()) {
                    try {
                        txn.rollback();
                    } catch (Exception e) {
                        log.error("Rollback failure", e);
                    }
                }
                throw t;
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
    }

    protected <T> T doInJPA(Function<EntityManager, T> function) {

        T result = null;
        EntityManager entityManager = null;
        EntityTransaction txn = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                entityManager = session.getEntityManagerFactory().createEntityManager();
                txn = entityManager.getTransaction();
                txn.begin();
                result = function.apply(entityManager);
                if (!txn.getRollbackOnly()) {
                    txn.commit();
                } else {
                    try {
                        txn.rollback();
                    } catch (Exception e) {
                        log.error("Rollback failure", e);
                    }
                }
            } catch (Throwable t) {
                if (txn != null && txn.isActive()) {
                    try {
                        txn.rollback();
                    } catch (Exception e) {
                        log.error("Rollback failure", e);
                    }
                }
                throw t;
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
            return result;
        }
    }


}
