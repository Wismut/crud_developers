package repository.impl;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import model.Developer;
import repository.DeveloperRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM developers WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Developer(resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Developer> getAll() {
        return null;
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
