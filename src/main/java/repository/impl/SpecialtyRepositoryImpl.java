package repository.impl;


import hibernate.HibernateUtil;
import model.Specialty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SpecialtyRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Optional<Specialty> getById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SpecialtyRepository.TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Specialty(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(NAME_COLUMN_NAME),
                    resultSet.getString(DESCRIPTION_COLUMN_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Specialty> getAll() {
        Connection connection = ConnectionUtil.getConnection();
        List<Specialty> specialties = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SpecialtyRepository.TABLE_NAME)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                specialties.add(new Specialty(resultSet.getLong(ID_COLUMN_NAME),
                        resultSet.getString(NAME_COLUMN_NAME),
                        resultSet.getString(DESCRIPTION_COLUMN_NAME)));
            }
            return specialties;
        } catch (SQLException e) {
            e.printStackTrace();
            return specialties;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Specialty save(Specialty specialty) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(specialty);
        transaction.commit();
        return null;
    }

    @Override
    public void deleteBy(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " +
                SpecialtyRepository.TABLE_NAME +
                " WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE " +
                SpecialtyRepository.TABLE_NAME +
                " SET " +
                NAME_COLUMN_NAME +
                " = ? WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setString(1, specialty.getName());
            statement.setLong(2, specialty.getId());
            statement.execute();
            return specialty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }
}
