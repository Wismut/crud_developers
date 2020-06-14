package repository.impl;


import hibernate.HibernateUtil;
import model.Specialty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.SpecialtyRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Optional<Specialty> getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Specialty> criteriaQuery = criteriaBuilder.createQuery(Specialty.class);
            Root<Specialty> from = criteriaQuery.from(Specialty.class);
            criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("id"), id));
            Query<Specialty> query = session.createQuery(criteriaQuery);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Specialty> criteriaQuery = criteriaBuilder.createQuery(Specialty.class);
            Root<Specialty> from = criteriaQuery.from(Specialty.class);
            CriteriaQuery<Specialty> findAll = criteriaQuery.select(from);
            Query<Specialty> query = session.createQuery(findAll);
            return query.getResultList();
        }
    }

    @Override
    public Specialty save(Specialty specialty) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(specialty);
        transaction.commit();
        return specialty;
    }

    @Override
    public void deleteBy(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from Specialty where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(specialty);
            session.getTransaction().commit();
            return specialty;
        }
    }
}
