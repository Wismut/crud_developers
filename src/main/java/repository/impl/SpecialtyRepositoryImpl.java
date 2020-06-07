package repository.impl;


import model.Specialty;
import repository.SpecialtyRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.*;
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
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                        SpecialtyRepository.TABLE_NAME +
                        " VALUES(0, ?, ?)"
                , Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, specialty.getName());
            statement.setString(2, specialty.getDescription());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long newId = generatedKeys.getLong(1);
            specialty.setId(newId);
            return specialty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
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
