package repository.impl;

import hibernate.HibernateUtil;
import model.Skill;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.SkillRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Optional<Skill> getById(Long id) {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> from = criteriaQuery.from(Skill.class);
        criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("id"), id));
        Query<Skill> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public List<Skill> getAll() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> from = criteriaQuery.from(Skill.class);
        CriteriaQuery<Skill> findAll = criteriaQuery.select(from);
        Query<Skill> query = session.createQuery(findAll);
        return query.getResultList();
    }

    @Override
    public Skill save(Skill skill) {
        Session session = HibernateUtil.getSession();
        session.save(skill);
        return skill;
    }

    @Override
    public void deleteBy(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from Skill where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Skill update(Skill skill) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(skill);
            session.getTransaction().commit();
        }
        return skill;
    }

    @Override
    public Optional<Skill> getByName(String name) {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> from = criteriaQuery.from(Skill.class);
        criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("name"), name));
        Query<Skill> query = session.createQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public List<Skill> getAllByNames(List<String> names) {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> from = criteriaQuery.from(Skill.class);
        Predicate predicate = from.get("name").in(names);
        CriteriaQuery<Skill> findAll = criteriaQuery.select(from)
                .where(predicate);
        Query<Skill> query = session.createQuery(findAll);
        return query.getResultList();
    }

    @Override
    public List<Long> saveBatch(List<Skill> skills) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            for (Skill skill : skills) {
                session.save(skill);
            }
            session.getTransaction().commit();
        }
        return skills.stream()
                .mapToLong(Skill::getId)
                .boxed()
                .collect(Collectors.toList());
    }
}
