package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.CustomerDAO;
import net.proselyte.hibernate.model.Customer;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nastya on 27.11.2017.
 */
public class HibernateCustomerDAOImpl implements CustomerDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateCustomerDAOImpl instance = new HibernateCustomerDAOImpl();

    private HibernateCustomerDAOImpl() {
    }

    public static CustomerDAO getInstance() {
        return instance;
    }

    public void save(Customer customer) {
        Transaction transaction = null;
       try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();
        session.save(customer);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Customer customer) throws SQLException {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.update(customer);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Customer getById(Long id) {
        try(Session session = this.sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                Hibernate.initialize(customer.getProjects());
            }
            return customer;
        }
    }

    public void delete(Customer customer) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.delete(customer);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Customer> getAll() {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Customer c");
            List<Customer> result = query.list();
            for (Customer customer : result) {
                Hibernate.initialize(customer.getProjects());
            }
            return result;
        }
    }
}
