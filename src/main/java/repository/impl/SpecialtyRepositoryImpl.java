package repository.impl;


import liquibase.util.StringUtils;
import model.Specialty;
import org.hibernate.Session;
import repository.SpecialtyRepository;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Optional<Specialty> getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return Optional.ofNullable(session.get(Specialty.class, id));
        }
    }

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session
                    .createQuery("FROM Specialty", Specialty.class)
                    .getResultList();
        }
    }

    @Override
    public Specialty save(Specialty specialty) {
        try (Session session = HibernateUtil.getSession()) {
            session.save(specialty);
            return specialty;
        }
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
            Specialty savedSpecialty = session.get(Specialty.class, specialty.getId());
            if (StringUtils.isNotEmpty(specialty.getName())) {
                savedSpecialty.setName(specialty.getName());
            }
            if (StringUtils.isNotEmpty(specialty.getDescription())) {
                savedSpecialty.setDescription(specialty.getDescription());
            }
            session.update(savedSpecialty);
            session.getTransaction().commit();
            return specialty;
        }
    }
}
