package repository.impl;

import model.Skill;
import repository.SkillRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Optional<Skill> getById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SkillRepository.TABLE_NAME + " WHERE " + ID_ROW_NAME + " = ?");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Skill(resultSet.getLong(ID_ROW_NAME),
                    resultSet.getString(NAME_ROW_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Skill> getAll() {
        Connection connection = ConnectionUtil.getConnection();
        List<Skill> skills = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SkillRepository.TABLE_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skills.add(new Skill(resultSet.getLong(ID_ROW_NAME),
                        resultSet.getString(NAME_ROW_NAME)));
            }
            return skills;
        } catch (SQLException e) {
            e.printStackTrace();
            return skills;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Skill save(Skill skill) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                            SkillRepository.TABLE_NAME +
                            "(" +
                            NAME_ROW_NAME +
                            ")" +
                            " VALUES(?)"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, skill.getName());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long newId = generatedKeys.getLong(ID_ROW_NAME);
            skill.setId(newId);
            return skill;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public void deleteBy(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " +
                    SkillRepository.TABLE_NAME +
                    " WHERE " +
                    ID_ROW_NAME +
                    " = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Skill update(Skill skill) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE " +
                    SkillRepository.TABLE_NAME +
                    " SET " +
                    NAME_ROW_NAME +
                    " = ? WHERE " +
                    ID_ROW_NAME +
                    " = ?");
            statement.setString(1, skill.getName());
            statement.execute();
            return skill;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Skill> getByName(String name) {
        return Optional.empty();
    }
}
