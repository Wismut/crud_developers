package repository.impl;

import hibernate.HibernateUtil;
import model.Skill;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import repository.SkillRepository;
import repository.connectionpool.ConnectionUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                SkillRepository.TABLE_NAME +
                " WHERE " +
                NAME_COLUMN_NAME +
                " = ?")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Skill(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(NAME_COLUMN_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Skill> getAllByNames(List<String> names) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Skill.class);
        criteria.add(Restrictions.in("name", names));
        return criteria.list();
//        Connection connection = ConnectionUtil.getConnection();
//        List<Skill> skills = new ArrayList<>();
//        String namesForQuery = names.stream()
//                .map(n -> "'" + n + "'")
//                .reduce((a, b) -> a + ',' + b)
//                .map(s -> '(' + s + ')')
//                .orElseThrow(RuntimeException::new);
//        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
//                SkillRepository.TABLE_NAME +
//                " WHERE " +
//                NAME_COLUMN_NAME +
//                " IN " +
//                namesForQuery)) {
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                skills.add(new Skill(resultSet.getLong(ID_COLUMN_NAME),
//                        resultSet.getString(NAME_COLUMN_NAME)));
//            }
//            return skills;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return skills;
//        } finally {
//            ConnectionUtil.releaseConnection(connection);
//        }
    }

    @Override
    public List<Long> saveBatch(List<Skill> skills) {
        Connection connection = ConnectionUtil.getConnection();
        List<Long> generatedIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                        SkillRepository.TABLE_NAME +
                        " VALUES(0, ?)"
                , Statement.RETURN_GENERATED_KEYS)) {
            for (Skill skill : skills) {
                statement.setString(1, skill.getName());
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generatedIds.add(generatedKeys.getLong(1));
            }
            return generatedIds;
        } catch (SQLException e) {
            return generatedIds;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }
}
