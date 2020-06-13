package repository.impl;

import hibernate.HibernateUtil;
import model.Developer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.DeveloperRepository;
import repository.connectionpool.ConnectionUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " +
                DeveloperRepository.TABLE_NAME +
                " WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Developer update(Developer developer) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE " +
                DeveloperRepository.TABLE_NAME +
                " SET " +
                FIRSTNAME_COLUMN_NAME +
                " = ?, " +
                LASTNAME_COLUMN_NAME +
                " = ?, " +
                " = ? WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setLong(3, developer.getId());
            statement.execute();
            return developer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Developer> getAllBySpecialty(String specialityName) {
        Connection connection = ConnectionUtil.getConnection();
        List<Developer> developers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                TABLE_NAME +
                " WHERE " +
                SPECIALTYID_COLUMN_NAME +
                " = ?")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developers.add(new Developer(resultSet.getLong(ID_COLUMN_NAME),
                        resultSet.getString(FIRSTNAME_COLUMN_NAME),
                        resultSet.getString(LASTNAME_COLUMN_NAME)));
            }
            return developers;
        } catch (SQLException e) {
            return developers;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }
}
