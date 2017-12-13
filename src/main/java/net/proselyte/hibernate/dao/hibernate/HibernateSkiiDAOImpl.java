package net.proselyte.hibernate.dao.hibernate;

import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.Skill;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nastya on 27.11.2017.
 */
public class HibernateSkiiDAOImpl implements SkillDAO {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static HibernateSkiiDAOImpl instance = new HibernateSkiiDAOImpl();

    private HibernateSkiiDAOImpl() {
    }

    public static SkillDAO getInstance() {
        return instance;
    }

    public void save(Skill skill) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.save(skill);

        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Skill skill) throws SQLException {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.update(skill);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Skill getById(Long id) {
        try (Session session = this.sessionFactory.openSession()){
        Skill skill = session.get(Skill.class, id);
        Hibernate.initialize(skill.getDevelopers());
        return skill;
         }
    }

    public void delete(Skill skill) {
        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()){
        transaction = session.beginTransaction();

        session.delete(skill);
        transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Skill> getAll() {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Skill s");
            List<Skill> result = query.list();
            for (Skill skill : result) {
                Hibernate.initialize(skill.getDevelopers());
            }
            return result;
        }
    }
}
