package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.Project;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nastya on 27.11.2017.
 */
public class HibernateProjectDAOImpl implements ProjectDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateProjectDAOImpl instance = new HibernateProjectDAOImpl();

    private HibernateProjectDAOImpl() {
    }

    public static ProjectDAO getInstance() {
        return instance;
    }

    public void save(Project project) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.save(project);

        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Project project) throws SQLException {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.update(project);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Project getById(Long id) {
        try(Session session = this.sessionFactory.openSession()) {
            Project project = session.get(Project.class, id);
            Hibernate.initialize(project.getCompanies());
            Hibernate.initialize(project.getCustomers());
            Hibernate.initialize(project.getDevelopers());
            return project;
        }
    }

    public void delete(Project project) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.delete(project);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Project> getAll() {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Project p");
            List<Project> result = query.list();
            for (Project project : result) {
                Hibernate.initialize(project.getCompanies());
                Hibernate.initialize(project.getCustomers());
                Hibernate.initialize(project.getDevelopers());
            }
            return result;
        }
    }
}
