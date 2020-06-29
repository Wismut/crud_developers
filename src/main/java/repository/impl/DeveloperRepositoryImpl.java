package repository.impl;

import liquibase.util.StringUtils;
import model.Developer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.DeveloperRepository;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return Optional.ofNullable(session.get(Developer.class, id));
        }
    }

    @Override
    public List<Developer> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session
                    .createQuery("FROM Developer", Developer.class)
                    .getResultList();
        }
    }

    @Override
    public Developer save(Developer developer) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(developer);
            transaction.commit();
            return developer;
        }
    }

    @Override
    public void deleteBy(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(id);
            transaction.commit();
        }
    }

    @Override
    public Developer update(Developer developer) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Developer savedDeveloper = session.get(Developer.class, developer.getId());
            if (StringUtils.isNotEmpty(developer.getFirstName())) {
                savedDeveloper.setFirstName(developer.getFirstName());
            }
            if (StringUtils.isNotEmpty(developer.getLastName())) {
                savedDeveloper.setLastName(developer.getLastName());
            }
            session.update(developer);
            transaction.commit();
            return savedDeveloper;
        }
    }

    @Override
    public List<Developer> getAllBySpecialty(String specialtyName) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            String sqlQuery = "SELECT new Developer(d.id, d.firstName, d.lastName, s.name) FROM Developer d JOIN Specialty s ON d.specialty = s.id AND s.name =:specialtyName";
            Query<Developer> query = session.createQuery(sqlQuery, Developer.class);
            query.setParameter("specialtyName", specialtyName);
            List<Developer> developers = query.list();
            transaction.commit();
            return developers;
        }
    }
}
