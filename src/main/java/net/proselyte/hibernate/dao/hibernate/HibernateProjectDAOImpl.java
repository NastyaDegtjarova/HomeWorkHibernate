package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.Project;
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
public class HibernateProjectDAOImpl implements ProjectDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateProjectDAOImpl instance = new HibernateProjectDAOImpl();

    private HibernateProjectDAOImpl() {
    }

    public static ProjectDAO getInstance() {
        return instance;
    }

    public void save(Project project) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(project);

        transaction.commit();
        session.close();
    }

    public void update(Project project) throws SQLException {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(project);
        transaction.commit();
        session.close();
    }

    public Project getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Project project = session.get(Project.class, id);
        Hibernate.initialize(project.getCompanies());
        Hibernate.initialize(project.getCustomers());
        Hibernate.initialize(project.getDevelopers());
        session.close();
        return project;
    }

    public void delete(Project project) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(project);
        transaction.commit();
        session.close();
    }

    public List<Project> getAll() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("FROM Project p");
        List<Project> result = query.list();
        for (Project project : result) {
            Hibernate.initialize(project.getCompanies());
            Hibernate.initialize(project.getCustomers());
            Hibernate.initialize(project.getDevelopers());
        }
        session.close();
        return result;
    }
}
