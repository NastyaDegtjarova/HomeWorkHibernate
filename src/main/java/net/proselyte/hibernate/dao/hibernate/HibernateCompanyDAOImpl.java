package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.CompanieDAO;
import net.proselyte.hibernate.model.Companie;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nastya on 27.11.2017.
 */
public class HibernateCompanyDAOImpl implements CompanieDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateCompanyDAOImpl instance = new HibernateCompanyDAOImpl();

    private HibernateCompanyDAOImpl() {
    }

    public static CompanieDAO getInstance() {
        return instance;
    }

    public void save(Companie companie) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(companie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Companie companie) throws SQLException {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.update(companie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Companie getById(Long id) {
        try (Session session = this.sessionFactory.openSession()) {
            Companie companie = session.get(Companie.class, id);
            Hibernate.initialize(companie.getProjects());
            return companie;
        }
    }

    public void delete(Companie companie) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(companie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Companie> getAll() {
        try (Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Companie c");
            List<Companie> result = query.list();
            for (Companie companie : result) {
                Hibernate.initialize(companie.getProjects());
            }
            return result;
        }
    }
}
