package repository.impl;

import model.Developer;
import repository.DeveloperRepository;
import repository.connectionpool.ConnectionUtil;

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
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM developers WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Developer(resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")));
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
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM developers");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developers.add(new Developer(resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")));
            }
            return developers;
        } catch (SQLException e) {
            e.printStackTrace();
            return developers;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Developer save(Developer developer) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Developer update(Developer developer) {
        return null;
    }
}
