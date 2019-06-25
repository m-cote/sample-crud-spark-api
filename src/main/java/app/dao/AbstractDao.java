package app.dao;

import app.util.JpaUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDao {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected void setTransactionReadOnly(EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);
        session.setDefaultReadOnly(true);
    }

    protected void doInJPA(Consumer<EntityManager> function) {
        doInJPA(function, false);
    }

    protected void doInJPA(Consumer<EntityManager> function, boolean readOnly) {

        EntityManager entityManager = null;
        EntityTransaction txn = null;

        try {
            entityManager = JpaUtil.createEntityManager();

            if (readOnly) {
                setTransactionReadOnly(entityManager);
            }

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

    protected <T> T doInJPA(Function<EntityManager, T> function) {
        return doInJPA(function, false);
    }

    protected <T> T doInJPA(Function<EntityManager, T> function, boolean readOnly) {

        T result = null;
        EntityManager entityManager = null;
        EntityTransaction txn = null;

        try {
            entityManager = JpaUtil.createEntityManager();

            if (readOnly) {
                setTransactionReadOnly(entityManager);
            }

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

    protected void doInHibernateSession(Work work) {
        doInHibernateSession(work, false);
    }

    protected void doInHibernateSession(Work work, boolean readOnly) {

        Transaction txn = null;

        try (Session session = JpaUtil.openSession()) {
            session.setDefaultReadOnly(readOnly);
            txn = session.getTransaction();
            txn.begin();
            session.doWork(work);
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
        }
    }

    protected <T> T doInHibernateSession(ReturningWork<T> work) {
        return doInHibernateSession(work, false);
    }

    protected <T> T doInHibernateSession(ReturningWork<T> work, boolean readOnly) {

        T result = null;
        Transaction txn = null;

        try (Session session = JpaUtil.openSession()) {
            session.setDefaultReadOnly(readOnly);
            txn = session.getTransaction();
            txn.begin();
            result = session.doReturningWork(work);
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
        }

        return result;
    }
}
