package repository.impl;

import model.Developer;
import repository.DeveloperRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                TABLE_NAME +
                " WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Developer(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(FIRSTNAME_COLUMN_NAME),
                    resultSet.getString(LASTNAME_COLUMN_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Developer> getAll() {
        Connection connection = ConnectionUtil.getConnection();
        List<Developer> developers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                TABLE_NAME)) {
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

    @Override
    public Developer save(Developer developer) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                        DeveloperRepository.TABLE_NAME +
                        " VALUES(0, ?, ?)"
                , Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setLong(3, developer.getSpecialty().getId());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long newId = generatedKeys.getLong(1);
            developer.setId(newId);
            return developer;
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
}
