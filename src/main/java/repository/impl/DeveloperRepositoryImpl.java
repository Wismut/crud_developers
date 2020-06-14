package repository.impl;

import hibernate.HibernateUtil;
import model.Developer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.DeveloperRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long id) {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Developer> criteriaQuery = criteriaBuilder.createQuery(Developer.class);
        Root<Developer> from = criteriaQuery.from(Developer.class);
        criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("id"), id));
        Query<Developer> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public List<Developer> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Developer> criteriaQuery = criteriaBuilder.createQuery(Developer.class);
        Root<Developer> from = criteriaQuery.from(Developer.class);
        criteriaQuery.select(from);
        Query<Developer> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Developer save(Developer developer) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        return null;
    }

    @Override
    public void deleteBy(Long id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(id);
        transaction.commit();
    }

    @Override
    public Developer update(Developer developer) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(developer);
        transaction.commit();
        return null;
    }

    @Override
    public List<Developer> getAllBySpecialty(String specialtyName) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String sqlQuery = "SELECT new Developer(d.firstName, d.lastName) FROM Developer d JOIN Specialty s ON d.specialty = s.id AND s.name =:specialtyName";
        Query<Developer> query = session.createQuery(sqlQuery, Developer.class);
        query.setParameter("specialtyName", specialtyName);
        List<Developer> developers = query.list();
        transaction.commit();
        return developers;
    }
}
