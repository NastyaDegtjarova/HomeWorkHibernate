package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.model.Developer;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class HibernateDeveloperDAOImpl implements DeveloperDAO {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateDeveloperDAOImpl instance = new HibernateDeveloperDAOImpl();

    private HibernateDeveloperDAOImpl() {
    }

    public static DeveloperDAO getInstance() {
        return instance;
    }

    public void save(Developer developer) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.save(developer);

        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Developer developer) throws SQLException {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.update(developer);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Developer getById(Long id) {
        try(Session session = this.sessionFactory.openSession()) {
            Developer developer = session.get(Developer.class, id);
            Hibernate.initialize(developer.getProjects());
            Hibernate.initialize(developer.getSkills());
            return developer;
        }
    }

    public void delete(Developer developer) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.delete(developer);
        transaction.commit();
    } catch (HibernateException e) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
    }

    public List<Developer> getAll() {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Developer d");
            List<Developer> result = query.list();
            for (Developer developer : result) {
                Hibernate.initialize(developer.getProjects());
                Hibernate.initialize(developer.getSkills());
            }
            return result;
        }
    }

    public List<Developer> getAllBySpecialty(String specialty) {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Developer d where d.SPECIALTY= :SPECIALTY");
            query.setParameter("SPECIALTY", specialty);
            List<Developer> result = query.list();
            return result;
        }
    }

    public List<Developer> getDeveloperWithSalaryAbove(BigDecimal salary) {
        try(Session session = this.sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Developer.class);
            criteria.add(Restrictions.gt("SALARY", salary));
            List<Developer> result = criteria.list();
            return result;
        }
    }

    public List<Developer> getAllDeveloperSQL() {
        try(Session session = this.sessionFactory.openSession()){
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM DEVELOPER");
        sqlQuery.addEntity(Developer.class);
        List<Developer> result = sqlQuery.list();
        return result;
    }
    }
}
