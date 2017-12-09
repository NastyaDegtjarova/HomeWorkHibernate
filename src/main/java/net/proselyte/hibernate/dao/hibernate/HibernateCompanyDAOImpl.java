package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.CompanieDAO;
import net.proselyte.hibernate.model.Companie;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(companie);

        transaction.commit();
        session.close();
    }

    public void update(Companie companie) throws SQLException {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(companie);
        transaction.commit();
        session.close();
    }

    public Companie getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Companie companie = session.get(Companie.class, id);
        Hibernate.initialize(companie.getProjects());
        session.close();
        return companie;
    }

    public void delete(Companie companie) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(companie);
        transaction.commit();
        session.close();
    }

    public List<Companie> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Companie c");
        List<Companie> result = query.list();
        for (Companie companie : result) {
            Hibernate.initialize(companie.getProjects());
        }
        session.close();
        return result;
    }


}
