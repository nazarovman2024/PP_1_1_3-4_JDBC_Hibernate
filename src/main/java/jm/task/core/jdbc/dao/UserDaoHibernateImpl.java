package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    private void query(String sql, String todo) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to " + todo, e);
        }
    }

    @Override
    public void createUsersTable() {
        query("CREATE TABLE IF NOT EXISTS users ("
                        + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                        + "name VARCHAR(255),"
                        + "lastName VARCHAR(255),"
                        + "age TINYINT)",
                "create users table");
    }

    @Override
    public void dropUsersTable() {
        query("DROP TABLE IF EXISTS users", "drop users table");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to remove user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            List<User> allUsers = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return allUsers;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to get all users", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        query("TRUNCATE TABLE users", "clean users table");
    }
}
