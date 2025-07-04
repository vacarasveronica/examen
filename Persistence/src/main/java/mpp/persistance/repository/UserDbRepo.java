package mpp.persistance.repository;



import mpp.model.User;
import mpp.persistance.UserRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDbRepo implements UserRepoInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public User findOne(Integer id) {
        logger.traceEntry("Finding user with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User u = session.get(User.class, id);
            logger.traceExit(u);
            return u;
        } catch (Exception e) {
            logger.error("Error finding user with id: " + id, e);
            return null;
        }
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry("Finding all users");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<User> u = session.createQuery("from User", User.class).list();
            logger.traceExit(u);
            return u;
        } catch (Exception e) {
            logger.error("Error finding all users", e);
            return List.of();
        }
    }

    @Override
    public User save(User entity) {
        logger.traceEntry("Saving user {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error saving user", e);
            return null;
        }
    }

    @Override
    public User delete(Integer id) {
        logger.traceEntry("Deleting user with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User u = session.get(User.class, id);
            if (u != null) {
                Transaction tx = session.beginTransaction();
                session.remove(u);
                tx.commit();
                logger.traceExit(u);
                return u;
            }
            logger.info("User with id {} not found", id);
            return null;
        } catch (Exception e) {
            logger.error("Error deleting user with id: " + id, e);
            return null;
        }
    }

    @Override
    public User update(User entity) {
        logger.traceEntry("Updating user {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error updating user", e);
            return null;
        }
    }
}


